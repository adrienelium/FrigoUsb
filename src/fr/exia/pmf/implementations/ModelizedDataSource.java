package fr.exia.pmf.implementations;

import fr.exia.pmf.model.Statement;

public class ModelizedDataSource extends RandomDataSource {
	
	private double H;
	private double Tin;
	private double Tout;
	
	// Cible pour modifier la temperature extérieure
	private double cibleTout;
	
	// L'équivalent de froid accumulé par le générateur
	private double variationFroid = 0d;
	
	// Variation accumulée avec la mise en marche du frigo
	private double pasVariationFroid = -0.1d;
	
	// Variation accumulée lors de l'arrêt du frigo
	private double pasVariationChaud = 0.2d;
	
	// Puissance maximale du frigo
	private double variationFroidMax = 0.9d;
	
	@Override
	public void init() throws Throwable {
		
		// Génération d'une température extérieure.
		// 22°C +/- 4°C
		Tout = 22.0d + (Math.random() * 8.0d) - 4.0d;
		
		// Initialement la T° intérieure est la même qu'à l'extérieur.
		Tin = Tout;
		
		// Le taux d'humidité est complétement random
		H = Math.random() * 100.0d;
		
		// Dès le début on fait grimper la température d'un degrés
		cibleTout = Tout + 1;
		
	}
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			
			// Naturellement la T° extérieure converge vers la T° intérieure.
			Tin -= (Tin-Tout)/10d;

			// Si le frigo est allumé, la génération de froid monte en puissance
			if (isPowerEnabled() && variationFroid < variationFroidMax) {
				variationFroid -= pasVariationFroid;
			}
			
			// Quand le frido est coupé la génération de froid baisse rapidement
			else if (!isPowerEnabled()) {
				variationFroid -= pasVariationChaud;
			}
			
			// On évite la chauffe du réfrigérateur
			if (variationFroid < 0) variationFroid = 0;
			
			// On fait varier la T° intérieure avec la génération de froid
			Tin -= variationFroid;
			
			// On fait varier la température extérieure
			if (Math.abs(cibleTout - Tout) > 0.01) {
				Tout -= (Tout-cibleTout)/20d;
			}
			else {
				// On génére une nouvelle température à atteindre
				cibleTout += Math.random() * 2 - 1;
				if (cibleTout > 27) cibleTout -= 3;
				if (cibleTout < 6) cibleTout += 3;
			}
			
			// On notifie les listeners qu'une nouvelle données est disponible
			notifyListeners(new Statement(H, Tin, Tout));
			
			// On sleep
			sleep(500);
		}
	}
	
}

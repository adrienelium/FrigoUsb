package fr.exia.pmf.implementations;

import fr.exia.pmf.model.Statement;

public class ModelizedDataSource extends RandomDataSource {
	
	private double H;
	private double Tin;
	private double Tout;
	
	// L'équivalent de froid accumulé par le générateur
	private double variationFroid = 0d;
	
	// Variation accumulée avec la mise en marche du frigo
	private double pasVariationFroid = -0.1d;
	
	// Variation accumulée lors de l'arrêt du frigo
	private double pasVariationChaud = 0.2d;
	
	// Puissance maximale du frigo
	private double variationFroidMax = 0.8d;
	
	// Cible pour modifier la temperature extérieure
	private double cibleTempExt;
	
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
		cibleTempExt = Tout + 1;
		
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
			if (!isPowerEnabled()) {
				variationFroid -= pasVariationChaud;
			}
			
			// On fait varier la T° intérieure avec la génération de froid
			Tin -= variationFroid;
			
			// On fait varier la température extérieure
			if (Math.abs(cibleTempExt - Tout) > 0.01) {
				Tout -= (Tout-cibleTempExt)/20d;
			}
			else {
				// On génére une nouvelle température à atteindre
				cibleTempExt += Math.random() * 2 - 1;
				if (cibleTempExt > 27) cibleTempExt -= 3;
				if (cibleTempExt < 6) cibleTempExt += 3;
			}
			
			// On notifie les listeners qu'une nouvelle données est disponible
			notifyListeners(new Statement(H, Tout, Tin));
			sleep();
		}
	}
	
}

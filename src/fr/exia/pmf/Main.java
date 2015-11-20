package fr.exia.pmf;


import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IRegulator;
import fr.exia.pmf.implementations.RandomDataSource;
import fr.exia.pmf.implementations.ModelizedDataSource;
import fr.exia.pmf.implementations.LogiqueApplicative;
import fr.exia.pmf.implementations.RegulationSimple;
import fr.exia.pmf.implementations.ArduinoDataSource;
import fr.exia.pmf.vue.WindowsV2;

public class Main {

	public static void main(String[] ag) {
		
		// Logs
		boolean PRODUCTION = true;
		
		// On obtient une implémentation de la liaison données
		IDataConnection datalink = getDataLinkImplementation();

		// On fabrique une logique de régulation
		IRegulator regulator = new RegulationSimple(!PRODUCTION);
		
		// On fabrique une logique applicative
		LogiqueApplicative app = new LogiqueApplicative();
		
		// Je passe dans le thread de l'IHM
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// On modifie le LookAndFeel
				try {
					UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					System.err.println("Impossible de charger le LookAndFeel");
				}
				
				// En production on lève une alerte si on est sur des fausses données
				if (PRODUCTION && !(datalink instanceof ArduinoDataSource)) {
					JOptionPane.showMessageDialog(null, "Impossible d'établire la liaison avec l'Arduino."
							+ "\nDe fausses données vont être utilisées.", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				
				// On fabrique une fenêtre
				WindowsV2 vue = new WindowsV2();
				
				// Et enfin on démarre la logique applicative
				app.start(vue, datalink, regulator);
								
			}
		});

	}

	private static IDataConnection getDataLinkImplementation() {
		// On choisit une implémentation valide de la source de donnée
		IDataConnection obj = chooseImplementation(
				new ArduinoDataSource(),
				new ModelizedDataSource(),
				new RandomDataSource());
		
		// Aucune implémentation valide
		if (obj == null) {
			System.err.println("No valid implementation to run");
			System.exit(-1);
		}
		
		// On affiche l'implémentation retenue
		System.out.println("Data source: " + obj.getClass().getSimpleName());
		
		// Et one renvoie l'implémentation
		return obj;
	}
	
	private static IDataConnection chooseImplementation(IDataConnection... impls) {
		// On parcours les implémentations
		for (IDataConnection impl : impls) {
			// On tente d'initialiser 
			try {
				impl.init();
			}
			// En cas d'erreur, on tente une autre implémentation
			catch (Throwable e) {
				continue;
			}
			// On a trouvé une implémentation valide
			return impl;
		}
		// On n'a pas trouvé d'implémentation valide
		return null;
	}

}

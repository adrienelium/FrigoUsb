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

	public static void main(String[] args) {

		// Logs
		boolean DEBUG = args.length > 0 && "debug".equals(args[0]);
		
		// On obtient une impl�mentation de la liaison donn�es
		IDataConnection datalink = DEBUG ? new ModelizedDataSource() : getDataLinkImplementation();
		
		// On affiche l'impl�mentation retenue
		System.out.println("[DataSource] Data source: " + datalink.getClass().getSimpleName());

		// On fabrique une logique de r�gulation
		IRegulator regulator = new RegulationSimple();
		
		// On fabrique une logique applicative
		LogiqueApplicative app = new LogiqueApplicative();
		
		// Je passe dans le thread de l'IHM
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// On modifie le LookAndFeel
				try {
					UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					System.err.println("[IHM] Impossible de charger le LookAndFeel");
				}
				
				// On fabrique une fen�tre
				WindowsV2 vue = new WindowsV2();
				
				// En production on l�ve une alerte si on est sur des fausses donn�es, et on l'indique dans le titre de la fen�tre
				if (!DEBUG && !(datalink instanceof ArduinoDataSource)) {
					JOptionPane.showMessageDialog(null, "Impossible d'�tablire la liaison avec l'Arduino."
							+ "\nDe fausses donn�es vont �tre utilis�es.", "Information", JOptionPane.INFORMATION_MESSAGE);
					vue.setTitle(vue.getTitle() + " (Simulation)");
				}
				
				// Et enfin on d�marre la logique applicative
				app.start(vue, datalink, regulator);
								
			}
		});

	}

	private static IDataConnection getDataLinkImplementation() {
		// On choisit une impl�mentation valide de la source de donn�e
		IDataConnection obj = chooseImplementation(
				new ArduinoDataSource(),
				new ModelizedDataSource(),
				new RandomDataSource());
		
		// Aucune impl�mentation valide
		if (obj == null) {
			System.err.println("No valid data source implementation to run");
			System.exit(-1);
		}
		
		// Et one renvoie l'impl�mentation
		return obj;
	}
	
	private static IDataConnection chooseImplementation(IDataConnection... impls) {
		// On parcours les impl�mentations
		for (IDataConnection impl : impls) {
			// On tente d'initialiser 
			try {
				impl.init();
			}
			// En cas d'erreur, on tente une autre impl�mentation
			catch (Throwable e) {
				System.out.println(String.format("[DataSource] Unable to use %s : %s (%s)",
						impl.getClass().getSimpleName(),
						e.getMessage(), e.getClass().getSimpleName()));
				continue;
			}
			// On a trouv� une impl�mentation valide
			return impl;
		}
		// On n'a pas trouv� d'impl�mentation valide
		return null;
	}

}

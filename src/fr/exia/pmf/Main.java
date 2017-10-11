package fr.exia.pmf;


import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IRegulator;
import fr.exia.pmf.implementations.ArduinoDataSource;
import fr.exia.pmf.implementations.LisseurTempExterne;
import fr.exia.pmf.implementations.LogiqueApplicative;
import fr.exia.pmf.implementations.ModelizedDataSource;
import fr.exia.pmf.implementations.RandomDataSource;
import fr.exia.pmf.implementations.RegulationPID;
import fr.exia.pmf.vue.WindowsV2;

public class Main {

	public static void main(String[] args) {

		// Logs
		final boolean SIMULATION = args.length > 0 && "simulation".equals(args[0]);
		
		// On obtient une impl�mentation de la liaison donn�es
		final IDataConnection datalink = SIMULATION ? new ModelizedDataSource() : getDataLinkImplementation();
		
		// On affiche l'impl�mentation retenue
		System.out.println("[DataSource] Data source: " + datalink.getClass().getSimpleName());

		// On fabrique une logique de r�gulation
		//final IRegulator regulator = new RegulationTOR();
		final IRegulator regulator = new RegulationPID(1, 1, 1);
		
		// On fabrique une logique applicative
		final LogiqueApplicative app = new LogiqueApplicative();
		
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
				if (!SIMULATION && datalink instanceof RandomDataSource) {
					JOptionPane.showMessageDialog(null, "Impossible d'�tablire la liaison avec l'Arduino."
							+ "\nDe fausses donn�es vont �tre utilis�es.", "Information", JOptionPane.INFORMATION_MESSAGE);
					vue.setTitle(vue.getTitle() + " (Simulation)");
				}
				
				// Et enfin on d�marre la logique applicative
				try {
					app.start(vue, datalink, regulator);
				} catch (Throwable ex) {
					JOptionPane.showMessageDialog(null, "Erreur au lancement de l'application."
							+ "\n" + ex.getClass().getSimpleName() + " : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

			}
		});

	}

	private static IDataConnection getDataLinkImplementation() {
		
		// On choisit une impl�mentation valide de la source de donn�e
		IDataConnection obj = chooseImplementation(
				new LisseurTempExterne(new ArduinoDataSource(), 15), // On corrige avec un lissage moyen sur 15 valeurs
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

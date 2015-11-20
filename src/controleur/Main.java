package controleur;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import abstractions.IConnection;
import modele.FakeDataInjector;
import modele.SerialClass;
import vue.Windows;

public class Main {

	public static void main(String[] ag) {
		
		// Logs
		boolean debug = false;
		
		// Je passe dans le thread de l'UI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// Je modifie le LookAndFeel
				try {
					UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					System.err.println("Impossible de charger le LookAndFeel");
				}
				
				// Je fabrique ma logique de r�gulation
				Regulation regul = new Regulation(debug);
				
				// Je fabrique ma vue
				Windows fen = new Windows(regul);
				fen.setVisible(true);
				
				// Je lance mes traitements hors IHM dans un nouveau thread
				new Thread(new Runnable() {
					public void run() {
						
						// On choisit une impl�mentation valide
						IConnection obj = chooseImplementation(new SerialClass(), new FakeDataInjector());
						
						// Aucune impl�mentation valide
						if (obj == null) {
							System.err.println("No valid implementation to run");
							return;
						}
						
						// On affiche l'impl�mentation retenue
						System.out.println("Data source: " + obj.getClass().getSimpleName());
						
						// On inscrit les listeners						
						obj.addListener(fen);
						obj.addListener(regul);
						
						// Et on lance la connexion
						obj.start();
						
					}
				}).start();
				
			}
		});

	}
	
	public static IConnection chooseImplementation(IConnection... impls) {
		// On parcours les impl�mentations
		for (IConnection impl : impls) {
			// On tente d'initialiser 
			try {
				impl.init();
			}
			// En cas d'erreur, on tente une autre impl�mentation
			catch (Throwable e) {
				continue;
			}
			// On a trouv� une impl�mentation valide
			return impl;
		}
		// On n'a pas trouv� d'impl�mentation valide
		return null;
	}

}

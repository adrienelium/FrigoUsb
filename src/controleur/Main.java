package controleur;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import modele.SerialClass;
import vue.Windows;

public class Main {

	public static void main(String[] ag) {
		
		// Je passe dans le thread de l'UI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// Je modifie le LookAndFeel
//				initLookAndFeel();
				try {
					UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					System.err.println("Impossible de charger le LookAndFeel");
				}
				
				// Je fabrique mes fenêtres
				Regulation regul = new Regulation();
				Windows fen = new Windows(regul);
				
				// Je lance mes traitements hors IHM dans un nouveau thread
				new Thread(new Runnable() {
					public void run() {
						SerialClass obj = new SerialClass(regul);
						obj.addObservateur(fen);
						obj.initialize();
					}
				}).start();
				
			}
		});
		




	}

	public static void initLookAndFeel() {

		// On détecte la version V6 de la librairie substance
		String className = "org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel";
		try {
			Class.forName(className);
		} catch (ClassNotFoundException ex1) {
			// On détecte la version V5 de la librairie substance
			className = "org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel";
			try {
				Class.forName(className);
			} catch (ClassNotFoundException ex2) {
				return;
			}
		}

		// On fait une copie de la classe du LAF
		final String lafClass = className;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// On tente le chargement de laf
				try {
					UIManager.setLookAndFeel(lafClass);

				} catch (Throwable ex) {
					System.err.println("Impossible de charger le LookAndFeel");
				}
			}
		});

	}

}

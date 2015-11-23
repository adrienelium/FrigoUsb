package fr.exia.pmf.implementations;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.abstractions.IRegulator;
import fr.exia.pmf.abstractions.IRegulatorListener;
import fr.exia.pmf.model.Statement;
import fr.exia.pmf.vue.FullScreenEffect;
import fr.exia.pmf.vue.WindowsV2;

public class LogiqueApplicative implements IDataConnectionListener, IRegulatorListener {
	
	// Puissance du réfrigérateur en Watt
	public static long PUISSANCE_FRIGO = 300;
	
	// Prix EDF en € au kWh au 21/11/15
	private static final double TARIF_KWH = 0.14040d;
	
	// Composants
	private WindowsV2 view;
	private IDataConnection datalink;
	private IRegulator regulator;

	/**
	 * Démarrer toute la logique applicative.
	 */
	public void start(WindowsV2 view, IDataConnection datalink, IRegulator regulator) {
		
		// On conserve les références
		this.view = view;
		this.datalink = datalink;
		this.regulator = regulator;
		
		// Valeur initiales de l'IHM
		this.view.labelConsigneTemp.setText(String.format("%.1f °C", regulator.getConsigneTemperature()));
		this.view.alertCondensation.setVisible(false);
		this.view.alertTempGap.setVisible(false);
		this.view.chart.mark.setValue(regulator.getConsigneTemperature());

		// On bind les comportements des boutons de régulation
		view.btnConsignePlus.addActionListener((actionEvent) -> {
			this.regulator.setTempConsigne(regulator.getConsigneTemperature() + 0.5f);
		});
		view.btnConsigneMoins.addActionListener((actionListener) -> {
			this.regulator.setTempConsigne(regulator.getConsigneTemperature() - 0.5f);
		});
		
		// On ajoute le comportement de fullscreen
		view.btnFullscreen.addActionListener(new FullScreenEffect(view));

		// On ferme l'application proprement quand la fenêtre se ferme
		view.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				// On ferme la connexion aux données
				datalink.stop();
			}
		});
		
		// Le régulateur a besoin d'une connexion à la donnée pour travailler
		this.datalink.addListener(regulator);

		// Pour mettre à jour l'IHM
		this.datalink.addListener(this);
		this.regulator.addListener(this);
		
		// On affiche la fenêtre
		this.view.setVisible(true);
		
		// Et enfin on lance la source de données !
		this.datalink.start();
		
	}

	/**
	 * On met à jour l'IHM quand de nouvelles données arrivent.
	 */
	@Override
	public void onNewStatementRead(Statement data) {
		EventQueue.invokeLater(() -> {
			// Update des labels
			view.labelTempExt.setText(String.format("%.1f °C", data.getExteriorTemperature()));
			view.labelTempInt.setText(String.format("%.1f °C", data.getInteriorTemperature()));
			view.labelHumitidy.setText(String.format("%.1f", data.getHumidityRate()) + "%");
			// On ajoute la donnée au chart
			view.chart.addData((float)data.getInteriorTemperature(), (float)data.getExteriorTemperature());
			// On ajoute de la conso quand le frigo est allumé
			double Wh = datalink.getPowerUptime() / 3600d * PUISSANCE_FRIGO;
			double kWh = Wh / 1000d;
			double prix = kWh * TARIF_KWH;
			String conso;
			if (Wh > 1000) {
				conso = String.format("Consommation : %.2f kWh (%.4f \u20AC)", kWh, prix);
			}
			else {
				conso = String.format("Consommation : %.0f Wh (%.4f \u20AC)", Wh, prix);
			}
			view.labelConsoWatt.setText(conso);
		});
	}
	
	/**
	 * On met à jour l'IHM quand la consigne de température change.
	 */
	@Override
	public void onConsigneTemperatureChanged(double temp) {
		EventQueue.invokeLater(() -> {
			view.labelConsigneTemp.setText(String.format("%.1f °C", temp));
			this.view.chart.mark.setValue(temp);
		});
	}
	
	/**
	 * Quand la consigne change, on l'envoi à la source de données.
	 */
	@Override
	public void onConsigneAllumageChanged(boolean enabled) {
		// On contrôle l'allumage ou l'extinction du système
		this.datalink.setPowerEnabled(enabled);
	}

	/**
	 * On met à jour l'IHM quand l'alerte de condensation change d'état.
	 */
	@Override
	public void onAlertCondensationChanged(boolean state) {
		EventQueue.invokeLater(() -> {
			view.alertCondensation.setVisible(state);
		});
	}

	/**
	 * On met à jour l'IHM quand l'alerte d'écart de température change d'état.
	 */
	@Override
	public void onAlertTemperatureGapChanged(boolean state) {
		EventQueue.invokeLater(() -> {
			view.alertTempGap.setVisible(state);
		});
	}

	/**
	 * On met à jour l'IHM quand l'état d'allumage du frigo est confirmé par la source.
	 */
	@Override
	public void onPowerStatusChanged(boolean powerOn) {
		EventQueue.invokeLater(() -> {
			// On met à jour la vue
			view.labelConsignePower.setText(String.format("Allumage %s", powerOn ? "ON " : "OFF"));
			view.labelConsignePower.setIcon(powerOn ? WindowsV2.ICON_YES : WindowsV2.ICON_NO);
		});
	}

}

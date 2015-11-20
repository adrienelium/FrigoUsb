package fr.exia.pmf.implementations;

import java.awt.EventQueue;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.abstractions.IRegulator;
import fr.exia.pmf.abstractions.IRegulatorListener;
import fr.exia.pmf.model.Statement;
import fr.exia.pmf.vue.WindowsV2;

public class LogiqueApplicative implements IDataConnectionListener, IRegulatorListener {
	
	private WindowsV2 view;
	private IDataConnection datalink;
	private IRegulator regulator;

	/**
	 * D�marrer toute la logique applicative.
	 */
	public void start(WindowsV2 view, IDataConnection datalink, IRegulator regulator) {
		
		// On conserve les r�f�rences
		this.view = view;
		this.datalink = datalink;
		this.regulator = regulator;
		
		// Valeur initiales de l'IHM
		this.view.labelConsigneTemp.setText(String.format("%.2f �C", regulator.getConsigneTemperature()));
		this.view.alertCondensation.setVisible(false);
		this.view.alertTempGap.setVisible(false);
		this.view.chart.mark.setValue(regulator.getConsigneTemperature());

		// On bind les comportements des boutons de r�gulation
		view.btnConsignePlus.addActionListener((actionEvent) -> {
			this.regulator.setTempConsigne(regulator.getConsigneTemperature() + 0.5f);
		});
		view.btnConsigneMoins.addActionListener((actionListener) -> {
			this.regulator.setTempConsigne(regulator.getConsigneTemperature() - 0.5f);
		});
		
		// Le r�gulateur a besoin d'une connexion � la donn�e pour travailler
		this.datalink.addListener(regulator);

		// Pour mettre � jour l'IHM
		this.datalink.addListener(this);
		this.regulator.addListener(this);
		
		// On affiche la fen�tre
		this.view.setVisible(true);
		
		// Et enfin on lance la source de donn�es !
		this.datalink.start();
		
	}

	/**
	 * On met � jour l'IHM quand de nouvelles donn�es arrivent.
	 */
	@Override
	public void onNewStatementRead(Statement data) {
		EventQueue.invokeLater(() -> {
			// Update des labels
			view.labelTempExt.setText(String.format("%.2f �C", data.getExteriorTemperature()));
			view.labelTempInt.setText(String.format("%.2f �C", data.getInteriorTemperature()));
			view.labelHumitidy.setText(String.format("%.1f", data.getHumidityRate()) + "%");
			// On ajoute la donn�e au chart
			view.chart.addData((float)data.getInteriorTemperature(), (float)data.getExteriorTemperature());	
		});
	}
	
	/**
	 * On met � jour l'IHM quand la consigne de temp�rature change.
	 */
	@Override
	public void onConsigneTemperatureChanged(double temp) {
		EventQueue.invokeLater(() -> {
			view.labelConsigneTemp.setText(String.format("%.2f �C", temp));
			this.view.chart.mark.setValue(temp);
		});
	}

	/**
	 * On met � jour l'IHM quand la consigne d'allumage change.
	 */
	@Override
	public void onConsigneAllumageChanged(boolean enabled) {
		EventQueue.invokeLater(() -> {
			// On met � jour la vue
			view.labelConsignePower.setText(String.format("Allumage %s", enabled ? "ON " : "OFF"));
			view.labelConsignePower.setIcon(enabled ? WindowsV2.ICON_YES : WindowsV2.ICON_NO);
			// On contr�le l'allumage ou l'extinction du syst�me
			this.datalink.setPowerEnabled(enabled);
		});
	}

	/**
	 * On met � jour l'IHM quand l'alerte de condensation change d'�tat.
	 */
	@Override
	public void onAlertCondensationChanged(boolean state) {
		EventQueue.invokeLater(() -> {
			view.alertCondensation.setVisible(state);
		});
	}

	/**
	 * On met � jour l'IHM quand l'alerte d'�cart de temp�rature change d'�tat.
	 */
	@Override
	public void onAlertTemperatureGapChanged(boolean state) {
		EventQueue.invokeLater(() -> {
			view.alertTempGap.setVisible(state);
		});
	}

}

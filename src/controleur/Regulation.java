package controleur;

import java.util.ArrayList;
import java.util.List;

import abstractions.IRegulator;
import abstractions.IRegulatorListener;
import modele.Statement;

public class Regulation implements IRegulator {
	
	private float consigneTemperature = 18.0f;
	private boolean consigneAllumage = false;
	private double histoIn;
	
	private boolean alertCondensation = false;
	private boolean alertTempGap = false;

	private List<IRegulatorListener> listeners;
	private boolean debug;
	
	/**
	 * Constructeur
	 */
	public Regulation(boolean debug) {

		this.debug = debug;
		// TODO ???
		this.histoIn = 5000;
		
		this.listeners = new ArrayList<IRegulatorListener>();
	}
	
	@Override
	public void onNewStatementRead(Statement data) {

		// On ramène le taux d'humidité entre 0 et 1
		double h = data.getHumidityRate() / 100;
		
		// On calcule la température de rosée
		double tempRose = Math.pow(h , 1.0/8) * (112 + (0.9 * data.getInteriorTemperature())) + (0.1 * data.getExteriorTemperature()) - 112;
		
		// On détecte de la condensation
		boolean isCondensation = (tempRose >= data.getInteriorTemperature());
		if (isCondensation != alertCondensation) {
			// On mémorise le nouvel état
			alertCondensation = isCondensation;
			// En cas de changement d'état on envoie un event
			notifyAlertCondensation(isCondensation);
		}
		
		// On détecte les forts écarts de température
		double delta = data.getInteriorTemperature() - this.histoIn;
		boolean isTempGap = delta >= 2 || delta <= -2 && this.histoIn != 5000;
		if (isTempGap != alertTempGap) {
			// On mémorise le nouvel état
			alertTempGap = isTempGap;
			// En cas de changement on envoie un event
			notifyAlertTemperatureGap(isTempGap);
		}
		
		// TODO C'est étrange ça... le temps n'est pas pris en compte ?
		this.histoIn = data.getInteriorTemperature();
		
		// On détermine l'état de la consigne d'allumage
		boolean consigneAllumage = data.getInteriorTemperature() > consigneTemperature;
		if (this.consigneAllumage != consigneAllumage) {
			// On mémorise la nouvelle consigne
			this.consigneAllumage = consigneAllumage;
			// On propage un événement
			notifyConsigneAllumageChanged(consigneAllumage);
		}
		
	}

	public void setTempConsigne(float tempConsigne) {
		// La température de consigne a changé
		if (tempConsigne != this.consigneTemperature) {
			// On enregistre la nouvelle consigne
			this.consigneTemperature = tempConsigne;
			// On propage un événement
			notifyConsigneTemperatureChanged(tempConsigne);
		}
	}

	@Override
	public void addListener(IRegulatorListener obs) {
		this.listeners.add(obs);
	}

	@Override
	public void removeListener(IRegulatorListener obs) {
		this.listeners.remove(obs);
	}

	@Override
	public void notifyConsigneTemperatureChanged(double tempConsigne) {
		if (debug) System.out.println("[Regulation] Consigne de température : " + tempConsigne);
		this.listeners.forEach(obs -> obs.onConsigneTemperatureChanged(tempConsigne));
	}

	@Override
	public void notifyConsigneAllumageChanged(boolean powerState) {
		if (debug) System.out.println("[Regulation] Consigne d'allumage : " + powerState);
		this.listeners.forEach(obs -> obs.onConsigneAllumageChanged(powerState));
	}

	@Override
	public void notifyAlertCondensation(boolean state) {
		if (debug) System.out.println("[Regulation] Alerte de condensation : " + state);
		this.listeners.forEach(obs -> obs.onAlertCondensationChanged(state));
	}

	@Override
	public void notifyAlertTemperatureGap(boolean state) {
		if (debug) System.out.println("[Regulation] Alerte d'écart de température : " + state);
		this.listeners.forEach(obs -> obs.onAlertTemperatureGapChanged(state));
	}

	public float getConsigneTemperature() {
		return consigneTemperature;
	}

	public boolean isConsigneAllumage() {
		return consigneAllumage;
	}

	public boolean isAlertCondensation() {
		return alertCondensation;
	}

	public boolean isAlertTempGap() {
		return alertTempGap;
	}
	
}

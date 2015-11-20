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

		// On ram�ne le taux d'humidit� entre 0 et 1
		double h = data.getHumidityRate() / 100;
		
		// On calcule la temp�rature de ros�e
		double tempRose = Math.pow(h , 1.0/8) * (112 + (0.9 * data.getInteriorTemperature())) + (0.1 * data.getExteriorTemperature()) - 112;
		
		// On d�tecte de la condensation
		boolean isCondensation = (tempRose >= data.getInteriorTemperature());
		if (isCondensation != alertCondensation) {
			// On m�morise le nouvel �tat
			alertCondensation = isCondensation;
			// En cas de changement d'�tat on envoie un event
			notifyAlertCondensation(isCondensation);
		}
		
		// On d�tecte les forts �carts de temp�rature
		double delta = data.getInteriorTemperature() - this.histoIn;
		boolean isTempGap = delta >= 2 || delta <= -2 && this.histoIn != 5000;
		if (isTempGap != alertTempGap) {
			// On m�morise le nouvel �tat
			alertTempGap = isTempGap;
			// En cas de changement on envoie un event
			notifyAlertTemperatureGap(isTempGap);
		}
		
		// TODO C'est �trange �a... le temps n'est pas pris en compte ?
		this.histoIn = data.getInteriorTemperature();
		
		// On d�termine l'�tat de la consigne d'allumage
		boolean consigneAllumage = data.getInteriorTemperature() > consigneTemperature;
		if (this.consigneAllumage != consigneAllumage) {
			// On m�morise la nouvelle consigne
			this.consigneAllumage = consigneAllumage;
			// On propage un �v�nement
			notifyConsigneAllumageChanged(consigneAllumage);
		}
		
	}

	public void setTempConsigne(float tempConsigne) {
		// La temp�rature de consigne a chang�
		if (tempConsigne != this.consigneTemperature) {
			// On enregistre la nouvelle consigne
			this.consigneTemperature = tempConsigne;
			// On propage un �v�nement
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
		if (debug) System.out.println("[Regulation] Consigne de temp�rature : " + tempConsigne);
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
		if (debug) System.out.println("[Regulation] Alerte d'�cart de temp�rature : " + state);
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

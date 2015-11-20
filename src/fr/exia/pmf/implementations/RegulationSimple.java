package fr.exia.pmf.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.exia.pmf.abstractions.IRegulator;
import fr.exia.pmf.abstractions.IRegulatorListener;
import fr.exia.pmf.model.Statement;

public class RegulationSimple implements IRegulator {
	
	private float consigneTemperature = 16.0f;
	private boolean consigneAllumage = false;
	
	private double histoIn;
	private Date histoDate;
	
	private boolean alertCondensation = false;
	private boolean alertTempGap = false;

	private List<IRegulatorListener> listeners;
	private boolean debug;
	private Date lastAllumageOn = null;
	
	/**
	 * Constructeur
	 */
	public RegulationSimple(boolean debug) {

		this.debug = debug;
		
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
		// Variation sup�rieure � X �C en 2 secondes
		boolean isTempGap = false;
		if (this.histoDate == null || new Date().getTime() - this.histoDate.getTime() < 2000) {
			// On check la variation
			isTempGap = (data.getInteriorTemperature() - this.histoIn > .4);
			// Et on m�moire les nouvelles donn�es
			this.histoDate = new Date();
			this.histoIn = data.getInteriorTemperature();
		}
		if (isTempGap != alertTempGap) {
			// On m�morise le nouvel �tat
			alertTempGap = isTempGap;
			// En cas de changement on envoie un event
			notifyAlertTemperatureGap(isTempGap);
		}
		
		// On d�termine l'�tat de la consigne d'allumage
		boolean consigneAllumage = data.getInteriorTemperature() > consigneTemperature;
		// On v�rifie que la consigne a chang�e
		if (this.consigneAllumage != consigneAllumage) {
			// On veut allumer le frigo
			if (consigneAllumage) {
				// Si on a allum� le frigo il y a moins de 2 secondes on ne le rallume pas
				// On simule un syst�me d'�conomie d'�nergie !
				if (lastAllumageOn != null && new Date().getTime() - lastAllumageOn.getTime() < 200) {
					return;
				}
				lastAllumageOn = new Date(); 
			}
			// On m�morise la nouvelle consigne
			this.consigneAllumage = consigneAllumage;
			// On propage un �v�nement
			notifyConsigneAllumageChanged(consigneAllumage);
		}
		
	}

	@Override
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

	@Override
	public float getConsigneTemperature() {
		return consigneTemperature;
	}

	@Override
	public boolean isConsigneAllumage() {
		return consigneAllumage;
	}

	@Override
	public boolean isAlertLiquefaction() {
		return alertCondensation;
	}

	@Override
	public boolean isAlertTempGap() {
		return alertTempGap;
	}
	
}

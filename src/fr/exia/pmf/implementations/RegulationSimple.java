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
	private Date lastAllumageOn = null;
	
	/**
	 * Constructeur
	 */
	public RegulationSimple() {
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
		// Variation supérieure à 1 °C en 3 secondes
		boolean isTempGap = alertTempGap;
		if (this.histoDate == null || (new Date().getTime() - this.histoDate.getTime()) >= 3000) {
			// On check la variation
			if (this.histoDate != null)
				isTempGap = (data.getInteriorTemperature() - this.histoIn > 1);
			// Et on mémorise les nouvelles données
			this.histoDate = new Date();
			this.histoIn = data.getInteriorTemperature();
		}
		if (isTempGap != alertTempGap) {
			// On mémorise le nouvel état
			alertTempGap = isTempGap;
			// En cas de changement on envoie un event
			notifyAlertTemperatureGap(isTempGap);
		}
		
		// On détermine l'état de la consigne d'allumage
		// On compare à la consigne -5% pour laisser la température refroidir un peu plus
		boolean consigneAllumage = data.getInteriorTemperature() > consigneTemperature * .95;
		// On vérifie que la consigne a changée
		if (this.consigneAllumage != consigneAllumage) {
			// On veut allumer le frigo
			if (consigneAllumage) {
				// Si on a allumé le frigo il y a moins de 2 secondes on ne le rallume pas
				// On simule un système d'économie d'énergie !
				if (lastAllumageOn != null && (new Date().getTime() - lastAllumageOn.getTime()) < 2000) {
					return;
				}
				lastAllumageOn = new Date(); 
			}
			// On mémorise la nouvelle consigne
			this.consigneAllumage = consigneAllumage;
			// On propage un événement
			notifyConsigneAllumageChanged(consigneAllumage);
		}
		
	}

	@Override
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
		System.out.println("[Regulation] Consigne de température : " + tempConsigne);
		this.listeners.forEach(obs -> obs.onConsigneTemperatureChanged(tempConsigne));
	}

	@Override
	public void notifyConsigneAllumageChanged(boolean powerState) {
		System.out.println("[Regulation] Consigne d'allumage : " + powerState);
		this.listeners.forEach(obs -> obs.onConsigneAllumageChanged(powerState));
	}

	@Override
	public void notifyAlertCondensation(boolean state) {
		System.out.println("[Regulation] Alerte de condensation : " + state);
		this.listeners.forEach(obs -> obs.onAlertCondensationChanged(state));
	}

	@Override
	public void notifyAlertTemperatureGap(boolean state) {
		System.out.println("[Regulation] Alerte d'écart de température : " + state);
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

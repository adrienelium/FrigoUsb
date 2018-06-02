package fr.exia.pmf.implementations;

import fr.exia.pmf.model.Statement;

public class RegulationPID extends RegulationTOR {
	
	protected double Kp;
	protected double Ki;
	protected double Kd;
	protected SizedStack queue;
	
	public RegulationPID(double Kp, double Ki, int Kd) {
		this(Kp, Ki, Kd, 50);
	}

	public RegulationPID(double Kp, double Ki, int Kd, int queueLength) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.queue = new SizedStack(queueLength);
	}

	protected void applyConsigne(Statement data) {
		System.out.println(data);
		
		double pwr = (consigneTemperature - data.getInteriorTemperature()) * Kp
				+ (consigneTemperature - queue.getLast()) * Kd 
				+ (consigneTemperature - queue.getAverage()) * Ki;
		
		System.out.println(pwr);
		
//		// On compare à la consigne -5% pour laisser la température refroidir un peu plus
//		// Il s'agit de la valeur d'hystérésis
//		boolean consigneAllumage = data.getInteriorTemperature() > consigneTemperature * .95;
//		// On vérifie que la consigne a changée
//		if (this.consigneAllumage != consigneAllumage) {
//			// On veut allumer le frigo
//			if (consigneAllumage) {
//				// Si on a allumé le frigo il y a moins de 2 secondes on ne le rallume pas
//				// On simule un système d'économie d'énergie !
//				if (lastAllumageOn != null && (new Date().getTime() - lastAllumageOn.getTime()) < 2000) {
//					return;
//				}
//				lastAllumageOn = new Date(); 
//			}
//			// On mémorise la nouvelle consigne
//			this.consigneAllumage = consigneAllumage;
//			// On propage un événement
//			notifyConsigneAllumageChanged(consigneAllumage);
//		}
	}

}

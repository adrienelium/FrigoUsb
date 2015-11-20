package fr.exia.pmf.abstractions;

public interface IRegulatorListener {

	/**
	 * Quand la température de consigne a changé. Température en °C.
	 */
	public void onConsigneTemperatureChanged(double temp);
	
	/**
	 * Quand le régulateur change sa consigne d'allumage.
	 */
	public void onConsigneAllumageChanged(boolean enabled);

	/**
	 * Quand l'alerte de condensation change d'état.
	 */
	public void onAlertCondensationChanged(boolean state);

	/**
	 * Quand l'alerte d'écart de température change d'état.
	 */
	public void onAlertTemperatureGapChanged(boolean state);
	
}

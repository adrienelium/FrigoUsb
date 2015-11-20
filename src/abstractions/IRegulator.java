package abstractions;

public interface IRegulator extends IConnectionListener {

	/**
	 * Ajouter un observateur.
	 */
	public void addListener(IRegulatorListener obs);
	
	/**
	 * Retirer un observateur.
	 */
	public void removeListener(IRegulatorListener obs);
	
	/**
	 * Notifier les observateurs quand une nouvelle température de consigne a été donnée.
	 */
	public void notifyConsigneTemperatureChanged(double tempConsigne);
	
	/**
	 * Notifier les observateurs quand la consigne d'allumage change.
	 */
	public void notifyConsigneAllumageChanged(boolean powerState);
	
	/**
	 * Notifier les observateurs quand l'alerte de condensation change d'état.
	 */
	public void notifyAlertCondensation(boolean state);
	
	/**
	 * Notifier les observateurs quand l'alerte d'écart de température change d'état.
	 */
	public void notifyAlertTemperatureGap(boolean state);
	
}

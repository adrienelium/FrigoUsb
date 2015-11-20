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
	 * Notifier les observateurs quand une nouvelle temp�rature de consigne a �t� donn�e.
	 */
	public void notifyConsigneTemperatureChanged(double tempConsigne);
	
	/**
	 * Notifier les observateurs quand la consigne d'allumage change.
	 */
	public void notifyConsigneAllumageChanged(boolean powerState);
	
	/**
	 * Notifier les observateurs quand l'alerte de condensation change d'�tat.
	 */
	public void notifyAlertCondensation(boolean state);
	
	/**
	 * Notifier les observateurs quand l'alerte d'�cart de temp�rature change d'�tat.
	 */
	public void notifyAlertTemperatureGap(boolean state);
	
}

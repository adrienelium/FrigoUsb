package fr.exia.pmf.abstractions;

import fr.exia.pmf.model.Statement;

public interface IDataConnection {
	
	/**
	 * Initialise le système.
	 * 
	 * @throws Exception Si le système ne peut être intialisé.
	 */
	public void init() throws Throwable;
	
	/**
	 * Démarrer le système.
	 */
	public void start();
	
	/**
	 * Ajouter un observateur.
	 */
	public void addListener(IDataConnectionListener obs);
	
	/**
	 * Retirer un observateur.
	 */
	public void removeListener(IDataConnectionListener obs);
	
	/**
	 * Notifier les observateurs qu'une nouvelle donnée a été lue.
	 */
	public void notifyListeners(Statement data);
	
	/**
	 * Activer ou désactiver l'alimentation électrique du réfrigérateur.
	 */
	public void setPowerEnabled(boolean value);
	
	/**
	 * Renvoie TRUE si l'alimentation électrique est activée.
	 */
	public boolean isPowerEnabled();

}

package fr.exia.pmf.abstractions;

import fr.exia.pmf.model.Statement;

public interface IDataConnectionListener {
	
	/**
	 * Quand une nouvelle donn�e est lue.
	 */
	public void onNewStatementRead(Statement data);

	/**
	 * Quand l'�tat d'allumage du r�frig�rateur a chang�.
	 */
	public void onPowerStatusChanged(boolean powerOn);
	
}

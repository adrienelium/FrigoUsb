package fr.exia.pmf.abstractions;

import fr.exia.pmf.model.Statement;

public interface IDataConnectionListener {
	
	/**
	 * Quand une nouvelle donnée est lue.
	 */
	public void onNewStatementRead(Statement data);
	
}

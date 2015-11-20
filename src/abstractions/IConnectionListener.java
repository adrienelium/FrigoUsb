package abstractions;

import modele.Statement;

public interface IConnectionListener {
	
	/**
	 * Quand une nouvelle donnée est lue.
	 */
	public void onNewStatementRead(Statement data);
	
}

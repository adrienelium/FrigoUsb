package abstractions;

import modele.Statement;

public interface IConnectionListener {
	
	/**
	 * Quand une nouvelle donn�e est lue.
	 */
	public void onNewStatementRead(Statement data);
	
}

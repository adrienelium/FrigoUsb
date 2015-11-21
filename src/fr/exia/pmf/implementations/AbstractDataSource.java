package fr.exia.pmf.implementations;

import java.util.ArrayList;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.model.Statement;

public abstract class AbstractDataSource implements IDataConnection {

	/** Les listeners de la source de données */
	private ArrayList<IDataConnectionListener> listeners;
	
	/** Etat d'activation de l'alimentation du réfrigérateur */
	protected boolean powerEnabled = false;
	
	public AbstractDataSource() {
		listeners = new ArrayList<IDataConnectionListener>();
	}
	
	@Override
	public void addListener(IDataConnectionListener obs) {
		listeners.add(obs);
	}
	
	@Override
	public void removeListener(IDataConnectionListener obs) {
		listeners.remove(obs);
	}

	@Override
	public void notifyListeners(Statement data) {
		listeners.forEach(observer -> observer.onNewStatementRead(data));
	}
	
	@Override
	public void notifyListeners(boolean powerOn) {
		listeners.forEach(observer -> observer.onPowerStatusChanged(powerOn));
	}

	protected void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			System.exit(-2);
		}
	}
	
	@Override
	public boolean isPowerEnabled() {
		return this.powerEnabled;
	}
	
}

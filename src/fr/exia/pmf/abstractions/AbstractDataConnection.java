package fr.exia.pmf.abstractions;

import java.util.ArrayList;
import java.util.Date;

import fr.exia.pmf.model.Statement;

public abstract class AbstractDataConnection implements IDataConnection {

	/** Les listeners de la source de données */
	private ArrayList<IDataConnectionListener> listeners;
	
	/** Etat d'activation de l'alimentation du réfrigérateur */
	protected boolean powerEnabled = false;
	
	// Temps en secondes
	private long tempsAllumage = 0;
	private Date powerOnTime;
	
	public AbstractDataConnection() {
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
		// On incrémente le temps d'allumage du frigo
		if (powerOn) {
			this.powerOnTime = new Date();
		}
		else {
			this.tempsAllumage = getPowerUptime();
			this.powerOnTime = null;
		}
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
	
	/**
	 * @return En secondes
	 */
	public long getPowerUptime() {
		long time = tempsAllumage;
		if (powerOnTime != null) {
			time += (new Date().getTime() - powerOnTime.getTime()) / 1000;
		}
		return time;
	}
}

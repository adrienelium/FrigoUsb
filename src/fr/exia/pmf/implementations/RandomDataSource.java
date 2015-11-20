package fr.exia.pmf.implementations;

import java.util.ArrayList;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.model.Statement;

public class RandomDataSource extends Thread implements IDataConnection {

	private ArrayList<IDataConnectionListener> listeners;
	private boolean enabled;
	
	public RandomDataSource() {
		listeners = new ArrayList<IDataConnectionListener>();
	}

	@Override
	public void init() throws Throwable {
	}
	
	@Override
	public void run() {
		
		while (!Thread.interrupted()) {
			
			// On génére de fausses données
			notifyListeners(new Statement(
					Math.random() * 100,
					Math.random() * 30,
					Math.random() * 30
			));
			
			sleep();
			
		}
		
	}

	protected void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.exit(-2);
		}
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
	public void setPowerEnabled(boolean value) {
		if (this.enabled != value) {
			this.enabled = value;
		}
	}

	@Override
	public boolean isPowerEnabled() {
		return enabled;
	}

}

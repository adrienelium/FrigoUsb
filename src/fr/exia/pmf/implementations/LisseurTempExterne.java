package fr.exia.pmf.implementations;

import java.util.ArrayList;
import java.util.List;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.model.Statement;

/**
 * Lisseur de la T° externe.
 */
public class LisseurTempExterne implements IDataConnection, IDataConnectionListener {
	
	private IDataConnection source;
	
	private List<Double> values;

	private int length;
	
	private ArrayList<IDataConnectionListener> listeners;

	public LisseurTempExterne(IDataConnection source, int length) {
		this.source = source;
		this.length = length;
		values = new ArrayList<Double>();
		listeners = new ArrayList<IDataConnectionListener>();
	}
	
	protected double add(double value) {
		values.add(value);
		while (values.size() > length) values.remove(0);
		float sum = 0;
		for (double val : values) sum += val;
		return sum / (double)values.size();
	}
	
	protected double add_debug(double value) {
		values.add(value);
		while (values.size() > length) values.remove(0);
		double sum = 0;
		double min = Float.MAX_VALUE;
		double max = Float.MIN_VALUE;
		for (double val : values) {
			sum += val;
			if (val < min) min = val;
			if (val > max) max = val;
		}
		double avg = sum / (double)values.size();
		System.out.println("[Lissage] Added="+value+" Length="+values.size()+" Min="+min+" Max="+max+" Average="+avg);
		return avg;
	}
	
	@Override
	public void notifyListeners(Statement data) {
		// On réécrit l'état avec une moyenne
		final Statement newData = new Statement(
				data.getHumidityRate(),
				data.getInteriorTemperature(),
				//add_debug(data.getExteriorTemperature())
				add(data.getExteriorTemperature())
		);
		listeners.forEach(observer -> observer.onNewStatementRead(newData));
	}

	@Override
	public void init() throws Throwable {
		this.source.init();
	}

	@Override
	public void start() {
		// On inscrit cet objet comme listener de la source
		this.source.addListener(this);
		// On démarre la source
		this.source.start();
	}

	@Override
	public void stop() {
		// On se retire des observateurs
		this.source.removeListener(this);
		// Et on arrête la source
		this.source.stop();
	}

	@Override
	public void addListener(IDataConnectionListener obs) {
		this.listeners.add(obs);
	}

	@Override
	public void removeListener(IDataConnectionListener obs) {
		this.listeners.remove(obs);
	}

	@Override
	public void notifyListeners(boolean powerOn) {
		listeners.forEach(observer -> observer.onPowerStatusChanged(powerOn));
	}

	@Override
	public void setPowerEnabled(boolean value) {
		this.source.setPowerEnabled(value);
	}

	@Override
	public boolean isPowerEnabled() {
		return this.source.isPowerEnabled();
	}

	@Override
	public long getPowerUptime() {
		return this.source.getPowerUptime();
	}

	@Override
	public void onNewStatementRead(Statement data) {
		// On redirige vers nos observateurs à nous
		notifyListeners(data);
	}

	@Override
	public void onPowerStatusChanged(boolean powerOn) {
		// On redirige vers nos observateurs à nous
		notifyListeners(powerOn);
	}

}

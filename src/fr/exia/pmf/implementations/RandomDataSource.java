package fr.exia.pmf.implementations;

import fr.exia.pmf.model.Statement;

public class RandomDataSource extends AbstractDataSource implements Runnable {

	private Thread thread;
	
	public RandomDataSource() {
		thread = new Thread(this);
	}

	@Override
	public void init() throws Throwable {
	}
	
	@Override
	public void start() {
		thread.start();
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
			
			sleep(500);
			
		}
		
	}

	@Override
	public void setPowerEnabled(boolean powerOn) {
		if (this.powerEnabled != powerOn) {
			this.powerEnabled = powerOn;
			notifyListeners(powerOn);
		}
	}

	@Override
	public void stop() {
		thread.interrupt();
	}

}

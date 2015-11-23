package fr.exia.pmf.model;

public class Statement {
	
	private double humidity;
	private double tempOut;
	private double tempIn;

	/**
	 * @param humidity En pourcentage d'humidit�.
	 * @param tempIn   La temp�rature int�rieure.
	 * @param tempOut  Le temp�rature ext�rieure.
	 */
	public Statement(double humidity, double tempIn, double tempOut) {
		this.humidity = humidity;
		this.tempOut = tempOut;
		this.tempIn = tempIn;
	}
	
	/**
	 * @return the h
	 */
	public double getHumidityRate() {
		return humidity;
	}

	/**
	 * @return the ext
	 */
	public double getExteriorTemperature() {
		return tempOut;
	}

	/**
	 * @return the inte
	 */
	public double getInteriorTemperature() {
		return tempIn;
	}
	
	@Override
	public String toString() {
		return String.format("Tin=%s�C Tout=%s�C H=%s%%", tempIn, tempOut, humidity);
	}

}

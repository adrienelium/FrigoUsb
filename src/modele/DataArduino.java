package modele;

public class DataArduino {
	private float h;
	private float ext;
	private float inte;

	public DataArduino(float h2,float out,float in) {
		this.h = h2;
		this.ext = out;
		this.inte = in;
	}
	
	/**
	 * @return the h
	 */
	public float getH() {
		return h;
	}

	/**
	 * @return the ext
	 */
	public float getExt() {
		return ext;
	}

	/**
	 * @return the inte
	 */
	public float getInte() {
		return inte;
	}

	/**
	 * @param h the h to set
	 */
	public void setH(int h) {
		this.h = h;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(int ext) {
		this.ext = ext;
	}

	/**
	 * @param inte the inte to set
	 */
	public void setInte(int inte) {
		this.inte = inte;
	}

}

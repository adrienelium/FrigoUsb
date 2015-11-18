package controleur;

import javax.swing.JOptionPane;
import modele.SerialClass.serialReader;

public class Regulation implements serialReader{
	
	private float tempConsigne;
	private boolean allumer;
	
	private float h;
	private float in;
	@SuppressWarnings("unused")
	private float out;
	
	private float histoIn;
	
	public Regulation() {
		
		
		
		
		setAllumer(true);
		tempConsigne = 18;
		setHistoIn(5000);
	}
	
	@Override
	public void afficherNouvelleDonnees(float h, float in, float out) {
		
		this.h= h;
		this.in = in;
		this.out = out;
		
	}
	
	public void setHistoIn(float histo)
	{
		this.histoIn = histo;
	}

	public float getTempConsigne() {
		return tempConsigne;
	}

	public void setTempConsigne(float tempConsigne) {
		this.tempConsigne = tempConsigne;
	}

	public boolean isAllumer() {
		return allumer;
	}

	public void setAllumer(boolean allumer) {
		this.allumer = allumer;
	}

	public void decider() {
		
		double h = this.h / 100;
		double tempRose = Math.pow(h , 1.0/8) * (112 + (0.9*this.in)) + (0.1*this.in) - 112;
		
		if (tempRose >= this.in)
		{
			JOptionPane.showMessageDialog(null, "Attention, Apparition de condensation !");
		}
		
		
		float nbr = this.in - this.histoIn;
		if(nbr >= 2 || nbr <= -2 && this.histoIn != 5000)
		{
			JOptionPane.showMessageDialog(null, "Attention, écart de température important detecté !");
		}
		
		this.histoIn = this.in;
		
		System.out.println("Température de rosé : " + tempRose);
		
		if (in > tempConsigne )
		{
			setAllumer(true);
		}
		else
		{
			setAllumer(false);
		}
		
	}
	
	
	
	
	
}

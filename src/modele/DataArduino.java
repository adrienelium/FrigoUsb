package modele;

import java.lang.reflect.Array;

public class DataArduino {
	private static float[] humidite = new float[500];
	private static int tailleH = 0;
	
	private static float[] tempExt = new float[500];
	private static int tailleExt = 0;
	
	private static float[] tempInt = new float[500];
	private static int tailleInt = 0;
	
	@SuppressWarnings("null")
	public static float[] getHumidite() {
		float[] tab = null;
		float[] valeurAretourner = humidite;
		
		int tailleInit = Array.getLength(valeurAretourner);
		if (tailleInit - 8 <= 0)
		{
			tailleInit = 0;
		}
		
		int e = 0;
		for (int i = tailleInit ; i < Array.getLength(valeurAretourner) ; i++)
		{
			tab[e] = valeurAretourner[i];	
			e++;
		}
		
		return tab;
	}
	
	@SuppressWarnings("null")
	public static float[] getTempExt() {
		float[] tab = null;
		float[] valeurAretourner = tempExt;
		
		int tailleInit = Array.getLength(valeurAretourner);
		if (tailleInit - 8 <= 0)
		{
			tailleInit = 0;
		}
		
		int e = 0;
		for (int i = tailleInit ; i < Array.getLength(valeurAretourner) ; i++)
		{
			tab[e] = valeurAretourner[i];	
			e++;
		}
		
		return tab;
	}
	
	@SuppressWarnings("null")
	public static float[] getTempInt() {
		float[] tab = null;
		float[] valeurAretourner = tempInt;
		
		int tailleInit = Array.getLength(valeurAretourner);
		if (tailleInit - 8 <= 0)
		{
			tailleInit = 0;
		}
		
		int e = 0;
		for (int i = tailleInit ; i < Array.getLength(valeurAretourner) ; i++)
		{
			tab[e] = valeurAretourner[i];	
			e++;
		}
		
		return tab;
	}
	
	public static void addHumidity(float taux)
	{
		humidite[tailleH] = taux;
		tailleH++;
	}
	
	public static void addTempExt(float temp)
	{
		tempExt[tailleExt] = temp;
		tailleExt++;
	}
	
	public static void addTempInt(float temp)
	{
		tempInt[tailleInt] = temp;
		tailleInt++;
	}
	
	

}

package controleur;

import java.io.*;
import java.io.InputStreamReader;

import modele.SerialClass;
import vue.Windows;


public class Main{
	
	public static int b = 1;
	public static BufferedReader input;
	public static OutputStream output;

	public static synchronized void writeData(String data) {
		System.out.println("Sent: " + data);
		try {
			output.write(data.getBytes());
		} catch (Exception e) {
			System.out.println("could not write to port : " + e.getMessage());
		}
	}

	public static void main(String[] ag)
	{
		Regulation regul = new Regulation();
		Windows fen = new Windows(regul);
		
		try
		{
			SerialClass obj = new SerialClass(fen,regul);
			
			int c=0;
			obj.initialize();
			
			

		}
		catch(Exception e){}

	}



}

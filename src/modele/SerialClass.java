package modele;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import vue.Observateur;

import java.util.ArrayList;
import java.util.Enumeration;
import controleur.Regulation;
public class SerialClass implements SerialPortEventListener,Observable {
	private int ordre = 1;
	
	private float h = 0;
	private float in = 0;
	private float out = 0;
	
	private ArrayList<Observateur> mesObservateur;
	
	private Regulation regul;
	
	public SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {
			"COM12", // Windows
	};

	public static BufferedReader input;
	public static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 9600;

	public SerialClass(Regulation regul) {
		mesObservateur = new ArrayList<Observateur>();
		this.regul = regul;
	}

	public void initialize() {
		CommPortIdentifier portId = null;
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
		
			if (currPortId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				portId = currPortId;
				PORT_NAMES[0] = portId.getName();
				System.out.println(portId.getName());
			}
			
			
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			char ch = 1;
			output.write(ch);


			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				
				
				//System.out.println("Données reçu H : " + inputLine + " %");
				switch (ordre) {
				case 1:
					System.out.println("Données reçu H : " + inputLine + " %");
					//DataArduino.addHumidity(Float.parseFloat(inputLine));
					h = Float.parseFloat(inputLine);
					break;
				case 2:
					System.out.println("Données reçu TempInt : " + inputLine + "°C");
					//DataArduino.addTempInt(Float.parseFloat(inputLine));
					in = Float.parseFloat(inputLine);
					break;
				case 3:
					System.out.println("Données reçu TempExt : " + inputLine + "°C");
					//DataArduino.addTempExt(Float.parseFloat(inputLine));
					out = Float.parseFloat(inputLine);
					break;
				default:
					break;
				}
				ordre++;
				if (ordre == 4)
				{
					ordre = 1;
					sendDataToView();
				}
				
				System.out.println();
				
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

	}

	private void sendDataToView() {
		notifyObservateurs();
		regul.afficherNouvelleDonnees(h, in, out);		
		
		regul.decider();
		
		if(regul.isAllumer())
		{
			writeData("1");
		}
		else
		{
			writeData("0");
		}
	}

	public static void writeData(String data) {
		System.out.println("Sent: " + data);
		try {
			output.write(data.getBytes());
		} catch (Exception e) {
			System.out.println("could not write to port");
		}
	}

	
	
	
	public interface serialReader
	{
		public void afficherNouvelleDonnees(float h,float in,float out);
	}




	@Override
	public void addObservateur(Observateur obs) {
		// TODO Auto-generated method stub
		mesObservateur.add(obs);
	}

	@Override
	public void notifyObservateurs() {
		// TODO Auto-generated method stub
		for (Observateur obs:mesObservateur) {
			obs.afficherNotification(new DataArduino(this.h, this.out, this.in));
		}
	}


}


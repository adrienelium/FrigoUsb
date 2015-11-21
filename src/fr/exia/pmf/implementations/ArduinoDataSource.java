package fr.exia.pmf.implementations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import fr.exia.pmf.abstractions.IDataConnection;
import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.model.Statement;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ArduinoDataSource implements SerialPortEventListener, IDataConnection {
	
	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	
	/** Default bits per second for COM port */
	public static final int DATA_RATE = 9600;
	
	/** Identifiant des trames de données */
	private static final String DataIdentifier = "D:";
	
	/** Identifiant des trames de feedback */
	private static final String FeedbackIdentifier = "R:";

	/** Message d'erreur en cas de buffer vide */
	private static final String EmptyBufferErrorMessage = "Underlying input stream returned zero bytes";
	
	/** Les listeners de la source de données */
	private ArrayList<IDataConnectionListener> listeners;
	
	/** Liaison série avec l'arduino */
	public SerialPort serialPort;
	
	/** Flux de lecture sur la liaison série */
	public BufferedReader input;
	
	/** Flux d'écriture sur la liaison série */
	public OutputStream output;

	/** Etat d'activation de l'alimentation du réfrigérateur */
	private boolean powerEnabled;

	/**
	 * Constructeur
	 */
	public ArduinoDataSource() {
		listeners = new ArrayList<IDataConnectionListener>();
	}
	
	@Override
	public void init() throws Throwable {
		
		CommPortIdentifier portId = null;
		
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (currPortId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				portId = currPortId;
				System.out.println("[DataSource] Arduino found on serial port: " + portId.getName());
			}
		}
		
		if (portId == null) {
			throw new IOException("Could not find COM port.");
		}

		// open serial port, and use class name for the appName.
		serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

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
	}

	public void start() {
		try {
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		}
		catch (TooManyListenersException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		
		// Uniquement les évents d'arrivée de données
		if (oEvent.getEventType() != SerialPortEvent.DATA_AVAILABLE) {
			return;
		}
		
		// On tente de lire une trame
		try {
			String inputLine = input.readLine();

			// Trame de données
			if (inputLine.startsWith(DataIdentifier)) {
				// On split la chaîne
				String[] tokens = inputLine.substring(2).split(";", 4);
				// On devrait avoir 4 tokens
				if (tokens.length != 4) {
					System.err.println("[Arduino] Invalid data message (length)");
					return;
				}
				// Conversion en floats
				float[] values = parseFloatArray(tokens);
				// Vérification CRC
				if (!tokens[3].equals("nan") && values[0] + values[1] + values[2] != values[3]) {
					System.err.println("[Arduino] Invalid data message (crc)");
				}
				// On prévient les listeners
				notifyListeners(new Statement(values[0], values[1], values[2]));
			}
			
			// Trame de feedback après l'envoi d'une commande
			else if (inputLine.startsWith(FeedbackIdentifier)) {
				int returnCode = Integer.parseInt(inputLine.substring(2));
				if (returnCode == 1) {
					System.out.println("[Arduino] Réfrigérateur ON");
					// TODO Cela pourrait lever un event
				}
				else if (returnCode == 2) {
					System.out.println("[Arduino] Réfrigérateur OFF");
					// TODO Cela pourrait lever un event
				}
				else {
					System.out.println("[Arduino] Invalid feedback code: " + returnCode);
				}
			}
			
			// Trame inconnue ?!
			else {
				System.err.println("[Arduino] Invalid unknown message");
			}
		}
		catch (IOException e) {
			if (e.getMessage().equals(EmptyBufferErrorMessage)) {
				// On laisse passer ce genre d'erreur
			}
			else {
				System.err.println(String.format("[Arduino] Error %s", e.toString()));
			}
		}
		catch (Throwable e) {
			System.err.println(String.format("[Arduino] Error %s", e.toString()));
			//e.printStackTrace();
		}

	}

	private float[] parseFloatArray(String[] tokens) {
		float[] r = new float[tokens.length];
		int i = 0;
		for (String tok : tokens) {
			r[i++] = tok.toLowerCase().equals("nan") ? -1 : Float.parseFloat(tok);
		}
		return r;
	}

	public void writeData(String data) {
		try {
			output.write(data.getBytes());
		}
		catch (Exception e) {
			System.err.println(String.format("Could not write data to serial, %s : %s", e.getClass().getSimpleName(), e.getMessage()));
		}
	}

	@Override
	public void addListener(IDataConnectionListener obs) {
		listeners.add(obs);
	}

	@Override
	public void notifyListeners(Statement data) {
		listeners.forEach(observer -> observer.onNewStatementRead(data));
	}

	@Override
	public void removeListener(IDataConnectionListener obs) {
		listeners.remove(obs);
	}

	@Override
	public void setPowerEnabled(boolean value) {
		// Uniquement en cas de changement
		if (this.powerEnabled != value) {
			// On change l'état
			this.powerEnabled = value;
			// On écrit la commande sur le bus série
			writeData(this.powerEnabled ? "1" : "0");
		}
	}

	@Override
	public boolean isPowerEnabled() {
		return this.powerEnabled;
	}

}
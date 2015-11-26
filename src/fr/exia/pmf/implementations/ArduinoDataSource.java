package fr.exia.pmf.implementations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import fr.exia.pmf.abstractions.AbstractDataConnection;
import fr.exia.pmf.model.Statement;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ArduinoDataSource extends AbstractDataConnection implements SerialPortEventListener {
	
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
	
	/** Liaison série avec l'arduino */
	public SerialPort serialPort;
	
	/** Flux de lecture sur la liaison série */
	public BufferedReader input;
	
	/** Flux d'écriture sur la liaison série */
	public OutputStream output;

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

		// Open the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();
	}

	@Override
	public void start() {
		try {
			System.out.println("[Arduino] Start");
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		}
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public synchronized void stop() {
		if (serialPort != null) {
			System.out.println("[Arduino] Stop");
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	@Override
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		
		// Uniquement les évents d'arrivée de données
		if (oEvent.getEventType() != SerialPortEvent.DATA_AVAILABLE) {
			System.out.println("[Arduino] Event : " + oEvent.getEventType());
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
//					System.err.println("Read " + inputLine);
					System.err.println("[Arduino] Invalid data message (crc)");
//					System.err.println(String.format("%s + %s + %s != %s", values[0],values[1],values[2],values[3]));
				}
				// On prévient les listeners
				notifyListeners(new Statement(values[0], values[1], values[2]));
			}
			
			// Trame de feedback après l'envoi d'une commande
			else if (inputLine.startsWith(FeedbackIdentifier)) {
				int returnCode = Integer.parseInt(inputLine.substring(2));
				if (returnCode == 1) {
					System.out.println("[Arduino] Réfrigérateur ON");
					notifyListeners(true);
				}
				else if (returnCode == 2) {
					System.out.println("[Arduino] Réfrigérateur OFF");
					notifyListeners(false);
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
	public void setPowerEnabled(boolean value) {
		// Uniquement en cas de changement
		if (this.powerEnabled != value) {
			// On change l'état
			this.powerEnabled = value;
			// On écrit la commande sur le bus série
			writeData(this.powerEnabled ? "1" : "2");
		}
	}

}
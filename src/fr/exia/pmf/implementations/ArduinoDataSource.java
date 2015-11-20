package fr.exia.pmf.implementations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import fr.exia.pmf.abstractions.IConnection;
import fr.exia.pmf.abstractions.IConnectionListener;
import fr.exia.pmf.model.Statement;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ArduinoDataSource implements SerialPortEventListener, IConnection {
	
	private int ordre = 1;
	
	private float h = 0;
	private float in = 0;
	private float out = 0;
	
	private ArrayList<IConnectionListener> listeners;
	
	public SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {
			"COM12", // Windows
	};

	public BufferedReader input;
	public OutputStream output;

	private boolean enabled;
	/** Milliseconds to block while waiting for port open */
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 9600;

	public ArduinoDataSource() {
		listeners = new ArrayList<IConnectionListener>();
	}
	
	@Override
	public void init() throws Throwable {
		
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
			throw new Exception("Could not find COM port.");
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


		} catch (Exception e) {
			System.err.println(e.toString());
		}
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
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				
				
				//System.out.println("Données reçu H : " + inputLine + " %");
				switch (ordre) {
				case 1:
//					System.out.println("Données reçu H : " + inputLine + " %");
					//DataArduino.addHumidity(Float.parseFloat(inputLine));
					h = Float.parseFloat(inputLine);
					break;
				case 2:
//					System.out.println("Données reçu TempInt : " + inputLine + "°C");
					//DataArduino.addTempInt(Float.parseFloat(inputLine));
					in = Float.parseFloat(inputLine);
					break;
				case 3:
//					System.out.println("Données reçu TempExt : " + inputLine + "°C");
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
					sendDataObservers();
				}
				
				System.out.println();
				
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

	}

	private void sendDataObservers() {
		
		// On prévient les listeners (la vue et le système de régulation)
		notifyListeners(new Statement(h, out, in));
		
		// Couplage fort : non, on va passer par l'observer
//		regul.afficherNouvelleDonnees(h, in, out);		
//		regul.decider();
		
		// Prise de décision : non, c'est à la régulation de faire les actions,
		// et à cette classe de fournir la méthode writeData() uniquement.
//		if(regul.isAllumer())
//		{
//			writeData("1");
//		}
//		else
//		{
//			writeData("0");
//		}
		
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
	public void addListener(IConnectionListener obs) {
		listeners.add(obs);
	}

	@Override
	public void notifyListeners(Statement data) {
		listeners.forEach(observer -> observer.onNewStatementRead(data));
	}

	@Override
	public void removeListener(IConnectionListener obs) {
		listeners.remove(obs);
	}

	@Override
	public void setPowerEnabled(boolean value) {
		// Uniquement en cas de changement
		if (this.enabled != value) {
			// On change l'état
			this.enabled = value;
			// On écrit la commande sur le bus série
			writeData(this.enabled ? "1" : "0");
		}
	}

	@Override
	public boolean isPowerEnabled() {
		return this.enabled;
	}

}
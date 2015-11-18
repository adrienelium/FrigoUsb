package vue;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controleur.Listenner;



public class Fenetre {

	private Frame mainFrame;
	private Label headerLabel;
	private Label statusLabel;
	private Panel controlPanel;


	public Fenetre()
	{

		mainFrame = new Frame("Java AWT Examples");



		mainFrame.setSize(1000,600);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setLayout(new GridLayout(3, 1));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});    
		headerLabel = new Label();
		headerLabel.setAlignment(Label.CENTER);
		statusLabel = new Label();        
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(350,100);

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());

		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		
		
		headerLabel.setText("Control in action: Button"); 

	      Button okButton = new Button("OK");
	      Button submitButton = new Button("Submit");
	      Button cancelButton = new Button("Cancel");

	      okButton.setActionCommand("OK");
	      submitButton.setActionCommand("Submit");
	      cancelButton.setActionCommand("Cancel");

	      okButton.addActionListener(new Listenner()); 
	      submitButton.addActionListener(new Listenner()); 
	      cancelButton.addActionListener(new Listenner()); 

	      controlPanel.add(okButton);
	      controlPanel.add(submitButton);
	      controlPanel.add(cancelButton);       

	      mainFrame.setVisible(true);
		
		
		mainFrame.setVisible(true);  

	}



}

package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Listenner implements ActionListener{
	
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();  
        if( command.equals( "OK" ))  {
        	JOptionPane.showMessageDialog(null,"OK");
        }
        else if( command.equals( "Submit" ) )  {
        	JOptionPane.showMessageDialog(null,"Submit");
        }
        else  {
        	JOptionPane.showMessageDialog(null,"Autre");
        }  	
     }	
}

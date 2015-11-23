package fr.exia.pmf.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author PeGiannOS
 * @see http://stackoverflow.com/questions/10055436/how-to-make-a-jframe-really-fullscreen
 */
public class FullScreenEffect implements ActionListener {
	
	private boolean Am_I_In_FullScreen = false;
	private int PrevX, PrevY, PrevWidth, PrevHeight;
	private JFrame frame;
	
	public FullScreenEffect(JFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (Am_I_In_FullScreen == false) {

			PrevX = frame.getX();
			PrevY = frame.getY();
			PrevWidth = frame.getWidth();
			PrevHeight = frame.getHeight();

			frame.dispose();
			frame.setVisible(false);
			frame.setUndecorated(true);

			frame.setBounds(0, 0, frame.getToolkit().getScreenSize().width, frame.getToolkit().getScreenSize().height);
			frame.setVisible(true);
			Am_I_In_FullScreen = true;
			
		}
		else {
			frame.setVisible(false);
			frame.setBounds(PrevX, PrevY, PrevWidth, PrevHeight);
			frame.dispose();
			frame.setUndecorated(false);
			frame.setVisible(true);
			Am_I_In_FullScreen = false;
		}
	}
}
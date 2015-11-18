package vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import controleur.Listenner;
import controleur.Regulation;
import modele.SerialClass;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Windows implements SerialClass.serialReader, ActionListener{

	private JFrame frame;
	
	private JLabel tempCons;
	private JLabel tempExt;
	private JLabel tempInt;
	private JLabel condensLabel;
	
	private LineChart chart;
	private Regulation regul;
	
	private JButton upConsigne;
	private JButton downConsigne;


	/**
	 * Create the application.
	 */
	public Windows(Regulation regul) {
		initialize();
		this.regul = regul;
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("USB Frigo Chargeur Plus");
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		upConsigne = new JButton("+");
		upConsigne.addActionListener(this);
		panel.add(upConsigne);
		
		downConsigne = new JButton("- ");
		downConsigne.addActionListener(this);
		panel.add(downConsigne);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		
		JLabel lblc = new JLabel("Consigne :");
		
		tempCons = new JLabel("0\u00B0C");
		
		JLabel lblExtrieur = new JLabel("Ext\u00E9rieur :");
		
		tempExt = new JLabel("0\u00B0C");
		
		tempInt = new JLabel("0\u00B0C");
		tempInt.setForeground(Color.GRAY);
		tempInt.setFont(new Font("Tahoma", Font.PLAIN, 29));
		
		JLabel lblIntrieur = new JLabel("Int\u00E9rieur :");
		
		JLabel lblHumidit = new JLabel("Humidit\u00E9 :");
		
		condensLabel = new JLabel("0\u00B0C");
		
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblc, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tempCons, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
						.addComponent(lblIntrieur)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
									.addComponent(tempInt)
									.addComponent(lblExtrieur, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblHumidit))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(condensLabel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addComponent(tempExt, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblc, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(tempCons))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblExtrieur, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(tempExt))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHumidit)
						.addComponent(condensLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
					.addComponent(lblIntrieur)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tempInt)
					.addGap(55))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		
		JPanel panelGraph = new JPanel();
		
		chart = new LineChart(
				"Courbe des températures" ,
				"Température extérieure et intérieure");

		ChartPanel component = new ChartPanel(chart.getJChart());
		
		panelGraph.add(component);
		
		panel_2.add(panelGraph);
	}

	public void afficherNouvelleDonnees(float h, float in, float out) {
		tempExt.setText(out + "°C");
		tempInt.setText(in + "°C");
		condensLabel.setText(h + "%");
		
		tempCons.setText(regul.getTempConsigne() + "°C");
		
		chart.addData(in,out);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == downConsigne)
		{
			float nbr = regul.getTempConsigne() - 1;
			regul.setTempConsigne(nbr);
		}
		else if (arg0.getSource() == upConsigne)
		{
			float nbr = regul.getTempConsigne() + 1;
			regul.setTempConsigne(nbr);
		}
		
		tempCons.setText(regul.getTempConsigne() + "°C");
		
	}
}

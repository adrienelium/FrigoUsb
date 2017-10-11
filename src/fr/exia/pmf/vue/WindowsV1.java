package fr.exia.pmf.vue;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.jfree.chart.ChartPanel;

import fr.exia.pmf.abstractions.IDataConnectionListener;
import fr.exia.pmf.abstractions.IRegulatorListener;
import fr.exia.pmf.implementations.RegulationTOR;
import fr.exia.pmf.model.Statement;

public class WindowsV1 implements ActionListener, IDataConnectionListener, IRegulatorListener {

	private JFrame frame;
	
	private LineChart chart;
	private RegulationTOR regul;
	
	private JLabel tempIntLabel;
	private JLabel consigneLabel;
	private JLabel hLabel;
	private JLabel tempExtLabel;

	/**
	 * Create the application.
	 */
	public WindowsV1(RegulationTOR regul) {
		initialize();
		this.regul = regul;
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 862, 893);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("USB Frigo Chargeur Plus");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(WindowsV1.class.getResource("/vue/logo.png")));
		
		JLabel lblPimpMyFridge = new JLabel("PIMP My Fridge");
		lblPimpMyFridge.setForeground(Color.WHITE);
		lblPimpMyFridge.setFont(new Font("Myriad Pro", Font.ITALIC, 30));
		lblPimpMyFridge.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panel = new JPanel();
		chart = new LineChart(
				"Courbe des températures" ,
				"Température extérieure et intérieure");
		panel.setLayout(new CardLayout(0, 0));

		ChartPanel component = new ChartPanel(chart.getJChart());
		panel.add(component, "name_318427173349897");
		
		JSeparator separator = new JSeparator();
		
		JSeparator separator_1 = new JSeparator();
		
		JPanel panel_3 = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 413, Short.MAX_VALUE)
							.addComponent(lblPimpMyFridge, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
				.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblPimpMyFridge, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, Short.MAX_VALUE)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
					.addGap(212))
		);
		
		JPanel panel_1 = new JPanel();
		
		JButton buttonMoins = new JButton("-");
		buttonMoins.addActionListener(this);
		
		JButton buttonPlus = new JButton("+");
		buttonPlus.addActionListener(this);
		
		tempIntLabel = new JLabel("0\u00B0C");
		tempIntLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tempIntLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tempIntLabel.setForeground(new Color(255, 255, 255));
		
		JLabel lblTemperatureActuelle = new JLabel("Temperature actuelle :");
		lblTemperatureActuelle.setForeground(new Color(255, 255, 255));
		lblTemperatureActuelle.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
		
		consigneLabel = new JLabel("0\u00B0C");
		consigneLabel.setForeground(new Color(240, 128, 128));
		consigneLabel.setFont(new Font("Tahoma", Font.PLAIN, 46));
		consigneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblConsigne = new JLabel("CONSIGNE");
		lblConsigne.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsigne.setForeground(new Color(240, 128, 128));
		lblConsigne.setFont(new Font("Myriad Pro", Font.PLAIN, 18));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblConsigne, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(consigneLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(buttonMoins, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
								.addComponent(buttonPlus, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblTemperatureActuelle)
							.addGap(18)
							.addComponent(tempIntLabel, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTemperatureActuelle, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(tempIntLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(consigneLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonMoins, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
							.addGap(1)
							.addComponent(lblConsigne, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
						.addComponent(buttonPlus, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		
		JLabel lblTempratureExtrieure = new JLabel("Temp\u00E9rature ext\u00E9rieure :");
		lblTempratureExtrieure.setForeground(new Color(255, 255, 255));
		lblTempratureExtrieure.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
		
		tempExtLabel = new JLabel("0 \u00B0C");
		tempExtLabel.setForeground(new Color(255, 255, 255));
		tempExtLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tempExtLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
		
		JLabel lblTauxDhumiditer = new JLabel("Taux d'humidité :");
		lblTauxDhumiditer.setForeground(new Color(255, 255, 255));
		lblTauxDhumiditer.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
		
		hLabel = new JLabel("0%");
		hLabel.setForeground(new Color(255, 255, 255));
		hLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
		
		JLabel lblRalisationCesiExia = new JLabel("R\u00E9alisation  CESI Exia Toulouse - Meltzer, Guerboukha, Jach, Allen, Kouevi ");
		lblRalisationCesiExia.setForeground(UIManager.getColor("Button.light"));
		lblRalisationCesiExia.setFont(new Font("Tahoma", Font.ITALIC, 11));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(45, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblRalisationCesiExia)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblTauxDhumiditer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblTempratureExtrieure, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(hLabel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addComponent(tempExtLabel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(tempExtLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTempratureExtrieure, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(hLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTauxDhumiditer, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
					.addComponent(lblRalisationCesiExia)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
					.addGap(72)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
							.addGap(48))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(48))))
		);
		panel_3.setLayout(gl_panel_3);
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("+")) {
			regul.setTempConsigne(regul.getConsigneTemperature() + 1);
		}
		else
		{
			regul.setTempConsigne(regul.getConsigneTemperature() - 1);
		}
		
		consigneLabel.setText(regul.getConsigneTemperature() + "°C");
		
	}

	@Override
	public void onNewStatementRead(Statement data) {
		
		// On met à jour les labels
		tempExtLabel.setText(String.format("%.2f °C", data.getExteriorTemperature()));
		tempIntLabel.setText(String.format("%.2f °C", data.getInteriorTemperature()));
		hLabel.setText(String.format("%.1f", data.getHumidityRate()) + " %");
		
		// On ajoute la donnée au chart
		chart.addData((float)data.getInteriorTemperature(), (float)data.getExteriorTemperature());
		
	}

	@Override
	public void onConsigneTemperatureChanged(double temp) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				consigneLabel.setText(regul.getConsigneTemperature() + "°C");
			}
		});
	}

	@Override
	public void onConsigneAllumageChanged(boolean enabled) {
	}

	@Override
	public void onAlertCondensationChanged(boolean state) {
	}

	@Override
	public void onAlertTemperatureGapChanged(boolean state) {
	}

	@Override
	public void onPowerStatusChanged(boolean powerOn) {
	}
	
}

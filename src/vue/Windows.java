package vue;

import java.awt.CardLayout;
import java.awt.Color;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.RectangleEdge;

import abstractions.IConnectionListener;
import abstractions.IRegulatorListener;
import controleur.Regulation;
import modele.Statement;

public class Windows extends JFrame implements IConnectionListener, IRegulatorListener {

	private Regulation regul;

	private JLabel labelConsigneTemp;
	private JLabel labelConsignePower;
	private JLabel labelTempInt;
	private JLabel labelTempExt;
	private JLabel labelHumitidy;
	private JLabel alertCondensation;
	private JLabel alertTempGap;

	private LineChart chart;

	/**
	 * Create the application.
	 */
	public Windows(Regulation regul) {
		initialize();
		this.regul = regul;
		
		// Valeur initiales
		labelConsigneTemp.setText(String.format("%.2f °C", regul.getConsigneTemperature()));
		alertCondensation.setVisible(false);
		alertTempGap.setVisible(false);
		
		regul.addListener(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setBounds(100, 100, 817, 418);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("USB Frigo Chargeur Plus");
		
		JPanel panelTop = new JPanel();
		
		JPanel panelCenter = new JPanel();
		chart = new LineChart("Courbe des températures", "Température extérieure et intérieure");
		ChartPanel component = new ChartPanel(chart.getJChart());
		
		panelCenter.add(component, "name_318427173349897");
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Acquisition", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panelRight = new JPanel();
		panelRight.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "R\u00E9gulation", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCenter, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
						.addComponent(panelTop, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelRight, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelTop, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelCenter, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelRight, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelCenter.setLayout(new CardLayout(0, 0));
		
		JLabel lblConsigne = new JLabel("Consigne");
		lblConsigne.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelConsigneTemp = new JLabel("0 \u00B0C");
		labelConsigneTemp.setForeground(Color.ORANGE);
		labelConsigneTemp.setFont(new Font("Tahoma", Font.PLAIN, 28));
		labelConsigneTemp.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelConsignePower = new JLabel("Allumage OFF");
		labelConsignePower.setIcon(new ImageIcon(Windows.class.getResource("/vue/off.gif")));
		
		JButton btnConsigne = new JButton("Consigne +");
		btnConsigne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				regul.setTempConsigne(regul.getConsigneTemperature() + 0.5f);
			}
		});
		
		JButton btnConsigne_1 = new JButton("Consigne -");
		btnConsigne_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				regul.setTempConsigne(regul.getConsigneTemperature() - 0.5f);
			}
		});
		
		alertCondensation = new JLabel("Condensation !");
		alertCondensation.setForeground(Color.RED);
		alertCondensation.setIcon(new ImageIcon(Windows.class.getResource("/vue/alert.png")));
		
		alertTempGap = new JLabel("Chute de T\u00B0 !");
		alertTempGap.setForeground(Color.RED);
		alertTempGap.setIcon(new ImageIcon(Windows.class.getResource("/vue/alert.png")));
		
		JLabel lblNewLabel_2 = new JLabel("By Meltzer, Guerboukha, Jach, Allen, Kouevi");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 7));
		
		GroupLayout gl_panelRight = new GroupLayout(panelRight);
		gl_panelRight.setHorizontalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblConsigne, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(labelConsigneTemp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnConsigne_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnConsigne, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(labelConsignePower))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addComponent(alertCondensation)
						.addComponent(alertTempGap)
						.addComponent(lblNewLabel_2))
					.addContainerGap(60, Short.MAX_VALUE))
		);
		gl_panelRight.setVerticalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConsigne)
						.addComponent(labelConsignePower)
						.addComponent(alertCondensation))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addComponent(labelConsigneTemp, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnConsigne)
								.addComponent(alertTempGap))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnConsigne_1)
								.addComponent(lblNewLabel_2))))
					.addContainerGap())
		);
		panelRight.setLayout(gl_panelRight);
		
		JLabel lblTempratureInterne = new JLabel("Temp\u00E9rature interne");
		
		labelTempInt = new JLabel("0 \u00B0C");
		labelTempInt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempInt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTempratureExterne = new JLabel("Temp\u00E9rature externe");
		
		labelTempExt = new JLabel("0\u00B0 C");
		labelTempExt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempExt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTauxDhumidit = new JLabel("Taux d'humidit\u00E9");
		
		labelHumitidy = new JLabel("0 %");
		labelHumitidy.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelHumitidy.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panelLeft = new GroupLayout(panelLeft);
		gl_panelLeft.setHorizontalGroup(
			gl_panelLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLeft.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(labelTempInt, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblTempratureInterne, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelTempExt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblTempratureExterne, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelHumitidy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblTauxDhumidit, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
					.addContainerGap(308, Short.MAX_VALUE))
		);
		gl_panelLeft.setVerticalGroup(
			gl_panelLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLeft.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTempratureInterne)
						.addComponent(lblTempratureExterne)
						.addComponent(lblTauxDhumidit))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
						.addComponent(labelHumitidy, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
						.addComponent(labelTempExt, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
						.addComponent(labelTempInt, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
					.addContainerGap())
		);
		panelLeft.setLayout(gl_panelLeft);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Windows.class.getResource("/vue/logo.png")));
		
		JLabel lblNewLabel_1 = new JLabel("Pimp My Fridge !");
		lblNewLabel_1.setFont(new Font("Stencil", Font.PLAIN, 26));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panelTop = new GroupLayout(panelTop);
		gl_panelTop.setHorizontalGroup(
			gl_panelTop.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTop.createSequentialGroup()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE))
		);
		gl_panelTop.setVerticalGroup(
			gl_panelTop.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTop.createSequentialGroup()
					.addGroup(gl_panelTop.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelTop.setLayout(gl_panelTop);
		getContentPane().setLayout(groupLayout);
		
	}

	@Override
	public void onConsigneTemperatureChanged(double temp) {
		labelConsigneTemp.setText(String.format("%.2f °C", temp));
	}

	@Override
	public void onConsigneAllumageChanged(boolean enabled) {
		labelConsignePower.setText("Allumage " + (enabled ? "ON " : "OFF"));
		labelConsignePower.setIcon(new ImageIcon(Windows.class.getResource("/vue/" + (enabled ? "yes" : "no") + ".gif")));
	}

	@Override
	public void onAlertCondensationChanged(boolean state) {
		alertCondensation.setVisible(state);
	}

	@Override
	public void onAlertTemperatureGapChanged(boolean state) {
		alertTempGap.setVisible(state);
	}

	@Override
	public void onNewStatementRead(Statement data) {
		labelTempExt.setText(String.format("%.2f °C", data.getExteriorTemperature()));
		labelTempInt.setText(String.format("%.2f °C", data.getInteriorTemperature()));
		labelHumitidy.setText(String.format("%.1f", data.getHumidityRate()) + "%");
		// On ajoute la donnée au chart
		chart.addData((float)data.getInteriorTemperature(), (float)data.getExteriorTemperature());
	}

}

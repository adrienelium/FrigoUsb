package fr.exia.pmf.vue;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

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

public class WindowsV2 extends JFrame {

	private static final long serialVersionUID = 1673388500927286919L;
	
	public static ImageIcon ICON_OFF = new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/off.gif"));
	public static ImageIcon ICON_YES = new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/yes.gif"));
	public static ImageIcon ICON_NO = new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/no.gif"));

	public JLabel labelConsigneTemp;
	public JLabel labelConsignePower;
	public JLabel labelTempInt;
	public JLabel labelTempExt;
	public JLabel labelHumitidy;
	public JLabel labelConsoWatt;
	public JLabel alertCondensation;
	public JLabel alertTempGap;
	public LineChart chart;
	public JButton btnConsignePlus;
	public JButton btnConsigneMoins;
	public JButton btnFullscreen;

	/**
	 * Create the application.
	 */
	public WindowsV2() {
		
		setBounds(100, 100, 869, 418);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("USB Frigo Chargeur Plus");
		
		JPanel panelTop = new JPanel();
		
		JPanel panelCenter = new JPanel();
		chart = new LineChart("Courbe des températures", "Température extérieure et intérieure");
		ChartPanel component = new ChartPanel(chart.getJChart());
		
		panelCenter.add(component, "name_318427173349897");
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), null, TitledBorder.LEADING, TitledBorder.TOP));
		
		JPanel panelRight = new JPanel();
		panelRight.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), null, TitledBorder.LEADING, TitledBorder.TOP));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCenter, GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
						.addComponent(panelTop, GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelRight, GroupLayout.PREFERRED_SIZE, 423, Short.MAX_VALUE)))
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
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelRight, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelCenter.setLayout(new CardLayout(0, 0));
		
		JLabel lblConsigne = new JLabel("Consigne");
		lblConsigne.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelConsigneTemp = new JLabel("0 \u00B0C");
		labelConsigneTemp.setForeground(Color.ORANGE);
		labelConsigneTemp.setFont(new Font("Tahoma", Font.PLAIN, 28));
		labelConsigneTemp.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnConsignePlus = new JButton("Consigne +");
		btnConsignePlus.setBackground(Color.ORANGE);
		btnConsignePlus.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConsigneMoins = new JButton("Consigne -");
		btnConsigneMoins.setBackground(Color.ORANGE);
		btnConsigneMoins.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		alertCondensation = new JLabel("Condensation !");
		alertCondensation.setFont(new Font("Tahoma", Font.BOLD, 11));
		alertCondensation.setForeground(new Color(241, 61, 7));
		alertCondensation.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/alert.png")));
		
		alertTempGap = new JLabel("Hausse de T\u00B0 !");
		alertTempGap.setFont(new Font("Tahoma", Font.BOLD, 11));
		alertTempGap.setForeground(new Color(241, 61, 7));
		alertTempGap.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/alert.png")));
		
		JLabel lblNewLabel_2 = new JLabel("R\u00E9alis\u00E9 par Meltzer, Guerboukha, Jach, Allen, Kouevi");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		labelConsoWatt = new JLabel("Consommation : 0 Wh (0 \u20AC)");
		labelConsoWatt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		GroupLayout gl_panelRight = new GroupLayout(panelRight);
		gl_panelRight.setHorizontalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING, false)
						.addComponent(labelConsigneTemp, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblConsigne, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addComponent(btnConsigneMoins)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnConsignePlus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
								.addComponent(alertTempGap, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
								.addComponent(alertCondensation, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
						.addComponent(lblNewLabel_2)
						.addComponent(labelConsoWatt, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panelRight.setVerticalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
								.addComponent(lblConsigne)
								.addComponent(alertTempGap))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelConsigneTemp, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelRight.createSequentialGroup()
									.addComponent(alertCondensation)
									.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
									.addComponent(lblNewLabel_2))
								.addComponent(labelConsoWatt, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnConsigneMoins, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnConsignePlus, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panelRight.setLayout(gl_panelRight);
		
		JLabel lblTempratureInterne = new JLabel("Temp\u00E9rature interne");
		
		labelTempInt = new JLabel("0 \u00B0C");
		labelTempInt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempInt.setForeground(new Color(0, 174, 189));
		labelTempInt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTempratureExterne = new JLabel("Temp\u00E9rature externe");
		
		labelTempExt = new JLabel("0\u00B0 C");
		labelTempExt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempExt.setForeground(new Color(241, 61, 7));
		labelTempExt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTauxDhumidit = new JLabel("Taux d'humidit\u00E9");
		
		labelHumitidy = new JLabel("0 %");
		labelHumitidy.setFont(new Font("Tahoma", Font.PLAIN, 22));
		//labelHumitidy.setForeground(new Color(66, 255, 66));
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
		
		JLabel lblNewLabel_1 = new JLabel("          Pimp My Fridge !");
		lblNewLabel_1.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/logo.png")));
		lblNewLabel_1.setFont(new Font("Arial Narrow", Font.PLAIN, 26));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		labelConsignePower = new JLabel("Allumage OFF");
		labelConsignePower.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelConsignePower.setIcon(ICON_OFF);
		
		btnFullscreen = new JButton("");
		btnFullscreen.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/fullscreen24.png")));
		GroupLayout gl_panelTop = new GroupLayout(panelTop);
		gl_panelTop.setHorizontalGroup(
			gl_panelTop.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelTop.createSequentialGroup()
					.addComponent(btnFullscreen, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelConsignePower, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
		);
		gl_panelTop.setVerticalGroup(
			gl_panelTop.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelTop.createSequentialGroup()
					.addGroup(gl_panelTop.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelConsignePower, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnFullscreen, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
					.addContainerGap())
		);
		panelTop.setLayout(gl_panelTop);
		getContentPane().setLayout(groupLayout);
		
	}
}

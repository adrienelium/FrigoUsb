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
	public JLabel alertCondensation;
	public JLabel alertTempGap;
	public LineChart chart;
	public JButton btnConsignePlus;
	public JButton btnConsigneMoins;

	/**
	 * Create the application.
	 */
	public WindowsV2() {
		
		setBounds(100, 100, 843, 418);
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
		
		labelConsignePower = new JLabel("Allumage OFF");
		labelConsignePower.setIcon(ICON_OFF);
		
		btnConsignePlus = new JButton("Consigne +");
		btnConsigneMoins = new JButton("Consigne -");
		
		alertCondensation = new JLabel("Condensation !");
		alertCondensation.setForeground(Color.RED);
		alertCondensation.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/alert.png")));
		
		alertTempGap = new JLabel("Chute de T\u00B0 !");
		alertTempGap.setForeground(Color.RED);
		alertTempGap.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/alert.png")));
		
		JLabel lblNewLabel_2 = new JLabel("R\u00E9alis\u00E9 par Meltzer, Guerboukha, Jach, Allen, Kouevi");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		GroupLayout gl_panelRight = new GroupLayout(panelRight);
		gl_panelRight.setHorizontalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblConsigne, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(labelConsigneTemp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
					.addGap(28)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addComponent(btnConsigneMoins)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnConsignePlus))
						.addGroup(gl_panelRight.createSequentialGroup()
							.addComponent(alertTempGap)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(alertCondensation))
						.addComponent(labelConsignePower))
					.addGap(113))
		);
		gl_panelRight.setVerticalGroup(
			gl_panelRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRight.createSequentialGroup()
					.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConsigne)
						.addComponent(labelConsignePower))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
						.addComponent(labelConsigneTemp, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
						.addGroup(gl_panelRight.createSequentialGroup()
							.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
								.addComponent(alertTempGap)
								.addComponent(alertCondensation))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnConsigneMoins, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnConsignePlus, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
							.addComponent(lblNewLabel_2)))
					.addContainerGap())
		);
		panelRight.setLayout(gl_panelRight);
		
		JLabel lblTempratureInterne = new JLabel("Temp\u00E9rature interne");
		
		labelTempInt = new JLabel("0 \u00B0C");
		labelTempInt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempInt.setForeground(new Color(66, 255, 66));
		labelTempInt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTempratureExterne = new JLabel("Temp\u00E9rature externe");
		
		labelTempExt = new JLabel("0\u00B0 C");
		labelTempExt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelTempExt.setForeground(new Color(241, 61, 7));
		labelTempExt.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTauxDhumidit = new JLabel("Taux d'humidit\u00E9");
		
		labelHumitidy = new JLabel("0 %");
		labelHumitidy.setFont(new Font("Tahoma", Font.PLAIN, 22));
		labelHumitidy.setForeground(new Color(0, 174, 189));
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
		lblNewLabel.setIcon(new ImageIcon(WindowsV2.class.getResource("/fr/exia/pmf/vue/logo.png")));
		
		JLabel lblNewLabel_1 = new JLabel("Pimp My Fridge !");
		lblNewLabel_1.setFont(new Font("Stencil", Font.PLAIN, 26));
		lblNewLabel_1.setForeground(Color.WHITE);
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
}

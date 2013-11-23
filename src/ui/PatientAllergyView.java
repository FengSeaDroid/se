package ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class PatientAllergyView extends JPanel {
	
	public static JPanel[] innerPanels;
	
	public PatientAllergyView(){
		super.setLayout(new MigLayout("","0[]0[]0","0[]0[]0"));
		this.setPreferredSize(new Dimension(MainWindow.d.width/3,MainWindow.d.height/2-100));
		innerPanels = MousePanel.fillerWithScroll(MainWindow.d.width/3-80,MainWindow.d.height/2-150);
//		innerPanels = MousePanel.fillerWithScroll(300,200);
		this.add(innerPanels[0]);
		this.setBorder(BorderFactory.createTitledBorder("Patient Allergy"));
		
	}
}
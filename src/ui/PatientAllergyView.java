package ui;

import java.awt.Dimension;

import javax.swing.JPanel;



import ui.VanillaPanel.OuterPanel;
import net.miginfocom.swing.MigLayout;


public class PatientAllergyView extends JPanel {
	
	public static OuterPanel outer;
	
	public PatientAllergyView(){
		super.setLayout(new MigLayout("debug"));
//		outer = MousePanel.fillerWithScroll(MainWindow.d.width/3-10,MainWindow.d.height/2);
		outer = MousePanel.fillerWithScroll(500,300);
		this.setPreferredSize(new Dimension(500,300));
	}
	
	public OuterPanel getData(){
		return outer;
	}
}
package ui;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;


public class PatientAllergyView extends JPanel {
	
	private JPanel[] innerPanels;
	
	public PatientAllergyView(){
		super.setLayout(new MigLayout("","0[]0[]0","0[]0[]0"));
		this.setPreferredSize(new Dimension(MainWindow.d.width/3,MainWindow.d.height/2-100));
		innerPanels = MousePanel.fillerWithScroll(MainWindow.d.width/3-80,MainWindow.d.height/2-150);
		this.add(innerPanels[0]);
		this.setBorder(BorderFactory.createTitledBorder("Patient Allergy"));
	}

	public void populate(Set<String> allergy, boolean editable) {
		((VanillaPanel) this.innerPanels[1]).populate(allergy,editable);
		
	}

	public void clear() {
		((VanillaPanel) this.innerPanels[1]).clear();
		
	}
	
	public Set<String> pull(){
		return new HashSet<String>(((VanillaPanel) this.innerPanels[1]).pull());
	}
}
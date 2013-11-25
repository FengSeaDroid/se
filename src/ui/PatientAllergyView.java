package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import control.MainControl;
import net.miginfocom.swing.MigLayout;


public class PatientAllergyView extends JPanel implements ActionListener {
	
	private JPanel[] innerPanels;
	private JButton addAllergyButton;
	
	public PatientAllergyView(){
		super.setLayout(new MigLayout("","0[]0[]0","0[]0[]0"));
		this.setPreferredSize(new Dimension(MainWindow.d.width/3,MainWindow.d.height/2-100));
		innerPanels = MousePanel.fillerWithScroll(MainWindow.d.width/3-80,MainWindow.d.height/2-150);
		this.add(innerPanels[0],"wrap");
		addAllergyButton = new JButton("Save Allergy");
		addAllergyButton.setPreferredSize(new Dimension((int) (MainWindow.d.width*0.75/3),20));
		addAllergyButton.addActionListener(this);
		this.add(addAllergyButton,"wrap, center");
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

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MainControl.getMainControl().getPatientManager().addAllergy(pull());
			for (String s: pull()){
				System.out.println("this should be added: "+s);
			}
			populate(pull(),false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import control.MainControl;
import net.miginfocom.swing.MigLayout;


public class PatientAllergyView extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel[] innerPanels;
	private JButton saveAllergyButton;
	private JButton addAllergyButton;


	public PatientAllergyView(){
		super.setLayout(new MigLayout("","0[]0[]0","0[]0[]0"));
		this.setPreferredSize(new Dimension(MainWindow.d.width/3,MainWindow.d.height/2));
		innerPanels = MousePanel.fillerWithScroll(MainWindow.d.width/3-80,MainWindow.d.height/2-150);
		this.add(innerPanels[0],"wrap,span");
		
		addAllergyButton=new EnterButton("Add Allergy");
		//addAllergyButton.setPreferredSize(new Dimension((int) (MainWindow.d.width*0.75/4),20));
		addAllergyButton.addActionListener(this);
		this.add(addAllergyButton,"right,width :220:");
		this.setBorder(BorderFactory.createTitledBorder("Patient Allergy"));
		
		saveAllergyButton = new EnterButton("Save Allergy");
		//saveAllergyButton.setPreferredSize(new Dimension((int) (MainWindow.d.width*0.75/4),20));
		saveAllergyButton.addActionListener(this);
		this.add(saveAllergyButton,"left,wrap,width :220:");
		
		this.setBorder(BorderFactory.createTitledBorder("Patient Allergy"));

		// save the command mapping for space
		Object spaceMap = saveAllergyButton.getInputMap().get(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true));
		// add a mapping from enter to the same command.
		saveAllergyButton.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),spaceMap);
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
		if(e.getSource()==saveAllergyButton){

			try {
				MainControl.getMainControl().getPatientManager().addAllergy(pull());
				populate(pull(),false);
				//			System.out.println("each click should pull twice ");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==addAllergyButton){
			System.out.println("AddAllergyButton pressed");
			JTextField tempJT = (JTextField)((VanillaPanel) this.innerPanels[1]).boxList.get(((VanillaPanel)this.innerPanels[1]).boxList.size()-1).getEditor().getEditorComponent();
			((VanillaPanel) this.innerPanels[1]).enterPress(((VanillaPanel) this.innerPanels[1]).boxList.size()-1,tempJT.getText().length());
		}


	}
}
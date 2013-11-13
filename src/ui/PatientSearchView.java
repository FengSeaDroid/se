package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import control.MainControl;

import net.miginfocom.swing.MigLayout;

public class PatientSearchView extends JPanel {

	private Map<String,String> patientInfo = new HashMap<String,String>();
	private patientAllergyView av = new patientAllergyView();
   
	JTextField mcp = new JTextField(20);
	JTextField DOB = new JTextField(20);
	JTextField weight = new JTextField(10);
	public PatientSearchView() {
		
		super(new MigLayout("wrap 8", "[][][][][][][][][]", "[][][]"));
		//patientInfo.put("DOB",);
		//patientInfo.put("weight", );
		JLabel label_3 = new JLabel("Name");
		this.add(label_3,"cell 1 0,alignx right,gapx unrelated");
		JTextField textField = new JTextField(20);
		this.add(textField, "cell 2 0");
		JLabel label = new JLabel("MCP");
		this.add(label,"cell 1 1,alignx right,gapx unrelated");
		//this.add(new JTextField(20));
		this.add(mcp, "cell 2 1");
		JLabel label1 = new JLabel("DOB");
		this.add(label1,"cell 0 2,gapx unrelated");
		DOB.setEditable(false);
		this.add(DOB, "cell 1 2");
		JLabel label_2 = new JLabel("Wieght");
		this.add(label_2,"cell 2 2,alignx right,gapx unrelated");
		final JTextField weight = new JTextField(15);
		weight.setEditable(false);
		this.add(weight, "cell 4 2");
		mcp.addActionListener(new ActionListener(){

	        public void actionPerformed(ActionEvent e){
	        	
	                DOB.setText(MainControl.getMainControl().getCurrentPatient().getDateOfBirth());
	                weight.setText(MainControl.getMainControl().getCurrentPatient().getWeight());
	                patientAllergyView.model.addRow(MainControl.getMainControl().getCurrentPatient().getAllergy());
	                
	        }});
		
	}
}

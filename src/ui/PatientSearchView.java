package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;

import control.MainControl;
import domain.Patient;
import domain.Prescription;

import net.miginfocom.swing.MigLayout;

public class PatientSearchView extends JPanel {

	//private Map<String,String> patientInfo = new HashMap<String,String>();
	//private patientAllergyView av = new patientAllergyView();
	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	Set<String>allergy = new HashSet<String>();
	JTextField nameField = new JTextField(20);
	JTextField mcp = new JTextField(20);
	JTextField DOB = new JTextField(20);
	JTextField weight = new JTextField(10);
	JTextField address = new JTextField(20);
	JTextField tel = new JTextField(10);
	
	static String patient_ID;
	public PatientSearchView() {
		
		super(new MigLayout("wrap 8", "[][grow][][][grow][][][][]", "[][][][][][][]"));
		//patientInfo.put("DOB",);
		//patientInfo.put("weight", );
		JLabel label_3 = new JLabel("Name");
		this.add(label_3,"cell 1 0,alignx right,gapx unrelated");
		
		this.add(nameField, "cell 2 0");
		
		JLabel label = new JLabel("MCP");
		this.add(label,"cell 1 1,alignx right,gapx unrelated");
		//this.add(new JTextField(20));
		this.add(mcp, "cell 2 1");
		
		JLabel lblAddress = new JLabel("Address: ");
		add(lblAddress, "cell 0 2");
		address.setEditable(false);
		address.setBorder(null);
		this.add(address, "cell 1 2");
		//address = new JTextField(15);
		
		JLabel lblTell = new JLabel("Tel: ");
		add(lblTell, "cell 2 2,alignx right");
		//Tell = new JTextField(15);
		tel.setEditable(false);
		tel.setBorder(null);
		this.add(tel, "cell 4 2");
		
		JLabel dobLabel = new JLabel("DOB: ");
		this.add(dobLabel,"cell 0 3,alignx trailing,gapx unrelated");
		DOB.setEditable(false);
		DOB.setBorder(null);
		this.add(DOB, "cell 1 3,growx");
		
		JLabel label_2 = new JLabel("Wieght: ");
		this.add(label_2,"cell 2 3,alignx right,gapx unrelated");
		weight= new JTextField(15);
		weight.setEditable(false);
		add(weight, "cell 4 3,growx");
		weight.setBorder(null);
		
		mcp.addActionListener(new ActionListener(){

	        public void actionPerformed(ActionEvent e){
	        	
	        	Patient patient = MainControl.getMainControl().lookupPatient(mcp.getText());
	        	//patient_ID = patient.getPatientID();
	        	nameField.setText(patient.getName());
                DOB.setText(patient.getDateOfBirth());
                weight.setText(patient.getWeight());
                address.setText(patient.getAddress());
                tel.setText(patient.getTel());
                allergy = patient.getAllergy();
                for(String a: allergy)
                {
                String[] data = {a};
                PatientAllergyView.model.addRow(data);
                }
                //take care below
                prescriptionHistory = patient.getPrescriptionHistory();
                //for(int i=0; i<prescriptionHistory.size(); i++)
                for(Prescription p: prescriptionHistory)
                {
               // String[] drugs = p.getDrugLines();
              //  for (int i=0; i< drugs.length; i++)
              //  {
                String[] data = {p.getIssueDate(), p.getDrugLines().toString()};
                prescriptionHistoryView.model.addRow(data);
              //  }
                }
	        }});
		
	}
}

package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;

import control.MainControl;
import domain.PatientManager;
import domain.Prescription;

public class HistoryWindow extends JPanel {
	private ClinicInfoView clinicInfoView;
	private PatientSearchView patientSearchView;
	private DrugLineView drugLineView;

	public HistoryWindow(Prescription currentPrescription)
	{
		super.setLayout(new MigLayout("","[]","[][][]"));
		clinicInfoView=new ClinicInfoView(false);
		patientSearchView=new PatientSearchView();
		patientSearchView.fill(MainControl.getMainControl().getCurrentPatient().getMCP());
		drugLineView=new DrugLineView();
		patientSearchView.mcpField.setEditable(false);
		drugLineView.printbutton.setVisible(false);

		JTextField jt =(JTextField)patientSearchView.nameField.getEditor().getEditorComponent();
		jt.setText(MainControl.getMainControl().getCurrentPatient().getName());
//		patientSearchView.nameField.setEditable(false);
		jt.setEditable(false);
		this.add(clinicInfoView,"cell 0 0, center, wrap");
		this.add(patientSearchView, "cell 0 1, center, wrap");
		this.add(drugLineView, "cell 0 2,  center, wrap");
		drugLineView.setBorder(BorderFactory.createLineBorder(Color.red));
		
		
		for(String p: currentPrescription.getDrugLines())
		{
			drugLineView.renewToDrugLineView(p);
		}
	}
}


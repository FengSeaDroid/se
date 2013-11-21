package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

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
		clinicInfoView=new ClinicInfoView(false);
		patientSearchView=new PatientSearchView();
		patientSearchView.fill(MainControl.getMainControl().getCurrentPatient().getMCP());
		drugLineView=new DrugLineView();
		patientSearchView.mcpField.setEditable(false);

		JTextField jt =(JTextField)patientSearchView.nameField.getEditor().getEditorComponent();
		jt.setText(MainControl.getMainControl().getCurrentPatient().getName());
//		patientSearchView.nameField.setEditable(false);
		jt.setEditable(false);
		this.add(clinicInfoView);
		this.add(patientSearchView);
		this.add(drugLineView);
	}
}


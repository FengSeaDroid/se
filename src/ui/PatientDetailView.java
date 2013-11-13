package ui;

import java.awt.Dimension;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class PatientDetailView extends JPanel {
	
	public PatientDetailView(boolean editable){
		this.setLayout(new MigLayout("", "[]", "[]20[]"));
		this.add(this.patientDetail(), "wrap");
		this.add(this.allergyView());
	}
	
	protected JComponent patientDetail(){
		JPanel patientDetail = new JPanel();
		patientDetail.setLayout(new MigLayout());
		this.add(new JLabel("Address"),"gap unrelated");
		this.add(new JTextField(50),"span, wrap");
		this.add(new JLabel("Tel"),"gap unrelated");
		this.add(new JTextField(20));
		this.add(new JLabel("Email"),"gap unrelated");
		this.add(new JTextField(20),"wrap");
		this.add(new JLabel("Emergency Contact"),"span, wrap");
		this.add(new JLabel("Name"),"gap unrelated");
		this.add(new JTextField(20));
		this.add(new JLabel("Tel"),"gap unrelated");
		this.add(new JTextField(20));
		return patientDetail;
	}
	
	private JComponent allergyView(){
		JPanel allergyView = new JPanel(new MigLayout("","[][]","[][][]"));
		JScrollPane allergyFiller = new JScrollPane();
		//JTable allergyInfo = new JTable();
		//allergyFiller.getViewport().setView(allergyInfo);
		allergyFiller.setPreferredSize(new Dimension(100,200));
		allergyView.add(new JLabel("Allergy Information"),"wrap,span 2");
		allergyView.add(allergyFiller,"wrap,cell 1 0 1");
		allergyView.add(new JButton("Add allergy")," alignx right");
		allergyView.add(new JButton("Remove allergy"),"alignx left");
		return allergyView;
	}

}

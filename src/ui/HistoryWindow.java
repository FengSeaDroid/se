package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

import control.MainControl;

public class HistoryWindow extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	public HistoryWindow()
	{
		//super(new MigLayout("","[grow]","20[]10[]10[]10"));
		super(new GridLayout());
		JLabel clinicNameLabel = new JLabel(MainControl.getMainControl().getClinicName());
		//clinicNameLabel.setFont (clinicNameLabel.getFont ().deriveFont (fontSize));
		JLabel clinicTel = new JLabel(MainControl.getMainControl().getClinicTel());
		//clinicTel.setFont (clinicTel.getFont ().deriveFont (fontSize));

		JLabel physicianName = new JLabel(MainControl.getMainControl().getPhysicianName());
		JLabel clinicAddress = new JLabel(MainControl.getMainControl().getClinicAddress());
		
		//physicianName.setFont (physicianName.getFont ().deriveFont (fontSize));

	//	this.add(clinicNameLabel,"align left,cell 2 1");
	//	this.add(clinicTel,"align left, cell 2 3");
	//	this.add(physicianName,"align left, cell 2 0");
	//	this.add(clinicAddress,"align left,cell 2 2");
	}
}


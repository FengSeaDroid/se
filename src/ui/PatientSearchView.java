package ui;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class PatientSearchView extends JPanel {

	public PatientSearchView() {
		super(new MigLayout("wrap 8"));
		this.add(new JLabel("Name"),"gap unrelated");
		this.add(new JTextField(20));
		this.add(new JLabel("MCP"),"gap unrelated");
		this.add(new JTextField(20));
		this.add(new JLabel("DOB"),"gap unrelated");
		this.add(new JTextField(20));
		this.add(new JLabel("Wieght"),"gap unrelated");
		this.add(new JTextField(10));
	}
}

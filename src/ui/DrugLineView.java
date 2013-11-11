package ui;

import java.awt.Dimension;
import java.util.Set;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import domain.Drug;

public class DrugLineView extends JPanel {
	
	private JList<Drug> drugList = new JList<Drug>();

	public DrugLineView() {
		super(new MigLayout());
		this.add(this.constructFillComponent(),"wrap");
		this.add(this.buttonView(),"alignx center");
	}
	
	private JComponent constructFillComponent(){
		JPanel drugFill = new JPanel(new MigLayout());
		drugFill.add(new JTextField(75));
		return drugFill;
	}
	
	private JComponent buttonView(){
		JPanel printView = new JPanel(new MigLayout());
		printView.add(new JButton("Print"));
		return printView;
	}
}

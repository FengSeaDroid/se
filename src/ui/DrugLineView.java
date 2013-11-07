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
		this.add(this.uiDrugLine(),"alignx center, wrap");
		this.add(this.buttonView(),"alignx center");
	}

	public DrugLineView(Set<Drug> drugLine) {
		this.add(this.uiDrugLine());
	}
	
	private JComponent constructFillComponent(){
		JPanel drugFill = new JPanel(new MigLayout());
		drugFill.add(new JLabel("Drug Name"),"gap unrelated");
		drugFill.add(new JTextField(20));
		drugFill.add(new JLabel("Strength"),"gap unrelated");
		drugFill.add(new JTextField(20));
		drugFill.add(new JLabel("Formulation"),"gap unrelated");
		drugFill.add(new JTextField(20),"wrap");
		drugFill.add(new JLabel("Dose Freq"),"gap unrelated");
		drugFill.add(new JTextField(20));
		drugFill.add(new JLabel("Route"),"gap unrelated");
		drugFill.add(new JTextField(20));
		drugFill.add(new JLabel("Total"),"gap unrelated");
		drugFill.add(new JTextField(20),"wrap");
		drugFill.add(new JLabel("Note"),"gap unrelated");
		drugFill.add(new JTextField(70),"span, wrap");
		drugFill.add(new JButton("Add drug"),"span 3, alignx right");
		drugFill.add(new JButton("Remove drug")," span 3, alignx left");
		return drugFill;
	}
	
	private JComponent uiDrugLine(){
		JPanel drugListView = new JPanel(new MigLayout());
		JScrollPane drugListFiller = new JScrollPane();
		drugListFiller.getViewport().setView(drugList);
		drugListFiller.setPreferredSize(new Dimension(this.getMaximumSize().width,500));
		drugListView.add(drugListFiller);
		return drugListView;
	}
	
	private JComponent buttonView(){
		JPanel printView = new JPanel(new MigLayout());
		printView.add(new JButton("Print"));
		return printView;
	}
}

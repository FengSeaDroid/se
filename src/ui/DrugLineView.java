package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Set;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import domain.Drug;

public class DrugLineView extends JPanel implements FocusListener {
	
	private JList<Drug> drugList = new JList<Drug>();
	private JScrollPane drugLineScroll;
	private JPanel drugLinePanel;

	public DrugLineView() {
		super(new MigLayout());
		drugLinePanel = new JPanel(new MigLayout());
		drugLineScroll = new JScrollPane(drugLinePanel);
		drugLineScroll.setPreferredSize(new Dimension(this.getMaximumSize().width,400));
		this.add(drugLineScroll,"wrap");
		drugLinePanel.add(this.drugFiller(),"wrap");
		this.add(this.buttonView(),"center");
	}
	
	private JComponent drugFiller(){
		JPanel drugFill = new JPanel(new MigLayout());
		//JTextField drugLine = new JTextField(55);
		JComboBox drugLine = new JComboBox();
		
		drugLine.setEditable(true);
		drugLine.setPreferredSize(new Dimension(500,10));
		//create the model
		SearchBoxModel sbm = new SearchBoxModel(drugLine);
		//set the model on the combobox
		drugLine.setModel(sbm);
		//set the model as the item listener also
		drugLine.addItemListener(sbm);
		
		
		drugLine.getEditor().getEditorComponent().addFocusListener(this);
		
		drugFill.add(drugLine);
		drugFill.add(new JButton("delete"));
		return drugFill;
	}
	
	private JComponent buttonView(){
		JPanel printView = new JPanel(new MigLayout());
		printView.add(new JButton("Print"));
		return printView;
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		this.drugLinePanel.add(this.drugFiller(),"wrap");
		drugLinePanel.revalidate();
		System.out.println("listened!!!");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}

package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		this.add(refillAndSig(),"wrap,align right");
		
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
		
		//add function of delete button
		JButton deletebutton=new JButton("delete");
		drugFill.add(deletebutton);
		
		deletebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton thebutton=(JButton) e.getSource();
				
				JPanel fatherpanel=(JPanel) thebutton.getParent().getParent();
				JPanel sonpanel=(JPanel) thebutton.getParent();
				fatherpanel.remove(sonpanel);
				fatherpanel.revalidate();
				fatherpanel.repaint();
			}
		});
		//function ends here
		
		return drugFill;
	}
	
	private JComponent buttonView(){
		JPanel printView = new JPanel(new MigLayout());
		printView.add(new JButton("Print"));
		return printView;
		
	}
	
	private JComponent refillAndSig(){
		JPanel ras= new JPanel(new MigLayout());
		
		JPanel jp1 = new JPanel();
		jp1.add(new JLabel("Refill:"));
		JTextField jt5=new JTextField(5);
		jp1.add(jt5);
		ras.add(jp1,"align left");
		
		JPanel jp2 = new JPanel();
		jp2.add(new JLabel("Signature"));
		JTextField jt6=new JTextField(15);
		jp2.add(jt6);
		ras.add(jp2,"span 3,align right");
		
		return ras;
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

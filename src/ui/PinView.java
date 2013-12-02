package ui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import control.MainControl;
import net.miginfocom.swing.MigLayout;

public class PinView extends JFrame implements ActionListener,FocusListener {

	private JPanel pinPanel;
	private JButton printButton;
	private JButton cancelButton;
	private JLabel pinLabel;
	private JPasswordField pinPasswordField;
	private JLabel msgLabel;
	private final NewDrugLineView drugLineView;
//	private String refill;

	public PinView(final NewDrugLineView dlv) {
		super("Print Authentication Window");
		pinPanel = new JPanel();
		pinLabel = new JLabel("Enter Your Pin: ");
		pinPasswordField=new JPasswordField(20);
		this.setSize(240, 110);
		
//		this.refill=refill;
		this.drugLineView = dlv;
		pinPasswordField.setDocument(new JTextFieldLimit(10));

		pinPanel.setLayout (new MigLayout("","[][][]","")); 
		pinPanel.add(pinLabel,"right");

		pinPasswordField=new JPasswordField(10);
		//pinPanel.add(pinPasswordField);
		//pinPasswordField.setSize(100, 100);
		pinPanel.add(pinPasswordField,"span 2,wrap");	
		pinPasswordField.addActionListener(this);
		
		msgLabel = new JLabel("");
		pinPanel.add(msgLabel,"cell 0 1");
		msgLabel.setForeground(Color.red);

		printButton=new JButton("Print");
		pinPanel.add(printButton,"right,,width :50:");
		printButton.addActionListener(this);

		cancelButton=new JButton("Cancel");
		pinPanel.add(cancelButton,"right,,width :50:");
		cancelButton.addActionListener(this);
		
		

//		no close the whole program Safwan the terrorist!!!
//		Safwan Alquraan close JFrame
//		Feng Wu not this JFrame man, it's static!
//		Safwan Alquraan really ok i know
//		Feng Wu so you should know it can't know which jframe to close, so it close all and all program is dead
//		all your fault!
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setFocusableWindowState(true);
		this.setLocationRelativeTo(null);

		this.addFocusListener(this);

		this.add(pinPanel);
		this.setVisible(true);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		       drugLineView.isPinShown = false;
		    }
		});
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==printButton || e.getSource()==pinPasswordField){
			if(pinPasswordField.getText().equals(MainControl.getMainControl().getPhysicianPin()))
			{
				//Print report method for report
				try {
//					MainControl.getMainControl().print(MainWindow.drugLineView.pull(), MainWindow.drugLineView.getEffectiveDate(),refill);
//					MainWindow.clear();
					this.drugLineView.print();
					this.drugLineView.isPinShown=false;
					this.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				msgLabel.setText("Wrong Pin");
				pinPasswordField.setText("");
			}
		}
		if(e.getSource()==cancelButton){
			this.drugLineView.isPinShown=false;
			this.dispose();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {

	}

	@Override
	public void focusLost(FocusEvent e) {
	}

}

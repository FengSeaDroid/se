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

	private JPanel pinPanel=new JPanel(new MigLayout("wrap 3","[grow][grow][grow]","[][][][]"));
	private JButton printButton;
	private JButton cancelButton;
	private JLabel pinLabel=new JLabel("Please type in your pin number:");
	private JPasswordField pinPasswordField=new JPasswordField(20);
	private JLabel msgLabel=new JLabel();
	private String refill;

	public PinView(String refill) {

		super("Print Authentication Window");
		this.setSize(260, 100);
		
		this.refill=refill;
		
		pinPasswordField.setDocument(new JTextFieldLimit(10));

		pinPanel.setLayout (new MigLayout()); 
		pinPanel.add(pinLabel,"span 2,align center");

		pinPasswordField=new JPasswordField(10);
		//pinPanel.add(pinPasswordField);
		//pinPasswordField.setSize(100, 100);
		pinPanel.add(pinPasswordField,"wrap, center");	
		pinPasswordField.addActionListener(this);


		printButton=new JButton("Print");
		pinPanel.add(printButton,"left,,width :80:");
		printButton.addActionListener(this);

		cancelButton=new JButton("Cancel");
		pinPanel.add(cancelButton,"right,,width :80:");
		cancelButton.addActionListener(this);
		
		pinPanel.add(msgLabel,"span");
		msgLabel.setForeground(Color.red);

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
	}

//	public static void main(String[] args){
//		JFrame jf=new PinView(this.refill);
//		//jf.setVisible(true);
//		jf.setLocationRelativeTo(null);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==printButton || e.getSource()==pinPasswordField){
			if(pinPasswordField.getText().equals(MainControl.getMainControl().getPhysicianPin()))
			{
				//Print report method for report
				try {
					MainControl.getMainControl().print(MainWindow.drugLineView.pull(), MainWindow.drugLineView.getEffectiveDate(),refill);
					MainWindow.clear();
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

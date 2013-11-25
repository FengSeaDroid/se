package ui;


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
	private JLabel msgLabel=new JLabel("Please type in your pin number:");
	private JPasswordField pinPasswordField=new JPasswordField(20);

	public PinView() {

		super("Print Authentication Window");
		this.setSize(250, 100);

		pinPanel.setLayout (new MigLayout()); 
		pinPanel.add(msgLabel,"span 5,align center");

		pinPasswordField=new JPasswordField(10);
		//pinPanel.add(pinPasswordField);
		//pinPasswordField.setSize(100, 100);
		pinPanel.add(pinPasswordField,"wrap, center");	
		pinPasswordField.addActionListener(this);


		printButton=new JButton("print");
		pinPanel.add(printButton,"left");
		printButton.addActionListener(this);

		cancelButton=new JButton("cancel");
		pinPanel.add(cancelButton,"right");
		cancelButton.addActionListener(this);


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setFocusableWindowState(true);
		this.setLocationRelativeTo(null);

		this.addFocusListener(this);

		this.add(pinPanel);
		this.setVisible(true);
	}

	public static void main(String[] args){
		JFrame jf=new PinView();
		//jf.setVisible(true);
		jf.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==printButton || e.getSource()==pinPasswordField){
			if(pinPasswordField.getText().equals(MainControl.getMainControl().getPhysicianPassword()))
			{
				//Print report method for report
				try {
					MainControl.getMainControl().print(MainWindow.drugLineView.pull(), MainWindow.drugLineView.getEffectiveDate());
					this.setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==cancelButton){
			this.setVisible(false);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {

	}

	@Override
	public void focusLost(FocusEvent e) {
		//		this.requestFocusInWindow();
		//		this.setFocusableWindowState(true);
		//		this.toFront();
	}

}

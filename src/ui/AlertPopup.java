package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class AlertPopup extends JFrame implements ActionListener{

	public AlertPopup(String s){
		this.add(new JLabel("Allergy alert: "+s));
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

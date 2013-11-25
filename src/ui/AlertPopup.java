package ui;


import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class AlertPopup extends JFrame implements ActionListener, KeyListener{

	public AlertPopup(String s){
		super.setLayout(new MigLayout());
		this.setSize(250,130);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.add(new JLabel("Allergy alert: "+s),"wrap,center");
		JButton delete = new JButton("Delete Drug");
		JButton ignore = new JButton("Ignore");
		ignore.addActionListener(this);
		ignore.addKeyListener(this);
		this.add(delete);
		this.add(ignore);
		this.pack();
		this.setVisible(true);
		delete.requestFocus();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER ||e.getKeyCode() == KeyEvent.VK_SPACE){
			dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

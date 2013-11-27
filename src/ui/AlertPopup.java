package ui;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class AlertPopup extends JFrame implements ActionListener, KeyListener{

	private final JTextField evFld;
	private final SuggestionPanel.FirableBox evBox;
	private final JButton delete;
	private final JButton ignore;
	
	public AlertPopup(String s, JTextField eventField, SuggestionPanel.FirableBox eventBox){
		super.setLayout(new MigLayout());
		
		this.evFld = eventField;
		this.evBox = eventBox;
		
		this.setSize(250,130);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.add(new JLabel("Allergy alert: "+s),"wrap,center");
		delete = new EnterButton("Delete Drug");
		DelFun delF = new DelFun();
		delete.addActionListener(delF);
		delete.addKeyListener(delF);
		ignore = new EnterButton("Ignore");
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
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT ||e.getKeyCode() == KeyEvent.VK_RIGHT){
			this.delete.requestFocus();
		}
	}
	
	private class DelFun implements ActionListener, KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT ||e.getKeyCode() == KeyEvent.VK_RIGHT){
				ignore.requestFocus();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			evFld.setText("");
			evBox.setFired(false);
			AlertPopup.this.dispose();
		}
	}//inner class
	
}

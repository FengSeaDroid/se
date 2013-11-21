package ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JWindow;

import ui.PinView;

public class PrintButtonCreateWindow implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFrame jf=new JFrame();
		jf.setSize(300, 140);
		jf.setLocationRelativeTo(null);
		
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//lock this window and stop user from modifying main window.
		jf.setAlwaysOnTop(true);
		
		jf.setVisible(true);
		
		jf.add(new PinView().drawPinView());
		
	}

}

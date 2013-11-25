package unused;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JWindow;

import control.MainControl;
import ui.PinView;

public class PrintButtonCreateWindow implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(MainControl.getMainControl().isLocum())
		{
			//Call print PDF directly without showing the pin
		}
		else
		{
			PinView pinview=new PinView();

		}

		
	}

}

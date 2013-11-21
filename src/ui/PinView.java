package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import net.miginfocom.swing.MigLayout;

public class PinView extends JPanel {

	public JPanel drawPinView() {
		// TODO Auto-generated constructor stub
		JPanel jp=new JPanel(new MigLayout("wrap 3","[grow][grow][grow]","[][][][]"));
		
		JLabel msg=new JLabel("Please type in your pin number :");
		
		jp.add(msg,"span 4,align center");
		jp.add(drawPin(),"span 4,align center");
		
		JButton bt_ok=new JButton("ok");
		JButton bt_cancel=new JButton("cancel");
		
		jp.add(bt_ok,"span 2,align center");
		jp.add(bt_cancel,"span 2,align center");
		return jp;
	}

	public JPanel drawPin(){
		JPanel jp=new JPanel();
		
		JPasswordField jpf=new JPasswordField(10);
		jpf.setSize(100, 100);
		
		jp.add(jpf);
		return jp;
	}
	
	public static void main(String[] args){
		JFrame jf=new JFrame();
		jf.setSize(300, 140);
		jf.add(new PinView().drawPinView());
		jf.setVisible(true);
		jf.setLocationRelativeTo(null);
	}
	
}

package ui;

import java.awt.Image;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;

public class LeftSideAll extends JPanel {

	/**
	 * @param args
	 */
	public JSplitPane drawLeftSideAll(){
		
		JSplitPane jsp= new JSplitPane(JSplitPane.VERTICAL_SPLIT,new PatientSearchView(),drawDownside());
		
		
		return jsp;
	}
	
	public JPanel drawDownside(){
		JPanel jp=new JPanel(new MigLayout("wrap 4","[][][grow][grow]","[][grow][grow][grow]")); 
		
		JComponent jc=new ClinicInfoView(false);
		jc.setBorder((BorderFactory.createEmptyBorder()));
//		jc.setPreferredSize();
		jp.add(jc,"span,h 100");
		
		URL url = ClassLoader.getSystemResource("Rx.jpg");
		ImageIcon img = new ImageIcon(url);
		img.setImage(img.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
		JLabel jl2=new JLabel(img);
		jp.add(jl2,"cell 2 1");
		
		
		JComponent jc2=new DrugLineView();
		jc2.setBorder((BorderFactory.createEmptyBorder()));
		jp.add(jc2,"cell 3 2,span 3 2");
		
		return jp;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf= new JFrame();
		jf.setSize(800, 700);
		jf.add(new LeftSideAll().drawLeftSideAll());
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}

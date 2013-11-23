package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import ui.listener.PrintButtonCreateWindow;
import ui.listener.PrintButtonSubmit;
import net.miginfocom.swing.MigLayout;
import control.MainControl;

@SuppressWarnings("serial")
public class NewDrugLineView extends JPanel{

	public static NewDrugLineView getDrugLineView(){
		return currentDrugLineView;
	}
	
	public static NewDrugLineView getHistoryDrugLineView(){
		return historyDrugLineView;
	}
	
	private static NewDrugLineView currentDrugLineView = new NewDrugLineView();
	private static NewDrugLineView historyDrugLineView = new NewDrugLineView();
	
	private JButton printbutton;
	private JPanel[] innerPanels;

	private NewDrugLineView() {
		super(new MigLayout("wrap 3","0[grow]0[grow]0[grow]0","0[]0[]0[]0[]0"));
				innerPanels = SuggestionPanel.fillerWithScroll(MainWindow.d.width*2/3-80,200);
		innerPanels[0].setBorder(BorderFactory.createTitledBorder(""));
		this.add(innerPanels[0],"wrap");
		this.add(drawReSigAndDate(),"wrap");
		this.add(this.buttonView(),"wrap,span 3,center");
	}
	
	public void populate(String s){
		Set<String> stringSet = new HashSet<String>();
		stringSet.add(s);
		((VanillaPanel) this.innerPanels[1]).populate(stringSet,true);
	}

	//print button here.add function now
	private JComponent buttonView(){
		JPanel printView = new JPanel(new MigLayout());

		printbutton=new JButton("Print");
		printbutton.addActionListener(new PrintButtonSubmit());
		printbutton.addActionListener(new PrintButtonCreateWindow());
		printView.add(printbutton);
		return printView;

	}

	private JPanel drawReSigAndDate(){
		JPanel jp=new JPanel(new MigLayout("wrap 2","[grow][grow]","[][]"));
		jp.setPreferredSize(new Dimension(this.getMaximumSize().width, 100));
		//jp.setBorder(BorderFactory.createLineBorder(Color.red));
		
		JPanel jp1 = new JPanel(new MigLayout("wrap 2","[]","[][]"));
		jp1.add(new JLabel("Refill:"),"align left");
		JTextField jt5=new JTextField(3);
		jp1.add(jt5,"align left");
		
		jp.add(jp1,"align left");
		
		JPanel jp2 = new JPanel(new MigLayout("wrap 2","[]","[][]"));
		jp2.add(new JLabel("Date:"),"align left");
		
		
		JTextField jt55=new JTextField(9);
		jt55.setName("effectiveDateView");
		//datechooser here
		DateChooser dateChooser = DateChooser.getInstance("yyyy-MM-dd");
		dateChooser.register(jt55);
		
		//
		jp2.add(jt55,"align left");
		
		jp.add(jp2,"align right,wrap");
		
		JPanel jp3 = new JPanel();
		jp3.add(new JLabel("Signature"));
		
		Image image;
		image=this.getToolkit().createImage(MainControl.getMainControl().getPatientManager().getSignature(MainControl.getMainControl().getPhysicianID()));
		ImageIcon icon=new ImageIcon(image);
		icon.setImage(image.getScaledInstance(100, 30,Image.SCALE_DEFAULT));
		JLabel imageLable=new JLabel();
		imageLable.setIcon(icon);
		
		jp3.add(imageLable);
		
		jp.add(jp3,"span 2,align right");
		
		return jp;
	}
}

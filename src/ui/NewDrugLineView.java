package ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import control.MainControl;

@SuppressWarnings("serial")
public class NewDrugLineView extends JPanel implements ActionListener{
	
	private JPanel buttonView;
	
	private JPanel dateView;
	
	private JTextField effectiveDate;
	public String getEffectiveDate(){
		return effectiveDate.getText();
	}
	private JPanel[] innerPanels;

	public NewDrugLineView() {
		super(new MigLayout("wrap 3","0[grow]0[grow]0[grow]0","0[]0[]0[]0[]0"));
				innerPanels = SuggestionPanel.fillerWithScroll(MainWindow.d.width*2/3-80,200);
		innerPanels[0].setBorder(BorderFactory.createTitledBorder(""));
		this.add(innerPanels[0],"wrap");
		
		dateView = sigAndDate();
		buttonView = buttonView();
				
		this.add(dateView,"wrap");
		this.add(buttonView,"wrap,span 3,center");
	}
	
	/**
	 * 
	 * @param v
	 */
	public void setEdible(boolean v){
		this.buttonView.setVisible(v);
		this.dateView.setVisible(v);
	}

	public void populate(String s,boolean edible){
		Set<String> stringSet = new HashSet<String>();
		stringSet.add(s);
		((VanillaPanel) this.innerPanels[1]).populate(stringSet,edible);
	}
	
	public Set<String> pull(){
		return new HashSet<String>(((VanillaPanel) innerPanels[1]).pull());
	}

	private JPanel sigAndDate(){
		JPanel jp=new JPanel(new MigLayout("wrap 2","[grow][grow]","[][]"));
		jp.setPreferredSize(new Dimension(this.getMaximumSize().width, 100));
		//jp.setBorder(BorderFactory.createLineBorder(Color.red));
		
		JPanel refillPanel = new JPanel(new MigLayout("wrap 2","[]","[][]"));
		refillPanel.add(new JLabel("Refill:"),"align left");	
		JTextField refill=new JTextField(3);
		refillPanel.add(refill,"align left");
		
		jp.add(refillPanel,"align left");
		
		JPanel effectiveDatePanel = new JPanel(new MigLayout("wrap 2","[]","[][]"));
		effectiveDatePanel.add(new JLabel("Effective Date:"),"align left");

		effectiveDate=new JTextField(9);
//		effectiveDate.setName("effectiveDateView");
		//datechooser here
		DateChooser dateChooser = DateChooser.getInstance("yyyy-MM-dd");
		dateChooser.register(effectiveDate);
		effectiveDatePanel.add(effectiveDate,"align left");
		jp.add(effectiveDatePanel,"align right,wrap");

		JPanel signaturePanel = new JPanel();
		signaturePanel.add(new JLabel("Signature"));
		Image image;
		image=this.getToolkit().createImage(MainControl.getMainControl().getPatientManager().getSignature(MainControl.getMainControl().getPhysicianID()));
		ImageIcon icon=new ImageIcon(image);
		icon.setImage(image.getScaledInstance(100, 30,Image.SCALE_DEFAULT));
		JLabel imageLable=new JLabel();
		imageLable.setIcon(icon);
		signaturePanel.add(imageLable);
		jp.add(signaturePanel,"span 2,align right");
		return jp;
	}

	//print button here.add function now
	private JPanel buttonView(){
		JPanel printView = new JPanel(new MigLayout());
		JButton printButton = new JButton("Print");
		printButton.addActionListener(this);
		printView.add(printButton);
		return printView;
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(MainControl.getMainControl().isLocum())
		{
			//Call print PDF directly without showing the pin
			try {
				MainControl.getMainControl().print(MainWindow.drugLineView.pull(), MainWindow.drugLineView.getEffectiveDate());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			new PinView();

		}
	}
}

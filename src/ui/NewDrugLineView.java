package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import uiapi.DateChooser;
import uiapi.TextPrompt;
import net.miginfocom.swing.MigLayout;
import control.MainControl;

@SuppressWarnings("serial")
public class NewDrugLineView extends JPanel implements ActionListener, FocusListener{
	
	private JPanel buttonView;
	private JPanel dateView;
	
	private JTextField effectiveDate;
	public String getEffectiveDate(){
		if (effectiveDate.getText().equals("")||effectiveDate.getText().equals(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()))){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
		}
		else {
			return effectiveDate.getText();
		}
	}
	
	public void setEffectiveDate(String s){
		this.effectiveDate.setText(s);
	}
	
	private JLabel printMessage;
	private JLabel dateMessage;
	private JTextField refill;
	private DateChooser dateChooser;
	
	private JPanel[] innerPanels;

	public NewDrugLineView() {
		super(new MigLayout("wrap 3","0[grow]0[grow]0[grow]0","0[]0[]0[]0[]0"));
				innerPanels = SuggestionPanel.fillerWithScroll(MainWindow.d.width*2/3-80, MainWindow.d.height*2/5);
		innerPanels[0].setBorder(BorderFactory.createTitledBorder(""));
		this.add(innerPanels[0],"wrap");
		
		dateView = sigAndDate();
		buttonView = buttonView();
		
		refill.setDocument(new JTextFieldLimit(3));
//		effectiveDate.setDocument(new JTextFieldLimit(12));
				
		this.add(dateView,"wrap");
		this.add(buttonView,"wrap,span 3,center");
	}
	
	/**
	 * 
	 * @param v
	 */
	public void disable(){
		this.buttonView.setVisible(false);
		this.dateView.setVisible(true);
		this.effectiveDate.setEditable(false);
		this.refill.setEditable(false);
		for (MouseListener ml:effectiveDate.getMouseListeners()){
			effectiveDate.removeMouseListener(ml);
		}
//		dateChooser.disable();
		

	}

	public void populate(String s,boolean edible){
		Set<String >stringSet = new HashSet<String>();
		if (s != null){
			stringSet.add(s);
		}
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

		JLabel refillText = new JLabel("Refill:");
		refillPanel.add(refillText,"align left");

		refill=new JTextField(3);
		refillPanel.add(refill,"align left");
		jp.add(refillPanel,"align left");
		
		JPanel effectiveDatePanel = new JPanel(new MigLayout("wrap 2","[]","[][]"));
		effectiveDatePanel.add(new JLabel("Effective Date:  "),"align left");
		
		effectiveDate=new JTextField(10);
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		TextPrompt effectiveDatePrompt =new TextPrompt(date,effectiveDate);
		effectiveDatePrompt.setShow(uiapi.TextPrompt.Show.FOCUS_LOST);
		effectiveDatePrompt.changeAlpha(0.5f);
		effectiveDate.setToolTipText("Input format: YYYY-MM-DD");
		javax.swing.ToolTipManager.sharedInstance().setInitialDelay(0);
		DateListener dateListener = new DateListener();
		effectiveDate.getDocument().addDocumentListener(dateListener);
		effectiveDate.addFocusListener(dateListener);
//		effectiveDate.setName("effectiveDateView");
		//datechooser here
		dateChooser = DateChooser.getInstance("yyyy-MM-dd");
		dateChooser.register(effectiveDate);
		effectiveDatePanel.add(effectiveDate,"align left, wrap");
		dateMessage = new JLabel(" ");
		dateMessage.setForeground(Color.RED);
		effectiveDatePanel.add(dateMessage,"align right, span");
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

	public String getRefill() {
		if (this.refill.getText().equals("")){
			return "0";
		}
		else{
			return this.refill.getText();
		}
	}

	public void setRefill(String refill) {
		this.refill.setText(refill);
	}

	//print button here.add function now
	private JPanel buttonView(){
		JPanel printView = new JPanel(new MigLayout());
		printMessage = new JLabel(" ");
		printMessage.setForeground(Color.RED);
		printView.add(printMessage,"wrap,center");
		
		JButton printButton = new EnterButton("Print");
		printButton.addActionListener(this);
		printButton.addFocusListener(this);
		printView.add(printButton,"center");
		return printView;
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pull().size()==0||MainControl.getMainControl().getCurrentPatient()==null){
			printMessage.setText("Cannot print empty prescription.");
		}
		else{
			if(MainControl.getMainControl().isLocum())
			{
				//Call print PDF directly without showing the pin
				try {
					MainControl.getMainControl().print(MainWindow.drugLineView.pull(), MainWindow.drugLineView.getEffectiveDate(),MainWindow.drugLineView.getRefill());
					MainWindow.clear();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				new PinView(this.getRefill());
	
			}
		}
	}

	public void clear() {
		((VanillaPanel) this.innerPanels[1]).clear();
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		this.printMessage.setText(" ");
	}
	
	class DateListener implements DocumentListener, FocusListener {
		
		private boolean dateFlag;
		
		public DateListener(){
			this.dateFlag = false;
		}

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if (this.dateFlag == false){
				effectiveDate.setText("");
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if (effectiveDate.getText().length()==10){
				Date inputDate, currentDate;

				try {
					inputDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(effectiveDate.getText());
					currentDate=Calendar.getInstance().getTime();
					if (inputDate.before(currentDate)){
						dateMessage.setText("Date should not be earlier than today.");
					}
					else {
						this.dateFlag = true;
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
//			System.out.println(effectiveDate.getText().length());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			dateMessage.setText(" ");
		}
		
	}
}
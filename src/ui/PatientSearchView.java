package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import ui.PrescriptionHistoryView.HistoryTableCellRenderer;
import control.MainControl;
import domain.Patient;
import domain.Prescription;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings({"serial","rawtypes"})
//public class PatientSearchView extends JPanel implements ActionListener, PatientSearchView.Filler{
public class PatientSearchView extends JPanel implements ActionListener{

//	private Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	private Set<String> allergy = new HashSet<String>();
	private JComboBox nameField = new JComboBox();

	private JTextField mcpField = new JTextField(20);
	private JLabel searchFail = new JLabel();
	private JLabel DOB = new JLabel();
	private JLabel weight = new JLabel();
	private JLabel address = new JLabel();
	private JLabel tel = new JLabel();


	/**
	 * create the view
	 */
	@SuppressWarnings("unchecked")
	public PatientSearchView() {
		super(new MigLayout("", "50[100][250][100][250]50", ""));
		mcpField.setDocument(new JTextFieldLimit(12));

		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setFont(new Font(nameLabel.getFont().getFontName(),Font.BOLD,nameLabel.getFont().getSize()+2));
		this.add(nameLabel,"cell 0 0,align right");
		nameField.setEditable(true);
		nameField.setPreferredSize(new Dimension(MainWindow.d.width*1/3,10));
		//delete the arrow
		nameField.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				JButton b = new JButton() {
					@Override
					public int getWidth() {
						return 0;
					}
				};
				b.setVisible(false);
				return b;
			}
		});
		nameField.setRenderer(new ComboBoxRenderer());
		JTextField jt = (JTextField) nameField.getEditor().getEditorComponent();
		jt.setBorder(BorderFactory.createEmptyBorder(2,10, 2, 2));
//		jt.setDocument(new JTextFieldLimit(30));
		//create the model
		SearchNameModel sbm = new SearchNameModel(this,nameField,MainControl.getMainControl().getPatientManager().getPatientList());
		//set the model on the combobox
		nameField.setModel(sbm);
		//set the model as the item listener also
		nameField.addItemListener(sbm);
		this.add(nameField, "cell 1 0, span 3");

		JLabel mcpLabel = new JLabel("MCP: ");
		mcpLabel.setFont(new Font(mcpLabel.getFont().getFontName(),Font.BOLD,mcpLabel.getFont().getSize()+2));
		this.add(mcpLabel,"cell 0 1,align right");
		mcpField.setPreferredSize(new Dimension(this.getMaximumSize().width,30));
		mcpField.setBorder(BorderFactory.createEmptyBorder(2,10, 2, 2));
		this.add(mcpField, "cell 1 1");
		mcpField.addActionListener(this);
		
		searchFail.setForeground(Color.RED);
		this.add(searchFail,"cell 2 1, span 2");

		JLabel lblAddress = new JLabel("Address: ");
		add(lblAddress, "cell 0 2,align right");
		this.add(address, "cell 1 2");

		JLabel telLabel = new JLabel("Tel: ");
		add(telLabel, "cell 2 2,align right");
		this.add(tel, "cell 3 2");

		JLabel dobLabel = new JLabel("DOB: ");
		this.add(dobLabel,"cell 0 3,align right");
		this.add(DOB, "cell 1 3");

		JLabel label_2 = new JLabel("Weight: ");
		this.add(label_2,"cell 2 3,align right");
		add(weight, "cell 3 3");
		
	}
	
	public void setEdible(boolean edible){
		this.nameField.setEnabled(edible);
		this.mcpField.setEnabled(edible);
		((JTextComponent) nameField.getEditor().getEditorComponent()).setDisabledTextColor(Color.BLACK);
		mcpField.setDisabledTextColor(Color.BLACK);
	}

	public void actionPerformed(ActionEvent e){
		this.fill(mcpField.getText());
	}

	public void fill(String mcp){
		if (this==MainWindow.patientSearch){
			MainWindow.clear();
		}
//		MainWindow.clear();
		Patient patient = MainControl.getMainControl().lookupPatient(mcp);
//		this.clear();
//		System.out.println("it should be cleared");
		if (patient != null){
			this.searchFail.setText("");
			this.nameField.getEditor().setItem(patient.getName());
			this.mcpField.setText(patient.getMCP());
			this.DOB.setText(patient.getDateOfBirth());
			this.weight.setText(patient.getWeight()+" Kg");
			this.address.setText(patient.getAddress());
			this.tel.setText(patient.getTel());
			this.allergy = patient.getAllergy();
			this.revalidate();
			
			//if not, it's used for the history window
			if (this == MainWindow.patientSearch){
				MainWindow.patientAllergy.populate(allergy, false);	
				//populate the history table
//				this.prescriptionHistory = patient.getPrescriptionHistory();
				List <Prescription> preList = new ArrayList <Prescription>(patient.getPrescriptionHistory());
				Collections.sort(preList);
				boolean flag = false;
				for (int i = 0; i < preList.size(); i++){
					for(String s:preList.get(i).getDrugLines()) {
//						drugsInHistory.add(s);
						StringTokenizer st = new StringTokenizer(s, " "); 
						String key = st.nextToken(); 
						String[] data = {preList.get(i).getIssueDate(),key};
						MainWindow.patientPrescriptionHistory.getModel().addRow(data);
						if (flag == true){
						((HistoryTableCellRenderer) MainWindow.patientPrescriptionHistory.getTable().getDefaultRenderer(Object.class)).add(new Integer(MainWindow.patientPrescriptionHistory.getTable().getRowCount()-1));
						}
					}//loop through the drugs of a prescription
					flag = !flag;
				}
				//to give the focus
				MainWindow.drugLineView.clear();
			}
		}
		else {
			this.searchFail.setText("Patient is not found");
			this.revalidate();
			this.setFocus();
		}
	}

	//inner class
	
	public void clear() {
		this.nameField.getEditor().setItem("");
		this.mcpField.setText("");
		this.DOB.setText("");
		this.weight.setText("");
		this.address.setText("");
		this.tel.setText("");
//		this.prescriptionHistory.clear();
		this.allergy.clear();
	}
	
	public void setFocus(){
		this.nameField.requestFocusInWindow();
	}

	public class SearchNameModel extends AbstractListModel implements ComboBoxModel, KeyListener, ItemListener{
		private Set<String> db = new HashSet<String>();
		private ArrayList<String> data = new ArrayList<String>();
		private String selection;
		private JComboBox cb;
		private ComboBoxEditor cbe;
		private int currPos = 0;
//		private Filler filler;
		private PatientSearchView filler;
		private String mcp;

//		public SearchNameModel(Filler f,JComboBox jcb, Set<String> textList) {
		public SearchNameModel(PatientSearchView f,JComboBox jcb, Set<String> textList) {

			cb = jcb;
			cbe = jcb.getEditor();
			//here we add the key listener to the text field that the combobox is wrapped around
			cbe.getEditorComponent().addKeyListener(this);

			//set up our "database" of items - in practice you will usuallu have a proper db.
			//db.addAll(MainControl.getMainControl().getFormulary().getAllDrugSet());
			db.addAll(textList);
			filler = f;
		}

		public void updateModel(String in){
			data.clear();
			//lets find any items which start with the string the user typed, and add it to the popup list
			//here you would usually get your items from a database, or some other storage...
			for(String s:db)
				//trim for space
				if(s.startsWith(in.trim())&&!in.trim().equals(""))
					data.add(s);

			super.fireContentsChanged(this, 0, data.size());

			//this is a hack to get around redraw problems when changing the list length of the displayed popups
			cb.hidePopup();
//			cb.showPopup();
			if(data.size() != 0)
				cb.showPopup();
				cb.setSelectedIndex(-1);
		}

		public int getSize(){
			return data.size();
		}

		public Object getElementAt(int index){
			return data.get(index);
		}

		public void setSelectedItem(Object anItem){
			selection = (String) anItem;
		}

		public Object getSelectedItem(){
			return selection;
		}

		public void keyTyped(KeyEvent e){}
		public void keyPressed(KeyEvent e){}

		public void keyReleased(KeyEvent e)
		{
			String str = cbe.getItem().toString();
			JTextField jtf = (JTextField)cbe.getEditorComponent();
			currPos = jtf.getCaretPosition();

			if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED){
				if(e.getKeyCode() != KeyEvent.VK_ENTER ){
					//limit the length
//					if(str.length()>30){
//						str = str.substring(0, 30);
//					}

					cbe.setItem(str);
					jtf.setCaretPosition(currPos);

				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				cb.setSelectedIndex(cb.getSelectedIndex());
				JTextField jt = (JTextField)cb.getEditor().getEditorComponent();
//				System.out.println(jt.getText());
				if (jt.getText().split(";").length==3){
					mcp=jt.getText().split(";")[2];
					filler.fill(mcp);
				}
				else{
					filler.fill("000000000000");
				}
			}
//			else if(e.getKeyCode() == KeyEvent.VK_TAB){
//				MainWindow.drugLineView.clear();
//			}
			else {
				updateModel(cb.getEditor().getItem().toString());
				//limit the length
//				if(str.length()>30){
//					str = str.substring(0, 30);
//				}
				cbe.setItem(str);
				jtf.setCaretPosition(currPos);
			}
		}

		public void itemStateChanged(ItemEvent e)
		{
			cbe.setItem(e.getItem().toString());
			cb.setSelectedItem(e.getItem());
		}
	}//inner class

	class ComboBoxRenderer extends javax.swing.plaf.basic.BasicComboBoxRenderer  
	{  
		public ComboBoxRenderer()  
		{  
			super();  
			setOpaque(true);  
		}  
		public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)  
		{  
			setText(value.toString());  
			if(isSelected) {
				setBackground(Color.decode("#335b8e")); 
				setForeground(Color.WHITE);
			}
			else {setBackground(Color.WHITE); setForeground(Color.BLACK);}
			return this;  
		}  
	}//inner class

}//class


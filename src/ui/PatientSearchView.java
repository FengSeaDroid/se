package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.JTextComponent;

import control.MainControl;
import domain.Patient;
import domain.Prescription;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings({"serial","rawtypes"})
public class PatientSearchView extends JPanel implements ActionListener, Filler{


	private Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	private Set<String> allergy = new HashSet<String>();
	private JComboBox nameField = new JComboBox();

	private JTextField mcpField = new JTextField(20);
	private JLabel DOB = new JLabel();
	private JLabel weight = new JLabel();
	private JLabel address = new JLabel();
	private JLabel tel = new JLabel();


	/**
	 * create the view
	 */
	@SuppressWarnings("unchecked")
	public PatientSearchView() {
		super(new MigLayout("wrap 8", "[][grow][][][grow][][][][]", "[][][][][][][]"));

		JLabel label_3 = new JLabel("Name");
		this.add(label_3,"cell 1 0,alignx right,gapx unrelated");

		nameField.setEditable(true);
		nameField.setPreferredSize(new Dimension(this.getMaximumSize().width,10));
		//set size
		nameField.setPrototypeDisplayValue("1234567890123456789012345678");

		//delete the arrow
		nameField.setUI(new BasicComboBoxUI() {
			@Override
			protected JButton createArrowButton() {
				return new JButton() {
					@Override
					public int getWidth() {
						return 0;
					}
				};
			}
		});
		nameField.setRenderer(new ComboBoxRenderer());
		((JComponent) nameField.getEditor().getEditorComponent()).setBorder(BorderFactory.createEmptyBorder(2,10, 2, 2));

		//create the model
		SearchNameModel sbm = new SearchNameModel(this,nameField,MainControl.getMainControl().getPatientManager().getPatientList());
		//set the model on the combobox
		nameField.setModel(sbm);
		//set the model as the item listener also
		nameField.addItemListener(sbm);
		this.add(nameField, "cell 2 0, wrap");

		JLabel label = new JLabel("MCP");
		this.add(label,"cell 1 1,alignx right,gapx unrelated");
		mcpField.setPreferredSize(new Dimension(this.getMaximumSize().width,30));
		this.add(mcpField, "cell 2 1");
		mcpField.addActionListener(this);

		JLabel lblAddress = new JLabel("Address: ");
		add(lblAddress, "cell 0 2");
		this.add(address, "cell 1 2");

		JLabel lblTell = new JLabel("Tel: ");
		add(lblTell, "cell 2 2,alignx right");
		this.add(tel, "cell 4 2");

		JLabel dobLabel = new JLabel("DOB: ");
		this.add(dobLabel,"cell 0 3,alignx trailing,gapx unrelated");
		this.add(DOB, "cell 1 3,growx");

		JLabel label_2 = new JLabel("Wieght: ");
		this.add(label_2,"cell 2 3,alignx right,gapx unrelated");
		add(weight, "cell 4 3,growx");
		
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
		Patient patient = MainControl.getMainControl().lookupPatient(mcp);
		MainWindow.clear();
		if (patient != null){
			this.nameField.getEditor().setItem(patient.getName());
			this.mcpField.setText(patient.getMCP());
			this.DOB.setText(patient.getDateOfBirth());
			this.weight.setText(patient.getWeight());
			this.address.setText(patient.getAddress());
			this.tel.setText(patient.getTel());
			this.allergy = patient.getAllergy();

			MainWindow.patientAllergy.populate(allergy, false);
			
			//take care below
			this.prescriptionHistory = patient.getPrescriptionHistory();
			List <String> drugsInHistory = new ArrayList<String>();
			for(Prescription p: prescriptionHistory)

			{
				for(String s:p.getDrugLines()) {
					drugsInHistory.add(s);
					StringTokenizer st = new StringTokenizer(s, " "); 
					String key = st.nextToken(); 
					String[] data = {p.getIssueDate(),key};
					MainWindow.patientPrescriptionHistory.getModel().addRow(data);
				}
			}
		}
	}

	public class SearchNameModel extends AbstractListModel
	implements ComboBoxModel, KeyListener, ItemListener{
		private Set<String> db = new HashSet<String>();
		private ArrayList<String> data = new ArrayList<String>();
		private String selection;
		private JComboBox cb;
		private ComboBoxEditor cbe;
		private int currPos = 0;
		private Filler filler;
		private String mcp;

		public SearchNameModel(Filler f,JComboBox jcb, Set<String> textList) {

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
				if(s.startsWith(in))
					data.add(s);

			super.fireContentsChanged(this, 0, data.size());

			//this is a hack to get around redraw problems when changing the list length of the displayed popups
			cb.hidePopup();
			cb.showPopup();
			if(data.size() != 0)
				cb.setSelectedIndex(0);
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
					cbe.setItem(str);
					jtf.setCaretPosition(currPos);
				}
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER){
				cb.setSelectedIndex(cb.getSelectedIndex());
				JTextField jt = (JTextField)cb.getEditor().getEditorComponent();
				System.out.println(jt.getText());
				if (jt.getText().split(";").length==3){
					mcp=jt.getText().split(";")[2];
					filler.fill(mcp);
				}
			}
			else {
				updateModel(cb.getEditor().getItem().toString());
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
	}  //inner class

	public void clear() {
		this.nameField.getEditor().setItem("");
		this.mcpField.setText("");
		this.DOB.setText("");
		this.weight.setText("");
		this.address.setText("");
		this.tel.setText("");
	}

}//class


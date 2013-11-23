package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import javax.swing.*;

import control.MainControl;
import ui.PatientSearchView.ComboBoxRenderer;

@SuppressWarnings({"serial","rawtypes"})
public class SuggestionPanel extends VanillaPanel {
	
	public SuggestionPanel(int width) {
		super(width);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Static factory
	 * @param width
	 * @param height
	 * @return
	 */
	public static JPanel[] fillerWithScroll(int width, int height){
		JPanel[] panels ={null,null};
		panels[0] = new JPanel();
		panels[1] = new SuggestionPanel(width);
		JScrollPane scroll = new JScrollPane(panels[1]);
		scroll.setBorder(null);
		scroll.setPreferredSize(new Dimension(width,height));
		panels[0].add(scroll);
		return panels;
	}
	
	/**
	 * create the drugLines
	 * @param s
	 * @param edible
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected JComboBox drugLine(String s,boolean edible){
		JComboBox drug = super.drugLine(s,edible);
		drug.setRenderer(new ComboBoxRenderer());
		SearchBoxModel sbm = new SearchBoxModel(drug,MainControl.getMainControl().getFormulary().getAllDrugSet());
		drug.setModel(sbm);
		drug.addItemListener(sbm);
		JTextField jt = (JTextField)drug.getEditor().getEditorComponent();
		jt.setText(s);
		jt.setEditable(edible);
		return drug;
	}
	
	public class SearchBoxModel extends AbstractListModel
	                implements ComboBoxModel, KeyListener, ItemListener{
	    private Set<String> db = new HashSet<String>();
	    private ArrayList<String> data = new ArrayList<String>();
	    private String selection;
	    private JComboBox cb;
	    private ComboBoxEditor cbe;
	    private int currPos = 0;

	    public SearchBoxModel(JComboBox jcb, Set<String> textList) {

	        cb = jcb;
	        cbe = jcb.getEditor();
	        //here we add the key listener to the text field that the combobox is wrapped around
	        cbe.getEditorComponent().addKeyListener(this);

	        //set up our "database" of items - in practice you will usuallu have a proper db.
	        db.addAll(textList);
//	        db.add("test 12 mg");
//	        db.add("test 22 mg");
//	        db.add("test 32 mg");
//	        db.add("safwan");
//	        db.add("safwan 2 mg");
//	        db.add("safwan 32 mg");
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
	        if(data.size() != 0){
	            cb.setSelectedIndex(-1);
	        }
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

	        if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
	        {
	            if(e.getKeyCode() != KeyEvent.VK_ENTER )
	            {
	                cbe.setItem(str);
	                jtf.setCaretPosition(currPos);
	            }
	        }
	        else if(e.getKeyCode() == KeyEvent.VK_ENTER)
	            cb.setSelectedIndex(cb.getSelectedIndex());
	        else if (cb.getEditor().getItem().toString().length()>4)
	        {
	            updateModel(cb.getEditor().getItem().toString());
	            cbe.setItem(str);
	            jtf.setCaretPosition(currPos);
	        }else{
	            updateModel("!@dummy item");
	            cbe.setItem(str);
	            jtf.setCaretPosition(currPos);
	        }
	    }

	    public void itemStateChanged(ItemEvent e)
	    {
	        cbe.setItem(e.getItem().toString());
	        cb.setSelectedItem(e.getItem());
	    }

	}

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

}

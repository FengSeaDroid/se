package ui;


import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.text.BadLocationException;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Fredseadroid
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class VanillaPanel extends JPanel implements KeyListener  {

	/**
	 * width for the combobox.
	 */
	protected int boxWidth = -1;
	
	
	public static final boolean EDIBLE = true;
	
	public static final boolean INEDIBLE = false;
	/**
	 * The data structure for the combobox.
	 */
	protected List<JComboBox> boxList = new ArrayList<JComboBox>();
	/**
	 * Create the panel.
	 */
	public VanillaPanel(int width) {
		super.setLayout(new MigLayout("", "", "0[]0[]0"));
		this.boxWidth=width;
		boxList.add(drugLine());
		reDraw();
		
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
		panels[1] = new VanillaPanel(width);
		JScrollPane scroll = new JScrollPane(panels[1]);
		scroll.setBorder(null);
		scroll.setPreferredSize(new Dimension(width,height));
		panels[0].add(scroll);
		return panels;
	}
	
	/**
	 * Clear all data. can also get focus!!!
	 */
	public void clear(){
		this.boxList.clear();
		boxList.add(drugLine());
		reDraw();
		this.boxList.get(0).requestFocus();
	}
	
	/**
	 * I give you all my drug lines.
	 * @return 	druglines
	 */
	public Set<String> pull(){
		Set<String> output = new HashSet<String>();
		for (int i = 0; i<this.boxList.size();i++){
			JTextField jt = (JTextField)this.boxList.get(i).getEditor().getEditorComponent();
			if (!jt.getText().equals("")){
				output.add(jt.getText());
			}
		}
		return output;
	}
	
	/**
	 * You give me and I'll show.
	 * @pre		the input string set should not contain same strings. edible or not.
	 * @param 	drugLines
	 * @param	edible 	
	 * 					whether the populate panel is edible or not
	 */
	public void populate(Set<String> drugLines, boolean edible){
		for (int i = 0; i<this.boxList.size();i++){
			JTextField jt = (JTextField)this.boxList.get(i).getEditor().getEditorComponent();
			if (!jt.getText().equals("")){
				drugLines.add(jt.getText().trim());
			}
		}
		this.boxList.clear();
		List<String> sort = new ArrayList<String>(drugLines);
		Collections.sort(sort);
		for (int i=0; i<sort.size(); i++){
			this.boxList.add(drugLine(sort.get(i).trim(),edible));
		}
		reDraw();
		this.boxList.get(this.boxList.size()-1).requestFocus();
	}
	
	/**
	 * create the drugLines
	 * @param s
	 * @param edible
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected JComboBox drugLine(String s,boolean edible){
		JComboBox drug = new JComboBox();
		int width = (int) (boxWidth*.9);
		drug.setPreferredSize(new Dimension(width,30));
//		System.out.println(boxWidth);
		drug.setEditable(true);
//		drug.setPrototypeDisplayValue("1234567890123456");
		drug.setUI(new BasicComboBoxUI() {
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

		JTextField jt = (JTextField)drug.getEditor().getEditorComponent();
		jt.addKeyListener(this);
		jt.setText(s);
		jt.setEditable(edible);
		return drug;
	}
	
	protected JComboBox drugLine(){
		return this.drugLine("",true);
	}
	
	/**
	 * @pre		pos>0
	 * @pre 	length>pos
	 * @param 	index
	 * @param 	pos
	 * @post	
	 */
	protected void enterPress(int index, int pos) {
		int newRow = index + 1;
		JTextField jt = (JTextField)this.boxList.get(index).getEditor().getEditorComponent();
		int length = jt.getText().length();
		assert length>pos;
		assert pos>0;
		
		try {
			this.boxList.add(newRow, drugLine(jt.getText(pos, length-pos),EDIBLE));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			jt.setText(jt.getText(0, pos));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reDraw();
		this.boxList.get(newRow).requestFocus();
		jt = (JTextField)this.boxList.get(index+1).getEditor().getEditorComponent();
		jt.setCaretPosition(0);
	}
	
	/**
	 * @pre		index >0
	 * @pre		index < boxList.size()
	 * @param 	index
	 */
	protected void deleteLine(int index){
		assert index >0;
		assert index < this.boxList.size();
		JTextField jt = (JTextField)this.boxList.get(index).getEditor().getEditorComponent();
		String remainder = jt.getText();
		this.boxList.remove(index);
		reDraw();
		if (index>0){
			jt = (JTextField)this.boxList.get(index-1).getEditor().getEditorComponent();
			int currentLength = jt.getText().length();
			jt.setText(jt.getText()+remainder);
			this.boxList.get(index-1).requestFocus();
			jt.setCaretPosition(currentLength);
		}
	}
	
	protected void reDraw(){
		this.removeAll();
		for (int i = 0; i<this.boxList.size();i++){
			this.add(this.boxList.get(i),"wrap, center");
		}
		this.revalidate();
		this.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			JTextField jt = (JTextField)e.getSource();
			int pos = jt.getCaretPosition();
			int i=this.boxList.indexOf(jt.getParent());
			enterPress(i,pos);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			JTextField jt = (JTextField)e.getSource();
			if (jt.getCaretPosition()==0){
				int i=this.boxList.indexOf(jt.getParent());
				if (i>0){
					deleteLine(i);
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DELETE){
			JTextField jt = (JTextField)e.getSource();
			int i=this.boxList.indexOf(jt.getParent());

			if (jt.getCaretPosition()==jt.getText().length()) {
				if (i < this.boxList.size()-1){
					jt = (JTextField)this.boxList.get(i+1).getEditor().getEditorComponent();
					jt.setText("-"+jt.getText());
					deleteLine(i+1);
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
			JTextField jt = (JTextField)e.getSource();
			JComboBox tempBox = (JComboBox)jt.getParent();
			int i=this.boxList.indexOf(tempBox);
			//tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()==0
			//the above code means if there's the dropdown menu, don't invoke
			if (tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()==0){	
				int pos = jt.getCaretPosition();
				if (i>0){
					this.boxList.get(i-1).requestFocus();
					JTextField njt = (JTextField)this.boxList.get(i-1).getEditor().getEditorComponent();
					int length = njt.getText().length();
					if (pos <= length){
						njt.setCaretPosition(pos);
					} else {
						njt.setCaretPosition(length);
					}
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			JTextField jt = (JTextField)e.getSource();
			JComboBox tempBox = (JComboBox)jt.getParent();
			int i=this.boxList.indexOf(tempBox);
			
			//tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()== tempBox.getItemCount()-1
			//the above code means if there's the dropdown menu, don't invoke
			if (tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()== tempBox.getItemCount()-1){
				int pos = jt.getCaretPosition();
				if (this.boxList.size()>i+1){
					this.boxList.get(i+1).requestFocus();
					JTextField njt = (JTextField)this.boxList.get(i+1).getEditor().getEditorComponent();
					int length = njt.getText().length();
					if (pos <= length){
						njt.setCaretPosition(pos);
					} else {
						njt.setCaretPosition(length);
					}
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			JTextField jt = (JTextField)e.getSource();
			JComboBox tempBox = (JComboBox)jt.getParent();
			int i=this.boxList.indexOf(tempBox);
			if (i>0 && jt.getCaretPosition()==0){
				this.boxList.get(i-1).requestFocus();
				JTextField njt = (JTextField)this.boxList.get(i-1).getEditor().getEditorComponent();
				int length = njt.getText().length();
				njt.setCaretPosition(length);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			JTextField jt = (JTextField)e.getSource();
			JComboBox tempBox = (JComboBox)jt.getParent();
			int i=this.boxList.indexOf(tempBox);
			if (this.boxList.size()>i+1 && jt.getCaretPosition()==jt.getText().length()){
				this.boxList.get(i+1).requestFocus();
				JTextField njt = (JTextField)this.boxList.get(i+1).getEditor().getEditorComponent();
				njt.setCaretPosition(0);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			
		}
		/**
		 * test: pull data.
		 */
		if(e.getKeyCode() == KeyEvent.VK_F12){
			for(String s:pull()){
				System.out.println(s);
			}
		}
	
		/**
		 * test: populate data, edible.
		 */
		if(e.getKeyCode() == KeyEvent.VK_F11){
			Set<String> testS = new HashSet<String>();
			testS.add("hello");
			testS.add("hello1");
			testS.add("hello2");
			populate(testS,EDIBLE);
		}
		
		/**
		 * test: populate data, inedible.
		 */
		if(e.getKeyCode() == KeyEvent.VK_F9){
			Set<String> testS = new HashSet<String>();
			testS.add("hello11");
			testS.add("hello111");
			testS.add("hello211");
			populate(testS,INEDIBLE);
		}
		
		/**
		 * test: clear data
		 */
		if(e.getKeyCode() == KeyEvent.VK_F10){
			clear();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
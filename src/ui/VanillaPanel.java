package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
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
import javax.swing.text.JTextComponent;

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
			if (!jt.getText().trim().equals("")){
				output.add(jt.getText().trim());
				
			}
		}
//		System.out.println("pulled size: "+output.size());
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
		if (drugLines == null){
			drugLines = new HashSet<String>();
		}
		Set<String> currentSet = new HashSet<String>();
//		for (int i = 0; i<this.boxList.size();i++){
//			JTextField jt = (JTextField)this.boxList.get(i).getEditor().getEditorComponent();
//			if (jt.getText().trim().equals("")||currentSet.contains(jt.getText().trim())){
////				this.boxList.remove(i);
//			}
//			else {
//				currentSet.add(jt.getText().trim());
//			}
//		}
		// use while loop coz the size of the arraylist may change.
		int index = 0;
		while (index < this.boxList.size()){
			JTextField jt = (JTextField)this.boxList.get(index).getEditor().getEditorComponent();
			if (jt.getText().trim().equals("")||currentSet.contains(jt.getText().trim())){
				this.boxList.remove(index);
			}
			else {
				currentSet.add(jt.getText().trim());
				index++;
			}
		}

//		System.out.println("boxList size after: "+this.boxList.size());
		for (String s : drugLines){
//			System.out.println("testing: "+s);
			if ((!currentSet.contains(s.trim()))&&(!s.trim().equals(""))){
				currentSet.add(s.trim());
				this.boxList.add(drugLine(s,edible));
//				System.out.println("added");
			}
		}
		reDraw();
		this.boxList.get(this.boxList.size()-1).requestFocusInWindow();
//		enterPress(this.boxList.size()-1,((JTextComponent) this.boxList.get(this.boxList.size()-1).getEditor().getEditorComponent()).getText().length());
		
		//sorted way will ruin the allergy check flag
//		for (int i = 0; i<this.boxList.size();i++){
//			JTextField jt = (JTextField)this.boxList.get(i).getEditor().getEditorComponent();
//			if (!jt.getText().equals("")){
//				drugLines.add(jt.getText().trim());
//			}
//		}
//		this.boxList.clear();
//		List<String> sort = new ArrayList<String>(drugLines);
//		Collections.sort(sort);
//		for (int i=0; i<sort.size(); i++){
//			this.boxList.add(drugLine(sort.get(i).trim(),edible));
//		}
//		reDraw();
//		this.boxList.get(this.boxList.size()-1).requestFocusInWindow();
	}
	
	/**
	 * create the drugLines
	 * @param s
	 * @param edible
	 * @return
	 */
	protected JComboBox drugLine(String s,boolean edible){
		JComboBox drug = new JComboBox();
//		JComboBox drug = new FirableBox();
		int width = (int) (boxWidth*.9);
		drug.setPreferredSize(new Dimension(width,30));
//		System.out.println(boxWidth);
		drug.setEditable(true);
//		drug.setPrototypeDisplayValue("1234567890123456");
		drug.setUI(new BasicComboBoxUI() {
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

		JTextField jt = (JTextField)drug.getEditor().getEditorComponent();
		jt.setBorder(BorderFactory.createEmptyBorder(2,10, 2, 2));
		jt.addKeyListener(this);
		jt.setText(s);
		jt.setEnabled(edible);
		jt.setDisabledTextColor(Color.BLACK);
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
//		((JComponent) this.getParent()).scrollRectToVisible(jt.getBounds());
		this.revalidate();
		int height = (int)this.getPreferredSize().getHeight();
		Rectangle rect = new Rectangle(0,height,10,10);
        this.scrollRectToVisible(rect);
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
		if (this.boxList.size()==0){
			boxList.add(drugLine());
		}
		for (int i = 0; i<this.boxList.size();i++){
			this.add(this.boxList.get(i),"wrap, center");
		}
		this.revalidate();
		this.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		JTextField jt = (JTextField)e.getSource();
		JComboBox tempBox = (JComboBox)jt.getParent();
		int i = this.boxList.indexOf(tempBox);
		int pos = jt.getCaretPosition();
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
//			JTextField jt = (JTextField)e.getSource();
//			JComboBox tempBox = (JComboBox)jt.getParent();
//			int i=this.boxList.indexOf(tempBox);
//			int pos = jt.getCaretPosition();
//			int i=this.boxList.indexOf(jt.getParent());
			if (tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()==-1){
				enterPress(i,pos);
			}
//			JScrollBar sb = ((JScrollPane) tempBox.getParent().getParent().getParent()).getVerticalScrollBar();
//			System.out.println(sb.getMaximum() );
//			sb.setMaximum(sb.getMaximum()+20);
//			sb.setValue( sb.getMaximum()+190 );
		}
		
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
//			JTextField jt = (JTextField)e.getSource();
			if (jt.getCaretPosition()==0){
//				int i=this.boxList.indexOf(jt.getParent());
				if (i>0){
					deleteLine(i);
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DELETE){
//			JTextField jt = (JTextField)e.getSource();
//			int i=this.boxList.indexOf(jt.getParent());

			if (jt.getCaretPosition()==jt.getText().length()) {
				if (i < this.boxList.size()-1){
					jt = (JTextField)this.boxList.get(i+1).getEditor().getEditorComponent();
					jt.setText("-"+jt.getText());
					deleteLine(i+1);
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP){
//			JTextField jt = (JTextField)e.getSource();
//			JComboBox tempBox = (JComboBox)jt.getParent();
//			int i=this.boxList.indexOf(tempBox);
			//tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()==0
			//the above code means if there's the dropdown menu, don't invoke
			if (tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()==0){	
//				int pos = jt.getCaretPosition();
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
//			JTextField jt = (JTextField)e.getSource();
//			JComboBox tempBox = (JComboBox)jt.getParent();
//			int i=this.boxList.indexOf(tempBox);
			
			//tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()== tempBox.getItemCount()-1
			//the above code means if there's the dropdown menu, don't invoke
			if (tempBox.getModel().getSize()==0 || tempBox.getSelectedIndex()== tempBox.getItemCount()-1){
//				int pos = jt.getCaretPosition();
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
//			JTextField jt = (JTextField)e.getSource();
//			JComboBox tempBox = (JComboBox)jt.getParent();
//			int i=this.boxList.indexOf(tempBox);
			if (i>0 && jt.getCaretPosition()==0){
				this.boxList.get(i-1).requestFocus();
				JTextField njt = (JTextField)this.boxList.get(i-1).getEditor().getEditorComponent();
				int length = njt.getText().length();
				njt.setCaretPosition(length);
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
//			JTextField jt = (JTextField)e.getSource();
//			JComboBox tempBox = (JComboBox)jt.getParent();
//			int i=this.boxList.indexOf(tempBox);
			if (this.boxList.size()>i+1 && jt.getCaretPosition()==jt.getText().length()){
				this.boxList.get(i+1).requestFocus();
				JTextField njt = (JTextField)this.boxList.get(i+1).getEditor().getEditorComponent();
				njt.setCaretPosition(0);
			}
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
//			testS.add("hello");
//			testS.add("hello1");
//			testS.add("hello2");
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

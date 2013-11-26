package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings({"serial","rawtypes"})
public class MousePanel extends VanillaPanel implements MouseListener {
	
	private int originalSize;
	
	public MousePanel(int width) {
		super(width);
	}

	/**
	 * Static factory
	 * @param width
	 * @param height
	 * @return
	 */
	public static JPanel[] fillerWithScroll(int width, int height){
		JPanel[] panels ={null,null,null};
		panels[0] = new JPanel();
		panels[1] = new MousePanel(width);
		panels[0].addMouseListener((MouseListener)panels[1]);
		JScrollPane scroll = new JScrollPane(panels[1]);
		scroll.setPreferredSize(new Dimension(width,height));
		scroll.addMouseListener((MouseListener)panels[1]);
		scroll.setBorder(null);
		panels[0].add(scroll);
		return panels;
	}
	
	@Override
	public void populate(Set<String> drugLines, boolean edible){
		super.populate(drugLines, edible);
		this.originalSize = this.boxList.size();
		for (int i = 0; i<this.boxList.size();i++){
			JTextField jt = (JTextField)this.boxList.get(i).getEditor().getEditorComponent();
			//This part must be here, or not working
			jt.setEnabled(false);
			jt.setDisabledTextColor(Color.BLACK);
			jt.addMouseListener(this);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			JTextField jt = (JTextField)e.getSource();
			JComboBox tempBox = (JComboBox)jt.getParent();
			int i= this.boxList.indexOf(tempBox);
			if (i>this.originalSize){
				super.keyPressed(e);
			} else {
				System.out.println(this.originalSize);
			}
		} else {
			super.keyPressed(e);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2){
			JTextField tempJT = (JTextField)this.boxList.get(this.boxList.size()-1).getEditor().getEditorComponent();
			enterPress(this.boxList.size()-1,tempJT.getText().length());
//			JComponent source = (JComponent) e.getSource();
////			System.out.println(source.getParent().getParent().getParent().getParent().getClass().toString());
//			if ( source.getParent().getClass() == JComboBox.class){
////				System.out.println("JScrollPane");
////				JScrollBar vertical = ((JScrollPane) source.getParent().getParent().getParent().getParent()).getVerticalScrollBar();
////				vertical.setValue( vertical.getMaximum());
////				((JScrollPane) source.getParent().getParent().getParent().getParent()).setViewportView(this);
//				((JScrollPane) source.getParent().getParent().getParent().getParent()).setViewportView(this);
//
//			}
		}
//		System.out.println("clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

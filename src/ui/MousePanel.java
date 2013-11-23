package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MousePanel extends VanillaPanel implements MouseListener {
	
	private int originalSize;
	
	/**
	 * Static factory
	 * @param width
	 * @param height
	 * @return
	 */
	public static OuterPanel fillerWithScroll(int width, int height){
		boxWidth=width;
		VanillaPanel inner = new MousePanel();
		OuterPanel outer = inner.new OuterPanel();
		outer.inner = inner;
		inner.addMouseListener((MouseListener) inner);
		JScrollPane scroll = new JScrollPane(inner);
		scroll.setPreferredSize(new Dimension(width,height));
		outer.add(scroll);
		return outer;
	}
	
	@Override
	public void populate(Set<String> drugLines, boolean edible){
		super.populate(drugLines, edible);
		this.originalSize = this.boxList.size();
		for (int i = 0; i<this.boxList.size();i++){
			JTextField jt = (JTextField)this.boxList.get(i).getEditor().getEditorComponent();
			jt.setEnabled(false);
			jt.setDisabledTextColor(Color.BLACK);
			jt.addMouseListener(this);
			System.out.println("populated");
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

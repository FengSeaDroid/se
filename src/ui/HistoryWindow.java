package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

import control.MainControl;

public class HistoryWindow extends JPanel {
	private JTextField textField;
	private JTextField textField_1;

	public HistoryWindow(String physicianName,String issueDate)
	{
		//super(new MigLayout("","[grow]","20[]10[]10[]10"));
		super(new MigLayout());
		HeaderView hv = new HeaderView(false);
		footerView fv = new footerView(false);
		
		JSplitPane upperLeftPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 	hv,fv);
		hv.setPhysicianName(physicianName);
		hv.setPhysicianName(physicianName);
	}
}


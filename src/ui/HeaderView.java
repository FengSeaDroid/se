package ui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MainControl;

import net.miginfocom.swing.MigLayout;

public class HeaderView extends JPanel {
	
	private JLabel physicianName;
	private JLabel issueDate;
	private final int viewHeight = 100;
	public HeaderView(boolean edible)
	{
		super(new MigLayout(" ","[]","[][]"));
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setPreferredSize(new Dimension(getMaximumSize().width,this.viewHeight));
	  //  physicianName = new JLabel();
	  // issueDate = new JLabel();
	   
	    physicianName = new JLabel("New label");
	  // add(physicianName, "cell 0 0");
	   
	   issueDate = new JLabel("New label");
	   //add(issueDate, "cell 0 1");
	   this.add(physicianName,"align left,cell 0 0");
	   this.add(issueDate,"align left,cell 0 1");
	}
	public void setPhysicianName(String physicianName)
	{
		this.physicianName.setText(physicianName);
	}
	public void setIssueDate(String issueDate)
	{
		this.issueDate.setText(issueDate);
	}
}

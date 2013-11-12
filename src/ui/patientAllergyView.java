package ui;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class patientAllergyView extends JPanel {
	public JTable allergyTable;
	//private DefaultTableModel tablemodel;
	//private Map<String,String> patientInfo = new HashMap<String,String>();
	 
	private static String[] columnNames = {"Drug Allergy"};
	
	private static Object[][] data ={{" "}};

	static DefaultTableModel model = new DefaultTableModel(data,columnNames);
	/**
	 * Create the panel.
	 */
	public patientAllergyView() {
		
		allergyTable = new JTable(model);
		allergyTable.setFillsViewportHeight(true);
		allergyTable.setPreferredScrollableViewportSize(allergyTable.getPreferredSize());
		JScrollPane scrollPane = new JScrollPane(allergyTable); 
		Dimension maximumSize = new Dimension(1, 1000);
		scrollPane.setMaximumSize(maximumSize);
	 	this.add(scrollPane);
		
	}
	

}

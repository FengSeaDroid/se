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

public class PatientAllergyView extends JPanel {
	public JTable allergyTable;
	 
	private static String[] columnNames = {"Drug Allergy"};
	
	private static Object[][] data ={{" "}};

	static DefaultTableModel model = new DefaultTableModel(data,columnNames);
	/**
	 * Create the panel.
	 */
	public PatientAllergyView() {
		allergyTable = new JTable(model);
		allergyTable.setFillsViewportHeight(true);
		allergyTable.setEnabled(true);
		JScrollPane scrollPane = new JScrollPane(allergyTable); 
		scrollPane.setPreferredSize(new Dimension(500,250));
	 	this.add(scrollPane);
	}
}

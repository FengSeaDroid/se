package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class patientAllergyView extends JPanel {
	private JTable allergyTable;
	
	private String[] columnNames = {"Allergy"};
	
	private Object[][] data = {{"Pool"},{"Swimming"},{"Beach"}};

	/**
	 * Create the panel.
	 */
	public patientAllergyView() {
		
		allergyTable = new JTable(data, columnNames);

		
		JScrollPane scrollPane = new JScrollPane(allergyTable);
		this.add(scrollPane);

	}

}

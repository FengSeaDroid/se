package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class prescriptionHistoryView extends JPanel {
	
	public JTable HistoryTable;
	private static String[] columnNames = {"Date","Medication"};
	private static Object[][] data ={{" "}};
	static DefaultTableModel model = new DefaultTableModel(data,columnNames);
	
	public prescriptionHistoryView()
	{
		
			HistoryTable = new JTable(model);
			HistoryTable.setFillsViewportHeight(true);
			HistoryTable.setPreferredSize(new Dimension(500,200));
			HistoryTable.getColumnModel().getColumn(1).setPreferredWidth(300);
			HistoryTable.setPreferredScrollableViewportSize(HistoryTable.getPreferredSize());
			JScrollPane scrollPane = new JScrollPane(HistoryTable); 
			Dimension maximumSize = new Dimension(1, 1000);
			scrollPane.setMaximumSize(maximumSize);
		 	this.add(scrollPane);
		 	HistoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
				 @Override
				 public void mouseClicked(java.awt.event.MouseEvent evt) {
					 
					 JFrame HistoryFrame = new JFrame("Prescription History");
					 HistoryFrame .getContentPane().add(new HistoryWindow(), BorderLayout.CENTER);
					 HistoryFrame .pack();
					 HistoryFrame .setVisible(true);
					 HistoryFrame.setSize(new Dimension(500,500));
				    }
				});
			
		
	}

}

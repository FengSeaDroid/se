package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.MainControl;

import domain.Patient;
import domain.Prescription;

public class prescriptionHistoryView extends JPanel {
	
	
	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
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
			HistoryTable.setEnabled(false);
			JScrollPane scrollPane = new JScrollPane(HistoryTable); 
			Dimension maximumSize = new Dimension(1, 1000);
			scrollPane.setMaximumSize(maximumSize);
		 	this.add(scrollPane);
		 	
		 	HistoryTable.addMouseListener(new java.awt.event.MouseAdapter() {
				 @Override
				 public void mouseClicked(MouseEvent e) {
					 if(e.getClickCount()==2){
					 JFrame HistoryFrame = new JFrame("Prescription History");
					 HistoryFrame .getContentPane().add(new HistoryWindow(), BorderLayout.CENTER);
					 HistoryFrame .pack();
					 HistoryFrame .setVisible(true);
					 HistoryFrame.setSize(new Dimension(500,500));
					 
					 JTable target = (JTable)e.getSource();
			          int row = target.getSelectedRow();
			         // final int column = target.getSelectedColumn();
			          //Cast to ur Object type
			          DefaultTableModel urObjctInCell =
			              ( DefaultTableModel)target.getValueAt(row, 0);
			         // prescriptionHistory = MainControl.getMainControl().getPrescription();
			         //Prescription prescription = MainControl.getMainControl().lookUpPrescription(MainControl.getMainControl().getCurrentPatient());
			         
					 }
				    }
				 
				});
		 	
		 	
		
	}

}

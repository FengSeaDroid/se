package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.MainControl;
import domain.Patient;
import domain.Prescription;

public class prescriptionHistoryView extends JPanel {


	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	Set<Prescription> prescriptionRenew = new HashSet<Prescription>();

	public JTable HistoryTable;
	private static String[] columnNames = {"Date","Medication"};
	private static Object[][] data ={{" "}};
	Patient patient;
	String physician;
	static DefaultTableModel model = new DefaultTableModel(data,columnNames) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};


	Prescription clickedPrescription;
	public prescriptionHistoryView()
	{

		HistoryTable = new JTable(model);
		HistoryTable.setFillsViewportHeight(true);
		HistoryTable.setPreferredSize(new Dimension(500,200));
		HistoryTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		HistoryTable.setPreferredScrollableViewportSize(HistoryTable.getPreferredSize());
		//HistoryTable.setEnabled(false);

		JScrollPane scrollPane = new JScrollPane(HistoryTable); 
		Dimension maximumSize = new Dimension(1, 1000);
		scrollPane.setMaximumSize(maximumSize);
		this.add(scrollPane);

		HistoryTable.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			//			public void  mousePressed(MouseEvent e) {
			//				patient = MainControl.getMainControl().getCurrentPatient();
			//				physician = MainControl.getMainControl().getPhysicianName();
			//				if(e.getClickCount()== 2 ){
			//
			//					JTable target = (JTable)e.getSource();
			//					int selectedRow = target.getSelectedRow();
			//					selectedRow =  HistoryTable.convertRowIndexToModel(selectedRow);
			//					String date = (String)HistoryTable.getModel().getValueAt(selectedRow, 0);
			//					// String med =  (String)HistoryTable.getModel().getValueAt(selectedRow, 1);
			//					prescriptionHistory = patient.getPrescriptionHistory();
			//
			//					for(Prescription p: prescriptionHistory)
			//					{
			//						if(p.getIssueDate() == date)
			//						{
			//							prescriptionRenew.add(p);
			//							clickedPrescription = p;
			//						}
			//					}
			//
			//					JFrame HistoryFrame = new JFrame("Prescription History");
			//					// HistoryFrame .getContentPane().add(new HistoryWindow(temp.getPhysician(),temp.getIssueDate()), BorderLayout.CENTER);
			//					HistoryFrame .getContentPane().add(new HistoryWindow(clickedPrescription), BorderLayout.CENTER);
			//					HistoryFrame .pack(); 
			//					HistoryFrame .setVisible(true);
			//					HistoryFrame.setSize(new Dimension(500,500));
			//
			//				}
			//				// int[] columns = HistoryTable.getSelectedColumns();
			//				// int rowcount = model.getRowCount();
			//				ArrayList<String> drugSpec;
			//				//				if(e.getClickCount()== 1){
			//				//
			//				//					JTable target = (JTable)e.getSource();
			//				//					//	 HistoryTable target = (HistoryTable)e.getSource();
			//				//					// int selectedRow = HistoryTable.getSelectedRow();
			//				//					int selectedRow = target.getSelectedRow();
			//				//					selectedRow = HistoryTable.convertRowIndexToModel(selectedRow);
			//				//					String date = (String)HistoryTable.getModel().getValueAt(selectedRow, 0);
			//				//					String med = (String)HistoryTable.getModel().getValueAt(selectedRow, 1);
			//				//					// Patient patient = MainControl.getMainControl().getCurrentPatient(); 
			//				//					drugSpec = PatientSearchView.DrugsInHistory;
			//				//					for(String s:drugSpec)
			//				//					{
			//				//						if(s.contains(med))
			//				//						{
			//				//							DrugLineView.getDrugLineview().renewToDrugLineView(s);
			//				//							System.out.println(s);
			//				//						}
			//				//					}
			//				//				}
			//			}
			public void  mousePressed(MouseEvent e) {
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK){
					System.out.println("Right Click pressed");
					JPopupMenu popupMenu = new JPopupMenu();
					
					JMenuItem renewDrug=new JMenuItem("Renew Drug");
					renewDrug.addMouseListener(new MouseAdapter() {

			                @Override
			                public void mousePressed(MouseEvent e) {
			                    System.out.println("renewDrug");
			                }
			            });
					popupMenu.add(renewDrug);
					
					JMenuItem renewPrescription=new JMenuItem("Renew Prescription");
					renewPrescription.addMouseListener(new MouseAdapter() {

			                @Override
			                public void mousePressed(MouseEvent e) {
			                    System.out.println("Renew Prescription");
			                }
			            });
					popupMenu.add(renewPrescription);
					
					JMenuItem viewPrescription=new JMenuItem("View Prescription");
					viewPrescription.addMouseListener(new MouseAdapter() {

			                @Override
			                public void mousePressed(MouseEvent e) {
			                    System.out.println("viewPrescription");
			                }
			            });
					popupMenu.add(viewPrescription);
					
					
					HistoryTable.setComponentPopupMenu(popupMenu);

					popupMenu.show();
					//repaint();
					//revalidate();
			        //popup.show();


				}
			}
		});

	}

}


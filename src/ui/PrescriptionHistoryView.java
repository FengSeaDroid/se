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

/**
 * @author safwan
 * View all the previous prescription for the selected patient
 * and add popup menu that will be shown whenever the user make
 * right click on the prescription history table with 3 options 
 * 1-renew prescription
 * 2-renew drugline 
 * 3-view prescription
 */
public class PrescriptionHistoryView extends JPanel {


	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	Set<Prescription> prescriptionRenew = new HashSet<Prescription>();

	public JTable HistoryTable;
	private static String[] columnNames = {"Date","Medication"};
	private static Object[][] data ={{" "}};
	private Patient patient;
	private String physician;
	private Prescription clickedPrescription;


	static DefaultTableModel model = new DefaultTableModel(data,columnNames) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	public PrescriptionHistoryView()
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

			/* 
			 * right lick mouse function to show popup menue
			 */
			public void  mousePressed(MouseEvent e) {
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK){

					patient = MainControl.getMainControl().getCurrentPatient();
					physician = MainControl.getMainControl().getPhysicianName();
					System.out.println("Right Click pressed");

					//get the table component where the right click of the mouse been made
					final JTable target = (JTable)e.getSource();
					int selectedRow = target.getSelectedRow();
					final int indexOfSelectedRow = target.rowAtPoint(e.getPoint());
					System.out.println("selected index is"+indexOfSelectedRow);
					target.setRowSelectionInterval(indexOfSelectedRow, indexOfSelectedRow);
					selectedRow = target.getSelectedRow();
					selectedRow =  HistoryTable.convertRowIndexToModel(selectedRow);
					String date = (String)HistoryTable.getModel().getValueAt(selectedRow, 0);
					prescriptionHistory = patient.getPrescriptionHistory();

					for(Prescription p: prescriptionHistory)
					{
						if(p.getIssueDate() == date)
						{
							prescriptionRenew.add(p);
							clickedPrescription = p;
							System.out.println(p.getIssueDate());
						}
					}

					JPopupMenu popupMenu = new JPopupMenu();

					JMenuItem renewDrug=new JMenuItem("Renew Drug");
					renewDrug.addMouseListener(new MouseAdapter() {

						@Override
						public void mousePressed(MouseEvent e) {
							String drugName = (String)HistoryTable.getModel().getValueAt(indexOfSelectedRow, 1);
							for(String drugLine:clickedPrescription.getDrugLines()){
								String[] drug=drugLine.split(" ");
								if(drug[0].equals(drugName))
								{
									DrugLineView.getDrugLineview().renewToDrugLineView(drugLine);
								}
							}


						}
					});
					popupMenu.add(renewDrug);

					JMenuItem renewPrescription=new JMenuItem("Renew Prescription");
					renewPrescription.addMouseListener(new MouseAdapter() {

						@Override
						public void mousePressed(MouseEvent e) {
							System.out.println("Renew Prescription");
							for(String p: clickedPrescription.getDrugLines())
							{
								DrugLineView.getDrugLineview().renewToDrugLineView(p);
								System.out.println("Renew Prescription Drug"+p);
							}
						}
					});
					popupMenu.add(renewPrescription);

					JMenuItem viewPrescription=new JMenuItem("View Prescription");
					viewPrescription.addMouseListener(new MouseAdapter() {

						@Override
						public void mousePressed(MouseEvent e) {
							System.out.println("viewPrescription");

							JFrame HistoryFrame = new JFrame("Prescription History");
							// HistoryFrame .getContentPane().add(new HistoryWindow(temp.getPhysician(),temp.getIssueDate()), BorderLayout.CENTER);
							HistoryFrame .getContentPane().add(new HistoryWindow(clickedPrescription), BorderLayout.CENTER);
							HistoryFrame .pack(); 
							HistoryFrame .setVisible(true);
							HistoryFrame.setSize(new Dimension(500,500));

						}
					});
					popupMenu.add(viewPrescription);


					HistoryTable.setComponentPopupMenu(popupMenu);

					popupMenu.show();
				}
			}
		});

	}

}


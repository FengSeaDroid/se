package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
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
public class PrescriptionHistoryView extends JPanel implements MouseListener {


	public PrescriptionHistoryView()
	{
		super.setLayout(new MigLayout("","0[]0","0[]0"));
		this.setBorder(BorderFactory.createTitledBorder("Patient History Prescription"));
		historyTable = new JTable(model);
		historyTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		historyTable.setAutoCreateRowSorter(true);
	
		JScrollPane scrollPane = new JScrollPane(historyTable); 
		scrollPane.setPreferredSize(new Dimension(MainWindow.d.width/3-80,MainWindow.d.height/2-90));
		this.add(scrollPane);
		this.setPreferredSize(new Dimension(MainWindow.d.width/3,MainWindow.d.height/2+10));
		historyTable.addMouseListener(this);
	}

	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	Set<Prescription> prescriptionRenew = new HashSet<Prescription>();

	private JTable historyTable;
	private static String[] columnNames = {"Date","Medication"};
	private static Object[][] data ={{" "}};
	private Patient patient;
	private Prescription clickedPrescription;


	private DefaultTableModel model = new DefaultTableModel(data,columnNames) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	public DefaultTableModel getModel(){
		return this.model;
	}

	public void clear() {
		getModel().setRowCount(0);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		if (e.getModifiers() == MouseEvent.BUTTON3_MASK){
			patient = MainControl.getMainControl().getCurrentPatient();
			System.out.println("Right Click pressed");

			//get the table component where the right click of the mouse been made
			final JTable target = (JTable)e.getSource();
			int selectedRow = target.getSelectedRow();
			final int indexOfSelectedRow = target.rowAtPoint(e.getPoint());
			System.out.println("selected index is"+indexOfSelectedRow);
			target.setRowSelectionInterval(indexOfSelectedRow, indexOfSelectedRow);
			selectedRow = target.getSelectedRow();
			selectedRow = historyTable.convertRowIndexToModel(selectedRow);
			String date = (String)historyTable.getModel().getValueAt(selectedRow, 0);
			prescriptionHistory = patient.getPrescriptionHistory();

			for(Prescription p: prescriptionHistory)
			{
				if(p.getIssueDate().equals(date))
				{
					prescriptionRenew.add(p);
					clickedPrescription = p;
					System.out.println("Thats the first click prescription"+clickedPrescription.getIssueDate());
				}
			}

			JPopupMenu popupMenu = new JPopupMenu();

			JMenuItem renewDrug=new JMenuItem("Renew Drug");
			popupMenu.add(renewDrug);
			renewDrug.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//some trying of multiple selection
					int[] selection = historyTable.getSelectedRows();
					for (int i=0;i<selection.length;i++){
						int a=selection[i];
						String drugName=(String) historyTable.getModel().getValueAt(a, 1);
						String time=(String) historyTable.getModel().getValueAt(a, 0);
						System.out.println("***"+drugName);
						System.out.println("***"+time);
						
						Iterator<Prescription> iter=prescriptionHistory.iterator();
						while (iter.hasNext()) {
							Prescription pres = (Prescription) iter.next();
							if(pres.getIssueDate().equals(time)){
								for(String drugLine:pres.getDrugLines()){
									String[] drug=drugLine.split(" ");
									if(drug[0].equals(drugName))
									{
										MainWindow.drugLineView.populate(drugLine,true);
									}
								}
							}
						}
					}
				}
			});

			JMenuItem renewPrescription=new JMenuItem("Renew Prescription");
			popupMenu.add(renewPrescription);

			renewPrescription.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					    
					System.out.println("Renew Prescription");
					for(String p: clickedPrescription.getDrugLines())
					{
						MainWindow.drugLineView.populate(p,true);
						System.out.println("Renew Prescription Drug"+p);
					}
				}
			});

			JMenuItem viewPrescription=new JMenuItem("View Prescription");
			viewPrescription.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("viewPrescription");
					JFrame historyFrame = new JFrame("Prescription History");
					// HistoryFrame .getContentPane().add(new HistoryWindow(temp.getPhysician(),temp.getIssueDate()), BorderLayout.CENTER);
					historyFrame.getContentPane().add(new HistoryWindow(clickedPrescription), BorderLayout.CENTER);
					System.out.println("The clicked prescription date is: "+clickedPrescription.getEffectiveDate());
					historyFrame.pack(); 
					historyFrame.setVisible(true);
					historyFrame.setSize(new Dimension(MainWindow.d.width*2/3,MainWindow.d.height*2/3));
					historyFrame.setLocation(MainWindow.d.width*1/7, 30);
				}
			});
			popupMenu.add(viewPrescription);
			historyTable.setComponentPopupMenu(popupMenu);
			popupMenu.setVisible(true);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}


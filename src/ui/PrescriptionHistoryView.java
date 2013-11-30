package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
		historyTable.setAutoCreateRowSorter(false);

		//setrenderer here
		historyTable.setDefaultRenderer(Object.class, new HistoryTableCellRenderer());

		JScrollPane scrollPane = new JScrollPane(historyTable); 
		scrollPane.setPreferredSize(new Dimension(MainWindow.d.width/3-80,MainWindow.d.height/2-90));
		this.add(scrollPane);
		this.setPreferredSize(new Dimension(MainWindow.d.width/3,MainWindow.d.height/2+10));
		historyTable.addMouseListener(this);
	}

	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	Set<Prescription> prescriptionRenew = new HashSet<Prescription>();

	private JTable historyTable;
	
	public JTable getTable(){
		return this.historyTable;
	}
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
			//			System.out.println("Right Click pressed");

			//get the table component where the right click of the mouse been made
			final JTable target = (JTable)e.getSource();
			int selectedRow = target.getSelectedRow();
			final int indexOfSelectedRow = target.rowAtPoint(e.getPoint());
			//			System.out.println("selected index is"+indexOfSelectedRow);
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
					//					System.out.println("Thats the first click prescription"+clickedPrescription.getIssueDate());
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

					int[] selection = historyTable.getSelectedRows();
					for (int i=0;i<selection.length;i++){
						int a=selection[i];

						String time=(String) historyTable.getModel().getValueAt(a, 0);

						Iterator<Prescription> iter=prescriptionHistory.iterator();
						while (iter.hasNext()) {
							Prescription pres = (Prescription) iter.next();
							if(pres.getIssueDate().equals(time)){
								Iterator<String> inneriter=pres.getDrugLines().iterator();
								while(inneriter.hasNext())
									MainWindow.drugLineView.populate(inneriter.next(),true);
							}
						}
					}	
					/*					
//					System.out.println("Renew Prescription");
					for(String p: clickedPrescription.getDrugLines())
					{
						MainWindow.drugLineView.populate(p,true);
//						System.out.println("Renew Prescription Drug"+p);
					}*/
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
					//					System.out.println("The clicked prescription date is: "+clickedPrescription.getEffectiveDate());
					historyFrame.pack(); 
					historyFrame.setVisible(true);
					historyFrame.setResizable(false);
					historyFrame.setSize(new Dimension(MainWindow.d.width*2/3,MainWindow.d.height-50));

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

	/*
	 * inner class
	 */
	@SuppressWarnings("serial")
	public class HistoryTableCellRenderer extends DefaultTableCellRenderer{
		
		private Set<Integer> flagSet;
		
		public void add(Integer i){
			flagSet.add(i);
		}
		
		public HistoryTableCellRenderer(){
			flagSet = new HashSet<Integer>();
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			Color foreground, background;

			if (!flagSet.contains(new Integer(row))) {
				foreground = Color.BLACK;
				background = Color.WHITE;
			} 
			else{
				foreground = Color.WHITE;
				background = Color.decode("#A0522D");
			}
			if (isSelected || hasFocus){
				foreground = Color.WHITE;
				background = Color.decode("#335b8e");
			}
			renderer.setForeground(foreground);
			renderer.setBackground(background);
			return renderer;
		}
	}

}

package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import control.MainControl;
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
@SuppressWarnings("serial")
public class PrescriptionHistoryView extends JPanel implements MouseListener {

	
	private int mouseSideFlag = -1;

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
		//delete useless listeners
		for (MouseMotionListener mml :historyTable.getMouseMotionListeners()){
			historyTable.removeMouseMotionListener(mml);
		}
	}

	Set<Prescription> prescriptionHistory = new HashSet<Prescription>();
	//	Set<Prescription> prescriptionRenew = new HashSet<Prescription>();

	private JTable historyTable;

	public JTable getTable(){
		return this.historyTable;
	}
	private static String[] columnNames = {"Issue Date","Medication"};
	private static Object[][] data ={{" "}};
	//	private Patient patient;
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
		MainWindow.mainFrame.setCursor (Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		System.out.println("lol position is: "+historyTable.columnAtPoint(e.getPoint()));
		//if clicked on the right side
		if (historyTable.columnAtPoint(e.getPoint())==1){
			this.mouseSideFlag = 1;
		}
		//if clicked on the left side
		if (historyTable.columnAtPoint(e.getPoint())==0){
			this.mouseSideFlag = 0;
		}
		
		
		try{
			prescriptionHistory = MainControl.getMainControl().getCurrentPatient().getPrescriptionHistory();
		}
		catch(Exception ex){
			return;
		}
		if (prescriptionHistory == null){
			return;
		}
		if (e.getModifiers() == MouseEvent.BUTTON3_MASK){
			int selectedRow = historyTable.getSelectedRow();
			//			selectedRow = historyTable.convertRowIndexToModel(selectedRow);
			String date = (String)historyTable.getModel().getValueAt(selectedRow, 0);

			for(Prescription p: prescriptionHistory)
			{
				if(p.getIssueDate().equals(date))
				{
					clickedPrescription = p;
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
						//						System.out.println("***"+drugName);
						//						System.out.println("***"+time);

						for(Prescription p:prescriptionHistory){
							if(p.getIssueDate().equals(time)){
								for(String drugLine:p.getDrugLines()){
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
						for(Prescription p:prescriptionHistory){
							if(p.getIssueDate().equals(time)){
								for(String drugLine:p.getDrugLines()){
									MainWindow.drugLineView.populate(drugLine,true);
								}
							}
						}
					}	
				}
			});

			JMenuItem viewPrescription=new JMenuItem("View Prescription");
			viewPrescription.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println("viewPrescription");
					JFrame historyFrame = new JFrame("Prescription History");
					historyFrame.getContentPane().add(new HistoryWindow(clickedPrescription), BorderLayout.CENTER);
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
		MainWindow.mainFrame.setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK && e.getX()<-50){	

			//if clicked on the right side
			if (this.mouseSideFlag==1){
				int[] selection = historyTable.getSelectedRows();
				for (int i=0;i<selection.length;i++){
					int a=selection[i];
					String drugName=(String) historyTable.getModel().getValueAt(a, 1);
					String time=(String) historyTable.getModel().getValueAt(a, 0);
					for(Prescription p:prescriptionHistory){
						if(p.getIssueDate().equals(time)){
							for(String drugLine:p.getDrugLines()){
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

			//if clicked on the left side
			if (this.mouseSideFlag==0){
				int[] selection = historyTable.getSelectedRows();
				for (int i=0;i<selection.length;i++){
					int a=selection[i];
					String time=(String) historyTable.getModel().getValueAt(a, 0);
					for(Prescription p:prescriptionHistory){
						if(p.getIssueDate().equals(time)){
							for(String drugLine:p.getDrugLines()){
								MainWindow.drugLineView.populate(drugLine,true);
							}
						}
					}
				}
			}
			
			this.mouseSideFlag = -1;
		}
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
				background = Color.decode("#909090");
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

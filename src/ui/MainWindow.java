package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;
import control.MainControl;

public class MainWindow extends JPanel {

	/**
	 * size of the screen
	 */
	static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 *  Static jpanels for the views to be shown here.
	 */
	static ClinicInfoView clinicInfo;
	static PatientSearchView patientSearch;
	static NewDrugLineView drugLineView;
	static PatientAllergyView patientAllergy;
	static PrescriptionHistoryView patientPrescriptionHistory;
	
	/**
	 *  Static method for clear the window
	 */
	public static void clear(){
		if (patientSearch != null &&
				drugLineView != null &&
				patientAllergy != null &&
				patientPrescriptionHistory != null){
			patientSearch.clear();
			drugLineView.clear();
			patientAllergy.clear();
			patientPrescriptionHistory.clear();
			MainControl.getMainControl().setCurrentPatient(null);
			MainControl.getMainControl().setPrescription(null);
		}

	}
	 /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame mainFrame = new JFrame("Electronic Prescription System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Add content to the window.
        mainFrame.getContentPane().add(new MainWindow(), BorderLayout.CENTER);
         
        mainFrame.setResizable(false);
//      	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();  
        mainFrame.setPreferredSize(new Dimension(d.width, d.height-30));//<---take 30 off the height 
     
        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
//      mainFrame.setExtendedState(mainFrame.MAXIMIZED_BOTH); 

        
    }

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		super(new GridLayout(1, 1));
        
//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.setBorder(BorderFactory.createLineBorder(Color.black));
////      tabbedPane.setPreferredSize(new Dimension(800, 600));
//        tabbedPane.addTab("New Prescription",this.newPrescription());
//  //    tabbedPane.addTab("Prescription History", this.prescriptionHistory());
//        this.add(tabbedPane);   
//        //The following line enables to use scrolling tabs.
//        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//		this.add(this.newPrescription());
		
		JSplitPane newPrescription = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT ,prescriptionView(),patientInfoView());
		newPrescription.setDividerLocation(d.width*2/3);
		newPrescription.setBorder(BorderFactory.createTitledBorder("New Prescription"));
		newPrescription.setEnabled( false );
		this.add(newPrescription);
		this.revalidate();
        patientSearch.setFocus();
 	}
	
//	protected JComponent newPrescription() {
//		
//		JSplitPane newPrescription = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT ,prescriptionView(),patientInfoView());
//		newPrescription.setDividerLocation(d.width*2/3);
////		newPrescription.setBorder(BorderFactory.createTitledBorder(""));
//		newPrescription.setEnabled( false );
//		return newPrescription;
//	}
	
	private JPanel prescriptionView(){
		JPanel pv = new JPanel();
		pv.setLayout(new MigLayout(""));
		pv.setBorder(BorderFactory.createTitledBorder(""));
		
		clinicInfo = new ClinicInfoView();
		pv.add(clinicInfo,"wrap");
		patientSearch = new PatientSearchView();
		pv.add(patientSearch,"wrap");
		drugLineView = new NewDrugLineView();
		pv.add(drugLineView,"wrap");
		return pv;
	}
	
	private JPanel patientInfoView(){
		JPanel pi = new JPanel();
		pi.setLayout(new MigLayout(""));
		patientAllergy = new PatientAllergyView();
		pi.add(patientAllergy,"wrap");
		patientPrescriptionHistory = new PrescriptionHistoryView();
		pi.add(patientPrescriptionHistory,"wrap");
		return pi;
	}
}

package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.Box;

public class MainWindow extends JPanel {

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
         
        //frame.setResizable(false);
     
        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		super(new GridLayout(1, 1));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("New Prescription",this.newPrescription());
        tabbedPane.addTab("Patient Information", this.patientInformation());
        tabbedPane.addTab("Prescription History", this.newPrescription());
        
        //Add the tabbed pane to this panel.
        this.add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	protected JComponent newPrescription() {
		JPanel newPrescripanel = new JPanel();
		newPrescripanel.setPreferredSize(new Dimension(800, 600));
		newPrescripanel.setLayout(new MigLayout("", "[]", "[]20[]50[][][]"));
		newPrescripanel.add(new PatientSearchPanel(), "cell 0 1,aligny center");	
		newPrescripanel.add(new DrugLinePanel(),"cell 0 2,alignx center");
		return newPrescripanel;
	}
	
	protected JComponent patientInformation(){
		JPanel patientInformation = new JPanel();
		patientInformation.setLayout(new MigLayout("", "[]", "[]20[]50[][][]"));
		patientInformation.add(new PatientSearchPanel(), "cell 0 1,aligny center");	
		//show an uneditable patient view
		patientInformation.add(new patientDetailView(false),"cell 0 2,alignx center");
		return patientInformation;
	}
	
	protected JComponent prescriptionHistory(){
		
		return null;
	}
}

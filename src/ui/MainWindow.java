package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

import java.awt.Component;

import javax.swing.Box;
import java.awt.FlowLayout;

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
        tabbedPane.setBorder(BorderFactory.createLineBorder(Color.black));
        tabbedPane.setPreferredSize(new Dimension(800, 600));
        tabbedPane.addTab("New Prescription",this.newPrescription());
        tabbedPane.addTab("Prescription History", this.prescriptionHistory());
        
        //Add the tabbed pane to this panel.
        this.add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	protected JComponent newPrescription() {
		
		JSplitPane upperLeftPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 	new ClinicInfoView(false),new PatientSearchView());
		
		JSplitPane newPrescripanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,upperLeftPanel, new DrugLineView());
		patientAllergyView patientAllergyView_ = new patientAllergyView();
		FlowLayout flowLayout = (FlowLayout) patientAllergyView_.getLayout();
		JSplitPane rightPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT ,newPrescripanel,patientAllergyView_);
		rightPanel.setDividerLocation(500);
		rightPanel.setSize(500, 500);
		//return newPrescripanel;
		return rightPanel;
	}
	
	protected JComponent prescriptionHistory(){
		JSplitPane patientInformation = new JSplitPane();
		return patientInformation;
	}
}

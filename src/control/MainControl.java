package control;

import java.util.Arrays;
import java.util.HashSet;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import domain.Patient;
import domain.Prescription;
import ui.MainWindow;

/**
 * 
 * A singleton controller
 *
 */
public class MainControl {
	
	/**
	 * The static attribute where the singleton lives
	 */
	private static MainControl singletonMainControl = new MainControl();
		
	/**
	 * The private constructor for this singleton.
	 */
	private MainControl(){
		this.setClinicName("Eastern Clinic");
		this.setClinicTel("749-3322");
		this.setPhysicianName("Gerard Farrell");
		this.setClinicAddress("123 Elithebeth Ave");
//		this.setClinicInfo("Eastern Clinic","749-3322","Gerard Farrell");
	}
	
	/**
	 * When the controller is needed, call this to get it.
	 * 
	 * @return this singleton controller.
	 */
	public static MainControl getMainControl(){
		return singletonMainControl;
	}
	
	private PatientManager patientManager = PatientManager.getPatientManager();
	
	/**
	 * The prescription this process is working on.
	 */
	private Prescription currentPrescription;
	
	public void setPrescription (Prescription prescription){
		this.currentPrescription = prescription;
	}
	
	public Prescription getPrescription(){
		return this.currentPrescription;
	}
	
	/**
	 * The patient who is going to be prescribed.
	 */
	private Patient currentPatient;
	
	/**
	 * @return the currentPatient
	 */
	public Patient getCurrentPatient() {
		return currentPatient;
	}

	/**
	 * @param currentPatient the currentPatient to set
	 */
	public void setCurrentPatient(Patient currentPatient) {
		this.currentPatient = currentPatient;
	}

	private String clinicName;
	
	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	private String clinicTel;
	
	public String getClinicTel() {
		return clinicTel;
	}

	public void setClinicTel(String clinicTel) {
		this.clinicTel = clinicTel;
	}

	private String physicianName;
	
	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	private String clinicAddress;
    
    public void setClinicAddress(String addr){
    	this.clinicAddress = addr;
    }

	public String getClinicAddress() {
		return this.clinicAddress;
	}
	
	public Patient lookupPatient (String MCP){
    	//this.setCurrentPatient(patientManager.lookupPatient(MCP));
    	
		//test patient info
    	String[] allergy = {"I'm","allergy","to","the","cold"};
    	String[] druglines1 = {"This drug tastes good","this one even better", "this one is the best", "nothing beats this one"};
    	String[] druglines2 = {"Sleep","Really","Good","Can fix"};
    	Prescription[] dummyPrescription = new Prescription[2];
    	Patient testPatient = new Patient("judy","12345","89/05/08","50","102 Water St.","709 749-3322",new HashSet<String>(Arrays.asList(allergy)));
    	dummyPrescription[0]=new Prescription(null,"Gerard Farrell","11/15/13","11/15/13",new HashSet<String>(Arrays.asList(druglines1)));
    	dummyPrescription[1]=new Prescription(null,"Gerard Farrell","11/15/13","11/15/13",new HashSet<String>(Arrays.asList(druglines2)));
    	testPatient.addPrescription(dummyPrescription[0]);
    	testPatient.addPrescription(dummyPrescription[1]);
		this.setCurrentPatient(testPatient);
        
		return getCurrentPatient();
	}
	
		/**
		 * Launch the application.
		 */
	    public static void main(String[] args) {
	    	SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                //Turn off metal's use of bold fonts
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
	        MainWindow.createAndShowGUI();
	            }
	        });//create window
	    }
}

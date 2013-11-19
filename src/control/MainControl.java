package control;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import domain.Formulary;
import domain.Patient;
import domain.PatientManager;
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
	
	/**
	 * It will connect to database here
	 */
	private PatientManager patientManager = PatientManager.getPatientManager();
	
	public PatientManager getPatientManager(){
		return this.patientManager;
	}
	
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

	/**
	 * 
	 */
	private final Formulary currentFormulary = this.patientManager.lookupFormulary();
	
	/**
	 * 
	 * @return
	 */
	public Formulary getFormulary(){
		return this.currentFormulary;
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
    	this.setCurrentPatient(patientManager.lookupPatient(MCP));
   		return getCurrentPatient();
	}
	
	/**
	 * 
	 * @param drugLines
	 * @param effectiveDate
	 */
	public void print(Set<String> drugLines, Date effectiveDate){
		
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

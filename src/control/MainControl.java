package control;

import java.util.*;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import domain.Patient;
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
//	
//	/**
//	 * The information of the clinic.
//	 */
//	private Map<String,String> clinicInfo = new HashMap<String,String>();
//	
//	/**
//	 * Setter for the clinic info.
//	 * @param clinicName
//	 * @param clinicTel
//	 * @param physicianName
//	 */
//	public void setClinicInfo(String clinicName,String clinicTel, String physicianName){
//		this.clinicInfo.put("clinicName",clinicName);
//		this.clinicInfo.put("clinicTel", clinicTel);
//		this.clinicInfo.put("physicianName", physicianName);
//	}//setter
//
//	/**
//	 * Getter for the clinic info.
//	 * @return
//	 */
//	public Map<String,String> getClinicInfo(){
//		return new HashMap<String,String>(this.clinicInfo);
//	}//getter
//
//	private static Map<String,String> patientInfo = new HashMap<String,String>();
//	public static void setPatientInfo(String name,String mcp, String dob, String weight,String[] allergy){
//		patientInfo.put("Name",name);
//		patientInfo.put("MCP", mcp);
//		patientInfo.put("DOB", dob);
//		patientInfo.put("Weight", weight);
//		patientInfo.put("Allergy", allergy[0]);
//	}
//	
//	public static Map<String,String> getPatientInfo(){
//		Map<String,String> tempClinicInf = new HashMap<String,String>();
//		tempClinicInf.putAll(patientInfo);
//		return tempClinicInf;
//	}
	


	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
    	//test patient info
    	String[] allerg = new String[1];
    	allerg[0] = "AMOX";
    	//create test patient
    	MainControl.getMainControl().setCurrentPatient(new Patient("judy","12345","89/05/08","50",allerg));
    	//setPatientInfo("judy", "12345", "89/05/08", "50", allerg);
        
    	//create window
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        MainWindow.createAndShowGUI();
            }
        });//create window
    }
}

package ui;

import java.util.*;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.MainWindow;

public class MainControl {
	
	private static Map<String,String> clinicInfo = new HashMap<String,String>();
	private static Map<String,String> patientInfo = new HashMap<String,String>();
	public static void setPatientInfo(String name,String mcp, String dob, String weight,String[] allergy){
		patientInfo.put("Name",name);
		patientInfo.put("MCP", mcp);
		patientInfo.put("DOB", dob);
		patientInfo.put("Weight", weight);
		patientInfo.put("Allergy", allergy[0]);
	}
	
	public static Map<String,String> getPatientInfo(){
		Map<String,String> tempClinicInf = new HashMap<String,String>();
		tempClinicInf.putAll(patientInfo);
		return tempClinicInf;
	}
	
	
	
	public static void setClinicInfo(String clinicName,String clinicTel, String physicianName){
		clinicInfo.put("clinicName",clinicName);
		clinicInfo.put("clinicTel", clinicTel);
		clinicInfo.put("physicianName", physicianName);
	}
	
	public static Map<String,String> getClinicInfo(){
		Map<String,String> tempClinicInf = new HashMap<String,String>();
		tempClinicInf.putAll(clinicInfo);
		return tempClinicInf;
	}
	
	/**
	 * Launch the application.
	 */
    public static void main(String[] args) {
    	String[] allerg = new String[1];
    	allerg[0] = "AMOX";
    	setClinicInfo("Eastern Clinic","749-3322","Gerard Farrel");
    	setPatientInfo("Judy", "12345", "89/05/08", "50", allerg);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        MainWindow.createAndShowGUI();
            }
        });
    }
}

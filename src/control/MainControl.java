package control;

import java.util.*;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ui.MainWindow;

public class MainControl {
	
	private static Map<String,String> clinicInfo = new HashMap<String,String>();
	
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
    	setClinicInfo("Eastern Clinic","749-3322","Safwan Wang");
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        MainWindow.createAndShowGUI();
            }
        });
    }
}

package control;

import domain.Patient;

public class PatientManager {
	
	private static PatientManager patientManagerInstance = new PatientManager();
	
	private PatientManager(){
		
	}
	
	public static PatientManager getPatientManager(){
		return patientManagerInstance;
	}
	
	public Patient lookupPatient(String MCP){
		return null;
	}
}

package control;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import mySqlDatabase.DBConnection;
import domain.Patient;
import domain.Prescription;

public class PatientManager {
	
	private static PatientManager patientManagerInstance = new PatientManager();
	private DBConnection dbconnection;
	private Patient patient;
	private PatientManager() {
		try 
		{
			dbconnection=new DBConnection("jdbc:mysql://mysql.cs.mun.ca:3306/cs6713","cs6713","3176sc!");
		}
		catch ( Exception e ) {
	    System.out.println(e.getMessage());
		}
	}
	
	public static PatientManager getPatientManager(){
		return patientManagerInstance;
	}
	
	public Patient lookupPatient(String MCP){
		try{
			ResultSet patientResult=dbconnection.execQuery("select * from patient where patient_id="+MCP);
			patient.setPatientID((patientResult.getString(1)));
			patient.setMCP(patientResult.getString(2));
			patient.setName(patientResult.getString(3));
			patient.setAddress(patientResult.getString(4));
			patient.setDateOfBirth(patientResult.getString(5));
			patient.setWeight(patientResult.getString(6));
			patient.setTel(patientResult.getString(7));
			
			ResultSet allergyResult=dbconnection.execQuery("select * from allergy where patient_id="+patient.getPatientID());
			Set<String> allergySet = new HashSet<String>();
	        while (allergyResult.next()){
	        	allergySet.add(""+allergyResult.getArray(2));
			}
	        patient.setAllergy(allergySet);
	        
	        ResultSet prescriptionResult=dbconnection.execQuery("select ph.name,pr.prescription_id,pr.issue_date,pr.effective_date from prescription pr,physican ph"
			+"where pr.patient_id="+patient.getPatientID()+" and pr.physicain_id=ph.physicain_id");
	        
	        ResultSet prescriptionSpecResult=dbconnection.execQuery("select medicine_name,medicine_spec from prescription_spec where "
	        		+ "prescription_id="+prescriptionResult.getString(2));
			
			Set<Prescription> prescriptionSet;
			
	        while (prescriptionResult.next()){
	        	Set<String> drugLine=new HashSet<String>();
	        	
	        	while(prescriptionSpecResult.next())
	        	{
	        		drugLine.add(prescriptionSpecResult.getString(1)+prescriptionResult.getString(2)); 
	        	}
        		Prescription prescription=new Prescription(prescriptionResult.getString(1),prescriptionResult.getString(3),prescriptionResult.getString(4),drugLine);

			}
		}
		catch (Exception e){
		    System.out.println(e.getMessage());
		}
		return patient;
	}
}

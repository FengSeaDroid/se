package control;

import java.sql.ResultSet;

import javax.naming.spi.DirStateFactory.Result;

import mySqlDatabase.DBConnection;
import domain.Patient;

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
			ResultSet result=dbconnection.execQuery("select * from patient");
			patient.setPatientID((result.getString(1)));
			patient.setMCP(result.getString(2));
			patient.setName(result.getString(3));
			patient.setAddress(result.getString(4));
			patient.setDateOfBirth(result.getString(5));
			patient.setWeight(result.getString(6));
			patient.setTel(result.getString(7));
			
			result=dbconnection.execQuery("select * from prescription where patient_id="+patient.getPatientID());
			
		}
		catch (Exception e){
		    System.out.println(e.getMessage());
		}
		return patient;
	}
}

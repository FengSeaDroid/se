package domain;
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
			System.out.println("not enter quesry statement");
			System.out.println(MCP);
			ResultSet patientResult=dbconnection.execQuery("select patient_id,MCP,name,address,dob,"
					+ "weight,phone from patient where patient_id=1");
			System.out.println("affter second stat quesry statement");
			
			patientResult.next();
			
			String p_id=patientResult.getString("patient_id");
			System.out.println(p_id);
			//patient_id	MCP	name	address	dob	weight	phone


			String mcp=patientResult.getString("MCP");

			String name=patientResult.getString("name");

			String address=patientResult.getString("address");

			String dateOfBirth=patientResult.getString("dob");

			String weight=patientResult.getString("weight");

			String tel=patientResult.getString("phone");
			
			System.out.println("DDDDDDDdone");

			//patient_id	allergy_agent
			ResultSet allergyResult=dbconnection.execQuery("select * from allergy where patient_id=1");
			System.out.println("DDDDDDDdone11111");

			Set<String> allergySet = new HashSet<String>();
			System.out.println("DDDDDDDdone22222");

	        while (allergyResult.next()){
				System.out.println("inside while");

	        	allergySet.add(""+allergyResult.getString("allergy_agent"));
			}
	       // patient.setAllergy(allergySet);
			System.out.println("DDDDDDDdoneqqq");
			
			//prescription_id	issue_date	effective_date	physician_id	patient_id
	        ResultSet prescriptionResult=dbconnection.execQuery("select ph.name,pr.prescription_id,pr.issue_date,pr.effective_date from prescription pr,physician ph"+
			" where pr.patient_id=1 and pr.physician_id=ph.physician_id and pr.physician_id=1");
	        
	        //prescription_id	medicine_name	medicine_spec
	        ResultSet prescriptionSpecResult=dbconnection.execQuery("select medicine_name,medicine_spec from prescription_spec where "
	        		+ "prescription_id=1");
			
			Set<Prescription> prescriptionSet=new HashSet<Prescription>();
			
	        while (prescriptionResult.next()){
	        	
	        	System.out.println("second while");
	        	Set<String> drugLine=new HashSet<String>();
	        	
	        	while(prescriptionSpecResult.next())
	        	{
		        	System.out.println("third while");

	        		drugLine.add(prescriptionSpecResult.getString("medicine_name")+" "+prescriptionSpecResult.getString("medicine_spec")); 
	        	}
        		Prescription prescription=new Prescription(prescriptionResult.getString(1),prescriptionResult.getString("issue_date")
        				,prescriptionResult.getString("effective_date"),drugLine);
        		System.out.println("done from third while");
        		prescriptionSet.add(prescription);

			}
	        System.out.println("end while");
			patient=new Patient(p_id,name,mcp,dateOfBirth,weight,address,tel,allergySet,prescriptionSet);

		}
		catch (Exception e){
		    System.out.println(e.getMessage());
		}
		return patient;
	}
	
	//medicine	medicine_spec
	public Formulary lookupFormulary(){
		Set<String> formularySet=new HashSet<String>();

		try{

			ResultSet formularyResult=dbconnection.execQuery("select medicine,medicine_spec from formulary");
			
			while(formularyResult.next()){
				formularySet.add(formularyResult.getString("medicine")+" "+formularyResult.getString("medicine_spec"));
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			}
		Formulary formulary=new Formulary(formularySet);
		return formulary;
	}
}

package domain;
import java.awt.Image;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import control.MainControl;
import mySqlDatabase.DBConnection;
import domain.Patient;
import domain.Prescription;

/**
 * A singleton Manager
 *
 */
public class PatientManager {
	
	private static PatientManager patientManagerInstance = new PatientManager();
	private DBConnection dbconnection;
	private Patient patient;
	
	/**
	 * Patient manager constructor
	 */
	private PatientManager() {
		try 
		{
			//Connection to University DB server
			dbconnection=new DBConnection("jdbc:mysql://mysql.cs.mun.ca:3306/cs6713","cs6713","3176sc!");
			//local connection to my DB server
			//dbconnection=new DBConnection("jdbc:mysql://localhost:3306/prescriptionsys","root","System");
		}
		catch ( Exception e ) {
	    System.out.println(e.getMessage());
		}
	}
	
	/**
	 * @return patient manager to be used whenever needed
	 */

	public static PatientManager getPatientManager(){
		return patientManagerInstance;
	}
	
	/**
	 * search patient on the database based on his MCP and return patient object
	 */
	public Patient lookupPatient(String MCP){
		try{
			System.out.println("MCP number is"+MCP);
			ResultSet patientResult=dbconnection.execQuery("select patient_id,MCP,name,address,dob,"
					+ "weight,phone from patient where MCp="+MCP);
			
			patientResult.next();
			
			String p_id=patientResult.getString("patient_id");
			System.out.println("Patient ID"+p_id);
			
			//Patient table description in the DB (patient_id,MCP,name,address,dob,weight,phone)

			String mcp=patientResult.getString("MCP");

			String name=patientResult.getString("name");

			String address=patientResult.getString("address");

			String dateOfBirth=patientResult.getString("dob");

			String weight=patientResult.getString("weight");

			String tel=patientResult.getString("phone");
			
			ResultSet allergyResult=getAlergy(p_id);
			Set<String> allergySet = new HashSet<String>();

	        while (allergyResult.next()){
	        	
	        	allergySet.add(""+allergyResult.getString("allergy_agent"));
			}
	        Set<Prescription> prescriptionSet=new HashSet<Prescription>();
	        prescriptionSet.addAll(this.getPrescriptionHistory(p_id));
			patient=new Patient(p_id,name,mcp,dateOfBirth,weight,address,tel,allergySet,prescriptionSet);

		}
		catch (Exception e){
		    System.out.println(e.getMessage());
		}
		return patient;
	}
	
	/**
	 * Get allergy as ResultSet for particular patient
	 */
	public ResultSet getAlergy(String patientID){
		try{
			ResultSet patientAllergyList=dbconnection.execQuery("select * from allergy where patient_id="+patientID);
			return patientAllergyList;

		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * Get prescription History for specific patient as set of prescription objects
	 */
	public Set<Prescription> getPrescriptionHistory(String patientID){
		try{

			ResultSet prescriptionResult=dbconnection.execQuery("select ph.name,pr.prescription_id,pr.issue_date,pr.effective_date from prescription pr,physician ph"+
					" where pr.patient_id="+patientID+" and pr.physician_id=ph.physician_id");

			Set<Prescription> prescriptionSet=new HashSet<Prescription>();

			while (prescriptionResult.next()){

				Set<String> drugLine=new HashSet<String>();
				String presc_id=prescriptionResult.getString("prescription_id");

				//Prescription_spec table description in the DB
				ResultSet prescriptionSpecResult=dbconnection.execQuery("select medicine_name,medicine_spec from prescription_spec where "
						+ "prescription_id="+presc_id);

				while(prescriptionSpecResult.next())
				{
					drugLine.add(prescriptionSpecResult.getString("medicine_name")+" "+prescriptionSpecResult.getString("medicine_spec")); 
				}
				Prescription prescription=new Prescription(prescriptionResult.getString(1),prescriptionResult.getString("issue_date")
						,prescriptionResult.getString("effective_date"),drugLine,false);

				prescriptionSet.add(prescription);

			}
			return prescriptionSet;
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * query the formulary table in the DB and return formulary object
	 */
	public Formulary lookupFormulary(){
		Set<String> formularySet=new HashSet<String>();

		try{
			//formulary table description in the DB (medicine,medicine_spec)
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
	
	/**
	 * save prescription for specific user and add it to the database
	 */
	public void savePrescription(Prescription prescription){
		try{
			
			dbconnection.manipulateData("insert into prescription (prescription_id,issue_date,effective_date,physician_id,patient_id)"
					+ "VALUES (NULL ,"+prescription.getIssueDate()+","+prescription.getEffectiveDate()+","+MainControl.getMainControl().getPhysicianID()+","
					+MainControl.getMainControl().getCurrentPatient().getPatientID()+");");
			
			ResultSet maxPrescriptionID=dbconnection.execQuery("SELECT max(prescription_id) FROM prescription");
			maxPrescriptionID.next();
			String maxID=maxPrescriptionID.getString(1);
			Set<String> drugs=prescription.getDrugLines();
			
			for (String s : drugs) {
				String[] medicine=s.split(" ");
				dbconnection.manipulateData("insert into prescription_spec(prescription_id,medicine_name,medicine_spec) values ("+maxID+","+medicine[0]+","+medicine[1]+medicine[2]+")");
			}
		
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void getSignature(String physicianID)
	{
		try
		{
			Image image;
			byte[] imageBytes;
			ResultSet signatureResult=dbconnection.execQuery("select signature from physician where physician_id="+physicianID);
			signatureResult.next();
			imageBytes=signatureResult.getBytes("columnLabel");
			//image=getToolkit().creatImage(imageBytes);
			//ImageIcon icon=new Icon(image);
			//JLabel x;
			//x.setIcon(icon);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
}

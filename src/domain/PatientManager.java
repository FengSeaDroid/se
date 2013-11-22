package domain;
import java.awt.Image;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import techService.DBConnection;
import control.MainControl;
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
						,prescriptionResult.getString("effective_date"),drugLine);

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
	protected void savePrescription (Prescription prescription) {
		try{
			
			dbconnection.manipulateData("insert into prescription (prescription_id,issue_date,effective_date,physician_id,patient_id)"
					+ "VALUES (NULL ,'"+prescription.getIssueDate()+"','"+prescription.getEffectiveDate()+"','"+MainControl.getMainControl().getPhysicianID()+"','"
					+MainControl.getMainControl().getCurrentPatient().getPatientID()+"');");
			
			ResultSet maxPrescriptionID=dbconnection.execQuery("SELECT max(prescription_id) FROM prescription");
			maxPrescriptionID.next();
			String maxID=maxPrescriptionID.getString(1);
			Set<String> drugs=prescription.getDrugLines();
			
			for (String s : drugs) {
				String[] medicine=s.split(" ");
				String spec ="";
				if (medicine.length<2){
					throw new IllegalArgumentException("Drug specification not exists.");
				}
				for (int i=1;i<medicine.length;i++){
					spec = spec + medicine[i];
				}
				dbconnection.manipulateData("insert into prescription_spec(prescription_id,medicine_name,medicine_spec) values ('"+maxID+"','"+medicine[0]+"','"+spec+"')");
				
			}
		
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public byte[] getSignature(String physicianID)
	{
		try
		{
			byte[] imageBytes;
			ResultSet signatureResult=dbconnection.execQuery("select signature from physician where physician_id="+physicianID);
			signatureResult.next();
			imageBytes=signatureResult.getBytes("signature");
			//Image image=null;
			//image=getToolkit().creatImage(imageBytes);
			//ImageIcon icon=new ImageIcon(image);
			//JLabel x;
			//x.setIcon(icon);
			return imageBytes;
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * return set of patients the information will be formated such that
	 * each element in this set will contain name,address,MCP
	 */
	public Set<String> getPatientList(){
		try
		{
			//patient table description on the DB (name,address,MCP)
			ResultSet signatureResult=dbconnection.execQuery("select name,address,MCP FROM patient");
			Set<String> patientSet=new HashSet<String>();
			while (signatureResult.next())
			{
//				System.out.println(signatureResult.getString("name")+";"+signatureResult.getString("address")+";"+signatureResult.getString("MCP"));
				patientSet.add(signatureResult.getString("name")+";"+signatureResult.getString("address")+";"+signatureResult.getString("MCP"));
			}
			return patientSet;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;

	}
	
	public DBConnection getDBConnection(){
		return dbconnection;
	}
	
}

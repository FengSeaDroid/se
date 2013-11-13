package domain;

import java.sql.Array;
import java.util.HashSet;
import java.util.Set;

/**
 * Patient class
 * @author 
 * @version
 *
 */
public class Patient {
	
	/**
	 * Constructor
	 * @param name
	 * @param MCP
	 * @param dob
	 * @param weight
	 * @param allergyList
	 */
	public Patient(String name,String MCP, String dob, String weight, String addr, String tel, Set<String> allergyList){
		this.setName(name);
		this.setMCP(MCP);
		this.setDateOfBirth(dob);
		this.setWeight(weight);
		this.setAddress(addr);
		this.setTel(tel);
		this.setAllergy(allergyList);
		//this.prescriptionHistory = new HashSet<Prescription>();
	}
	
	public Patient(String id, String name,String MCP, String dob, String weight, String addr, String tel,  Set<String> allergyList, Set<Prescription> prescriptionHistory){
		this.setPatientID(id);
		this.setName(name);
		this.setMCP(MCP);
		this.setDateOfBirth(dob);
		this.setWeight(weight);
		this.setAddress(addr);
		this.setTel(tel);
		this.setAllergy(allergyList);
		this.prescriptionHistory=prescriptionHistory;
	}
	
	private String patientID;
	
	public String getPatientID(){
		return this.patientID;
	}
	
	public void setPatientID(String id){
		this.patientID = id;
	}

	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name.toString();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	private String MCP;
	
	public String getMCP() {
		return MCP;
	}

	public void setMCP(String mCP) {
		MCP = mCP;
	}

	private String dateOfBirth;
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	private String weight;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	private Set<String> allergy;

	/**
	 * @return the allergy
	 */
	public Set<String> getAllergy() {
		return allergy;
	}

	/**
	 * @param array the allergy to set
	 */
	public void setAllergy(Set<String> set) {
		this.allergy = set;
	}
	
//	private Set<Prescription> prescriptionList = new HashSet<Prescription>();
//
//	/**
//	 * @return the prescriptionList
//	 */
//	public Set<Prescription> getPrescriptionList() {
//		return new HashSet<Prescription>(this.prescriptionList);
//	}
//
//	/**
//	 * @param prescriptionList the prescriptionList to set
//	 */
//	public void setPrescriptionList(Set<Prescription> prescriptionList) {
//		this.prescriptionList = prescriptionList;
//	}
	
	private String tel;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	private Set<Prescription> prescriptionHistory = new HashSet<Prescription>();;
	
	public Set<Prescription> getPrescriptionHistory(){
		return new HashSet<Prescription>(this.prescriptionHistory);
	}
	
	public void addPrescription (Prescription prescription){
		prescriptionHistory.add(prescription);
		//prescription.setOwner(this);
	}
}

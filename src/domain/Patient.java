package domain;

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
	public Patient(String name,String MCP, String dob, String weight, String[] allergyList){
		this.setName(name);
		this.setMCP(MCP);
		this.setDateOfBirth(dob);
		this.setWeight(weight);
		this.setAllergy(allergyList.clone());
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

	private String[] allergy;

	/**
	 * @return the allergy
	 */
	public String[] getAllergy() {
		return allergy.clone();
	}

	/**
	 * @param allergy the allergy to set
	 */
	public void setAllergy(String[] allergy) {
		this.allergy = allergy;
	}
	
	private Set<Prescription> prescriptionList = new HashSet<Prescription>();

	/**
	 * @return the prescriptionList
	 */
	public Set<Prescription> getPrescriptionList() {
		return new HashSet<Prescription>(this.prescriptionList);
	}

	/**
	 * @param prescriptionList the prescriptionList to set
	 */
	public void setPrescriptionList(Set<Prescription> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}
}

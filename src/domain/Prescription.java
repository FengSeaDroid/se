package domain;

import java.util.Set;

public class Prescription {
	
	public Prescription(/*Patient p,*/ String physician, String issueDate, String effectiveDate, Set<String> drugLines){
		//this.setOwner(p);
		this.setPhysician(physician);
		this.setIssueDate(issueDate);
		this.setEffectiveDate(effectiveDate);
		this.setDrugLines(drugLines);
	}
	
	/*private Patient owner;
	
	public Patient getOwner() {
		return owner;
	}

	protected void setOwner(Patient owner) {
		this.owner = owner;
	}*/

	private String physician;
	
	public String getPhysician() {
		return physician;
	}

	public void setPhysician(String physician) {
		this.physician = physician;
	}

	private String issueDate;
	
	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	private String effectiveDate;
	
	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	private Set<String> drugLines;

	public Set<String> getDrugLines() {
		return drugLines;
	}

	public void setDrugLines(Set<String> drugLines) {
		this.drugLines = drugLines;
	}

}

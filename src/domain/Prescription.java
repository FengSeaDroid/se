package domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import control.MainControl;

public class Prescription {
	
	/**
	 * create Prescription object which is in achieved state.
	 * 		Used for read history prescription from database.
	 * @param physician
	 * @param issueDate
	 * @param effectiveDate
	 * @param drugLines
	 * @param activeState
	 */
	public Prescription(/*Patient p,*/ String physician, String issueDate, String effectiveDate, Set<String> drugLines){
		//this.setOwner(p);
		this.physician=physician;
		this.issueDate=issueDate;
		this.effectiveDate=effectiveDate;
		this.setDrugLines(drugLines);
		this.active = false;
	}
	
	/**
	 * Create Prescription object which is in active state.
	 * @param physician
	 */
	public Prescription(String physician){
		this.active = true;
		this.setPhysician(physician);
		this.drugLines = new HashSet<String>();
	}
	
	/*private Patient owner;
	
	public Patient getOwner() {
		return owner;
	}

	protected void setOwner(Patient owner) {
		this.owner = owner;
	}*/
	
	/**
	 * flag for the state of the prescription. when created it is active and when saved it is achieved.
	 */
	private boolean active;
	
	public boolean isActive(){
		return this.active;
	}
	
	private void achieve(){
		this.active = false;
	}

	private String physician;
	
	public String getPhysician() {
		return physician;
	}

	public void setPhysician(String physician) throws IllegalStateException{
		if (this.isActive() == false) throw new IllegalStateException();
		this.physician = physician;
	}

	private String issueDate;
	
	public String getIssueDate() {
		return issueDate;
	}

	private void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	void issue(){
		this.setIssueDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
	}

	private String effectiveDate;
	
	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) throws IllegalStateException{
		if (this.isActive() == false) throw new IllegalStateException();
		this.effectiveDate = effectiveDate;
	}

	private Set<String> drugLines;

	public Set<String> getDrugLines() {
		return new HashSet<String>(drugLines);
	}

	protected void setDrugLines(Set<String> drugLines) {
		this.drugLines = drugLines;
	}
	
	public void addDrugLine (String drugLine) throws IllegalStateException{
		if (this.isActive() == false) throw new IllegalStateException();
		this.drugLines.add(drugLine);
	}
	
	public void save() throws IllegalStateException{
		if (this.isActive() == false) throw new IllegalStateException();
		this.achieve();
		this.issue();
		MainControl.getMainControl().getPatientManager().savePrescription(this);
	}
}
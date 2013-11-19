package domain;

import java.util.*;

public class Formulary {
	
	private Set<String> drugList = new HashSet<String>();

	public Formulary(){
		drugList.add("aspirin");
		drugList.add("penicillin");
		drugList.add("acetaminophen");
	}
	
	public Formulary(Set<String> formularySet){
		drugList=formularySet;
	}
	
	public Set<String> getAllDrugSet(){
		return new HashSet<String>(drugList);
	}
	
	/**
	 * This function tests if the given string matches the name of drugs
	 * 	maintained in this formulary and if yes return the drugs that matched.
	 * 
	 * @param 	query the given drug name to be matched.
	 * @return 	a collection of drugs that match the given drug name.
	 * @pre		the given string should not be null.
	 * 			|query != null
	 */
	public Set<String> match (String query){
		assert query != null;
		Set<String> matchedList = new HashSet<String>();
		for (String drug : this.drugList){
			if (drug.startsWith(query)){
				matchedList.add(drug);
			}
		}
		return matchedList;
	}
}

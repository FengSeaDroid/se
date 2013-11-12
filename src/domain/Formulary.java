package domain;

import java.util.HashSet;
import java.util.Set;

public class Formulary {
	
	private Set<String> drugList = new HashSet<String>();

	public Formulary(){
		drugList.add("aspirin");
		drugList.add("penicillin");
		drugList.add("acetaminophen");
	}
	
	public Set<String> match (String query){
		Set<String> matchedList = new HashSet<String>();
		for (String drug : this.drugList){
			if (drug.startsWith(query)){
				matchedList.add(query);
			}
		}
		return matchedList;
	}
}

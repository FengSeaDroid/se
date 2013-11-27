package domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import control.MainControl;

public class PatientManagerTest {

	private Prescription testP;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		String[] sa = {"drug1 222 mg","drug2 333 mg","drug3 444 mg"};
		testP = new Prescription("physician","5555-55-55","5555-55-55",new HashSet<String>(Arrays.asList(sa)));
		MainControl.getMainControl().setCurrentPatient(MainControl.getMainControl().getPatientManager().lookupPatient("321"));

	}

	@Test
	public void testLookupPatient() {
		assertTrue(PatientManager.getPatientManager().lookupPatient("123") instanceof Patient);
		assertTrue(PatientManager.getPatientManager().lookupPatient("123").getPatientID().equals("2"));
	}
	
	@Test
	public void testLookupPatientList(){
		assertEquals(PatientManager.getPatientManager().getPatientList().size(),3);
	}
	
	@Test
	public void testSavePrescription(){
		PatientManager.getPatientManager().savePrescription(testP,"sss");
	}

}

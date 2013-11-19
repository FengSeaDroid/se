package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import control.MainControl;

public class PrescriptionTest {

	private static Prescription testPrescription;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testPrescription = new Prescription("Annastasya");
		MainControl.getMainControl().setCurrentPatient(MainControl.getMainControl().getPatientManager().lookupPatient("321"));
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testConstructor() {
		Prescription constructorPre = new Prescription("New physician");
		assertTrue(constructorPre.isActive());
		assertEquals(constructorPre.getDrugLines().size(),0);
	}
	
	@Test
	public void testAdd(){
		assertEquals(testPrescription.getDrugLines().size(),0);
		assertTrue(testPrescription.getPhysician().equals("Annastasya"));
		testPrescription.addDrugLine("Viagra 1000 mg");
		testPrescription.addDrugLine("Aspirin 2000 mg");
		assertEquals(testPrescription.getDrugLines().size(),2);
	}
	
	@Test
	public void testEffectiveDate(){
		testPrescription.setEffectiveDate("2014-05-31");
		assertTrue(testPrescription.getEffectiveDate().equals("2014-05-31"));
	}
	
	@Test
	public void testIssue(){
		testPrescription.issue();
		assertTrue(testPrescription.getIssueDate().equals("2013-11-19"));
	}
	
	@Test 
	public void testSave(){
		assertEquals(testPrescription.getDrugLines().size(),2);
		testPrescription.setEffectiveDate("2014-05-31");
		assertTrue(testPrescription.getEffectiveDate().equals("2014-05-31"));
		testPrescription.save();
		assertFalse(testPrescription.isActive());
	}

}

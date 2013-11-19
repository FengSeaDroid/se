package domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	
	/*
	 * test if I can save an already achieved prescription.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testSaveFailCase(){
		Prescription testP = new Prescription("physician","1999-9-9","1999-9-9",new HashSet<String>(Arrays.asList("Viagra 312 mg","testDrug 12 mg")),false);
		assertFalse(testP.isActive());
		testP.save();
	}

}

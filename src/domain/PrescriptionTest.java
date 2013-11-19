package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import control.MainControl;

public class PrescriptionTest {

	private Prescription testPrescription;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testPrescription = new Prescription("Annastasya");
		MainControl.getMainControl().setCurrentPatient(MainControl.getMainControl().getPatientManager().lookupPatient("123"));
	}

	@Test
	public void testConstructor() {
		Prescription constructorPre = new Prescription("New physician");
		assertTrue(constructorPre.isActive());
		assertEquals(constructorPre.getDrugLines().size(),0);
	}
	
	@Test
	//public void 

}

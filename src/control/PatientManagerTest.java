package control;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Patient;

public class PatientManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(PatientManager.getPatientManager().lookupPatient("123") instanceof Patient);
		assertEquals(PatientManager.getPatientManager().lookupPatient("123").getPatientID(),2);
	}

}

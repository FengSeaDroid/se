package domain;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PatientManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLookupPatient() {
		assertTrue(PatientManager.getPatientManager().lookupPatient("123") instanceof Patient);
		assertTrue(PatientManager.getPatientManager().lookupPatient("123").getPatientID().equals("2"));
	}
	
	@Test
	public void testLookupPatientList(){
		assertEquals(PatientManager.getPatientManager().getPatientList().size(),2);
	}

}

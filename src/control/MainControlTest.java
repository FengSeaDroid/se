package control;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import domain.Patient;
import domain.Prescription;

public class MainControlTest {

	@Before
	public void setUp() throws Exception {
		MainControl.getMainControl().lookupPatient("12345");
	}

	@Test
	public void test() {
		assertTrue(MainControl.getMainControl().getCurrentPatient() instanceof Patient);
		assertEquals(MainControl.getMainControl().getCurrentPatient().getPrescriptionHistory().size(),2);
		assertEquals(MainControl.getMainControl().getCurrentPatient().getPrescriptionHistory().iterator().next().getDrugLines().size(),4);
	}
}

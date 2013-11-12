package domain;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FormularyTest {
	private Formulary testFormulary;
	private Set<String> results;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testFormulary = new Formulary();
	}

	@Test
	public void testMatchTrue() {
		results = testFormulary.match("a");
		assertTrue (results.contains("aspirin"));
		assertTrue (results.contains("acetaminophen"));
		assertEquals( results.size(),2);
	}

}

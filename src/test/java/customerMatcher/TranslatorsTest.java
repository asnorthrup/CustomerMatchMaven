package customerMatcher;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import com.CarolinaCAT.busIntel.matching.Translators;

public class TranslatorsTest {

	String t1;
	String t2;
	String t3;
	Translators t;
	
	String te1;
	String te2;
	String te3;
	
	@Before
	public void setUp() throws Exception {
		t1 = "John D/B/A John Trucking";
		t2 = "John DBA John Trucking";
		t3 = "John D.B.A. John Trucking";
		t = new Translators();
		te1 = "John - CASH SALE";
		te2 = "John --- C ASH SALE";
		te3 = "JOHN   - CASH SA LE";
	}
	
	@Test
	public void testDBA(){
		String test = t.stripBeginning(t1);
		assertEquals(test, "JOHN TRUCKING");
		test = t.stripBeginning(t2);
		assertEquals(test, "JOHN TRUCKING");
		test = t.stripBeginning(t3);
		assertEquals(test, "JOHN TRUCKING");
	}
	
	@Test
	public void testCashSale(){
		String test = t.stripEndings(te1);
		assertEquals(test, "JOHN");
		test = t.stripBeginning(te2);
		assertEquals(test, "JOHN");
		test = t.stripBeginning(te3);
		assertEquals(test, "JOHN");
	}
	
}

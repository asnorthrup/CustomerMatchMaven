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
	
	String n1;
	String n2;
	String n3;
	
	String a1;
	String a2;
	String a3;
	
	@Before
	public void setUp() throws Exception {
		t1 = "John D/B/A John Trucking";
		t2 = "John DBA John Trucking";
		t3 = "John D.B.A. John Trucking";
		
		te1 = "John - CASH SALE";
		te2 = "John --- C ASH SALE";
		te3 = "JOHN   - CASH SA LE";
		
		t = new Translators();
		n1 = "John's MTN Home";
		n2 = "CLT MTN House";
		n3 = "MTN HOUSE CORP GRDNG CO";
		
		a1 = "PO Box 144";
		a2 = "Post OFfice BoX 144";
		a3 = "PO. BoX 144";
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
		test = t.stripEndings(te2);
		assertEquals(test, "JOHN");
		test = t.stripEndings(te3);
		assertEquals(test, "JOHN");
	}
	
	@Test
	public void testnameTrans(){
		String test = t.customerNameTranslations(n1);
		assertEquals(test, "JOHN'S MOUNTAIN HOME");
		test = t.customerNameTranslations(n2);
		assertEquals(test, "CHARLOTTE MOUNTAIN HOUSE");
		test = t.customerNameTranslations(n3);
		assertEquals(test, "MOUNTAIN HOUSE CORPORATION GRADING COMPANY");
	}
	
	@Test
	public void testPOBoxTrans(){
		String test = t.modPOBox(a1);
		assertEquals(test, "P.O. BOX 144");
		test = t.modPOBox(a2);
		assertEquals(test, "P.O. BOX 144");
		test = t.modPOBox(a3);
		assertEquals(test, "P.O. BOX 144");
	}
	
}

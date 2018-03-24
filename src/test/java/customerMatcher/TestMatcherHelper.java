package customerMatcher;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.CustomerObj;
import com.CarolinaCAT.busIntel.matching.MatcherHelpers;

public class TestMatcherHelper {
	CustomerObj co;
	CustomerObj co1;
	CustomerObj co2;
	CustomerObj co3;
	CustomerObj co4;
	CustomerObj co5;
	
	@Before
	public void setUp() throws Exception {
		co = new CustomerObj(null,"John John", "DBA JJ's Trucking", null, "1011 Bane Street", null, "12345");
		co1 = new CustomerObj(null,"J's Trucking", null ,null, "101 Main Street", null, "12345");
		co2 = new CustomerObj(null,"AJ Dump Truck", null, null,"PO Box 101", null, "21345");		
		co3 = new CustomerObj(null,"JJ D ump T ruc Service", null, null, "PO Box 101", null, "12345");
		co4 = new CustomerObj(null,"JA Truck Shop", null, null, "1111 Trade Street", null, "12345");	
		co5 = new CustomerObj(null,"Truck Stop Inc.", null, null, "101 Main Street", null, "22345");

	}

	@Test
	public void test() {
		int addrScore = MatcherHelpers.getAddressScore(co.address, co.zipCode, co1.address, co1.zipCode);
		System.out.println(addrScore);
		int nameScore = MatcherHelpers.getNameScore(co.name, co1.name);
		System.out.println(nameScore);
		int aggScore = MatcherHelpers.aggrScore(addrScore, nameScore);
		System.out.println(aggScore);
		fail("Not yet implemented");
	}
	@Test
	public void testPOBoxes(){
		int addrScore = MatcherHelpers.getAddressScore(co2.address, co2.zipCode, co3.address, co3.zipCode);
		System.out.println(addrScore);
		addrScore = MatcherHelpers.getAddressScore(co1.address, co1.zipCode, co5.address, co5.zipCode);
		System.out.println("test" + addrScore);
		//addrScore = MatcherHelpers.getAddressScore(co.address, co.zipCode, co1.address, co1.zipCode);
		//System.out.println(addrScore);
	}

}

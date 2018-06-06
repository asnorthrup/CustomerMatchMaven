package customerMatcher;

import static org.junit.Assert.*;

import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.CustomerObj;

public class CustomerObjTest {

	@Test
	public void testCreateCustomer() {
		CustomerObj co = new CustomerObj(null,"John John", "DBA JJ's Trucking", null, "101 Main Street", "704-444-4483", "12345", null, null);
		assertEquals(co.name, "JJ'S TRUCKING");
		assertEquals(co.billAddress, "101 Main Street");
		assertEquals(co.billZipCode, "12345");
		assertEquals(co.phone, "7044444483");
	}
	
	@Test
	public void testPOBoxAddr() {
		CustomerObj co = new CustomerObj(null,"John John", "DBA JJ's Trucking", null, "PO Box 1111", "704-444-4483", "12345", null, null);
		assertEquals(co.name, "JJ'S TRUCKING");
		assertEquals(co.billAddress, "P.O. Box 1111");
		assertEquals(co.billZipCode, "12345");
		assertEquals(co.phone, "7044444483");
	}
	
	@Test
	public void testPhoneZipError() {
		CustomerObj co = new CustomerObj(null,"John John", "Albert", null, "101 Main Street", "704-44483", "12345-3233", null, null);
		assertEquals(co.billZipCode, "12345");
		assertEquals(co.phone, "N/A");
	}
	@Test
	public void testCashZipError() {
		CustomerObj co = new CustomerObj(null,"John John", "Albert Cash SALE", null, "101 Main Street", "704-44483", "00000-233", null, null);
		assertEquals(co.billZipCode, null);
		assertEquals(co.phone, "N/A");
		assertEquals(co.name, "JOHN JOHN ALBERT");
	}
	@Test
	public void testCustomerNum() {
		CustomerObj co = new CustomerObj("$23445","John John", "Albert Cash SALE", "03445", "101 Main Street", "704-44483", "00000-233", null, null);
		assertEquals(co.cuno, "$23445");
		assertEquals(co.parent, "03445");
	}
	@Test
	public void testSetMatchScore() {
		CustomerObj co = new CustomerObj("$23445","John John", "Albert Cash SALE", "03445", "101 Main Street", "704-44483", "00000-233", null, null);
		//deprecated method
		//co.setMatchScore(98);
		//assertEquals(co.matchScore, 98.0,.001);
	}
	//TODO test Post Office Box addresses

}

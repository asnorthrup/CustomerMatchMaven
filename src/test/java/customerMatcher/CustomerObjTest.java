package customerMatcher;

import static org.junit.Assert.*;

import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.CustomerObj;

public class CustomerObjTest {

	@Test
	public void testCreateCustomer() {
		CustomerObj co = new CustomerObj(null,"John John", "DBA JJ's Trucking", null, "101 Main Street", "704-444-4483", "99999");
		assertEquals(co.name, "JJ's Trucking");
		assertEquals(co.address, "101 Main Street");
		assertEquals(co.address, "101 Main Street");
		assertEquals(co.zipCode, null);
		assertEquals(co.phone, "7044444483");
	}

}

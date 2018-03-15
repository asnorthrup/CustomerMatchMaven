package customerMatcher;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.CustomerObj;

public class ExcelCustomerObjTest {

	@Before
	public void setUp() throws Exception {
		CustomerObj co = new CustomerObj(null,"John John", "DBA JJ's Trucking", null, "101 Main Street", "704-444-4483", "12345");
	}

	@Test
	public void test() {
		
		fail("Not yet implemented");
	}

}

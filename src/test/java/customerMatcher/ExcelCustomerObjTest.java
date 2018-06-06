package customerMatcher;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.CustomerObj;
import com.CarolinaCAT.busIntel.matching.PotentialMatch;
import com.CarolinaCAT.busIntel.matching.excelCustomerObj;
import com.CarolinaCAT.busIntel.matching.matchSorting;

public class ExcelCustomerObjTest {
	CustomerObj co;
	CustomerObj co1;
	CustomerObj co2;
	@Before
	public void setUp() throws Exception {
		co = new CustomerObj("M23456","John John", "DBA JJ's Trucking", null, "101 Main Street", "704-444-4483", "12345", null, null);
		
		//co.setMatchScore(80.1);
		co1 = new CustomerObj("C54321","Alan's Shop", null, "54321", "1111 Trade Street", "704-555-5555", "12345", null, null);
		//co1.setMatchScore(99.1);
		co2 = new CustomerObj("M23456","John Allan D/B/A New Era Farms", null, null, "101 Main Street", "704-444-1000", "12345", null, null);
		//co2.setMatchScore(85.1);
	}

	@Test
	public void testExcelCustomerObj() {
		excelCustomerObj eco = new excelCustomerObj(null, "Joe's Grading",null,null,"1801 Leaf St.","12345","802-555-5555",null, null, 12);
		assertEquals(eco.physAddress,"1801 Leaf St.");
		assertEquals(eco.name,"JOE'S GRADING");
		assertEquals(eco.phone,"8025555555");
		/*
		eco.addPotentialDBSCustomer(co);
		eco.addPotentialDBSCustomer(co1);
		eco.addPotentialDBSCustomer(co2);
		Collections.sort(eco.potenDBSMatches,new matchSorting());
		assertEquals(eco.potenDBSMatches.get(0).name,"ALAN'S SHOP");
		assertEquals(eco.potenDBSMatches.get(1).name,"NEW ERA FARMS");
		assertEquals(eco.potenDBSMatches.get(2).name,"JJ'S TRUCKING");
		*/
	}

}

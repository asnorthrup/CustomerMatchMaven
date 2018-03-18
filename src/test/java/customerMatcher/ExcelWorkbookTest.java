package customerMatcher;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.ExcelWorkbook;

public class ExcelWorkbookTest {
	String path;
	@Before
	public void setUp() throws Exception {
		path = "C:\\Users\\anorthrup\\Documents\\Product List 20180318_testfile.xlsx";
	}

	@Test
	public void test() {
		ExcelWorkbook ewo = new ExcelWorkbook(path);
		assertEquals(ewo.customersInWB.size(), 10);	
		assertEquals(ewo.customersInWB.get(0).name,"DEERE-HITACHI CONSTR MACHRY");
		assertEquals(ewo.customersInWB.get(0).address,"P.O. Box 1187");
		assertEquals(ewo.customersInWB.get(3).name,"DUKE ENERGY");
	}

}

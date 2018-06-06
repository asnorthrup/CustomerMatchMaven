package customerMatcher;

import static org.junit.Assert.*;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.CustomerObj;
import com.CarolinaCAT.busIntel.matching.ExcelWorkbook;
import com.CarolinaCAT.busIntel.matching.excelCustomerObj;

public class ExcelWorkbookTest {
	String path;
	ExcelWorkbook ewo;
	@Before
	public void setUp() throws Exception {
		path = "C:\\Users\\anorthrup\\Documents\\Product List 20180318_testfile.xlsx";
		ewo = new ExcelWorkbook(path, null, null, path, null);
	}

	@Test
	public void testReadingInWorkbook() {
		//assertEquals(ewo.customersInWB.size(), 10);	
		assertEquals(ewo.customersInWB.get(0).name,"DEERE-HITACHI CONSTR MACHRY");
		assertEquals(ewo.customersInWB.get(0).billAddress,"P.O. Box 1187");
		assertEquals(ewo.customersInWB.get(3).name,"DUKE ENERGY");
		//assertEquals(ewo.customersInWB.get(9).name,"HUNTER CONSTRUCTION GROUP");
	}
	
	@Test
	public void testWriteOutWorkbook() {
		/*
		CustomerObj co = new CustomerObj("C12897","John John", "Albert Cash SALE", "P03445", "101 Main Street", "704-444-1283", "12777-233");
		co.setMatchScore(91);
		CustomerObj co1 = new CustomerObj("$23445","John John", "Albert Cash SALE", "P903445", "101 Main Street", "704-445-5583", "12345-233");
		co1.setMatchScore(94);
		CustomerObj co2 = new CustomerObj("M5000","Duke Energy", " Cash SALE", "P012335", "PO BOX 50", "704-574-4483", "97865-233");
		co2.setMatchScore(65);
		for (excelCustomerObj eco: ewo.customersInWB){
			eco.addPotentialDBSCustomer(co);
			eco.addPotentialDBSCustomer(co1);
			eco.addPotentialDBSCustomer(co2);
		}

		ewo.addSheetOfMatches("C:\\Users\\anorthrup\\Documents\\Product List 20180318_testfileOutput3.xlsx"); */
	}
		
}

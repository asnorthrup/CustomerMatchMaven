package customerMatcher;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.AccessProspectsQuery;


public class ProspectQueryTest {
	
	AccessProspectsQuery apq = null;
	private File accessDBFile;
    @Before
    public void runBeforeTestMethod() {
    	//String qry, String dbPath
    	String qry = "";
    	String dbPath = "";
    	//Gui g = new Gui();
    	//dbPath = g.getAbsPath();
    	accessDBFile= new File("P:\\Business Intelligence\\SalesLinkCustomers.accdb");
    	qry = "SELECT SaleslinkCustomers.* FROM SaleslinkCustomers WHERE (((SaleslinkCustomers.CustomerNo) Like '$%'))";
    	dbPath = accessDBFile.getAbsolutePath();
    	System.out.println(dbPath);
    	apq = new AccessProspectsQuery(qry, dbPath);
    }
	
	
	@Test
	public void testResultsExist() {
		
		String col1 = null;
		try {
			col1 = apq.getResultSetMetaData().getColumnName(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(col1,"CustomerNo");
	}
	
	
	@Test
	public void testHasProspects() {
		//should have 9580 rows
		ResultSet rs = apq.getResultSet();
		try {
			int count = 0;
			while(rs.next()){
				count++;
			}
			assertEquals(Integer.toString(9580), Integer.toString(count));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

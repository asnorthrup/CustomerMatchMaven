package customerMatcher;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.AccessProspectsQuery;


public class ProspectQueryTest {
	
	AccessProspectsQuery apq = null;
	
	
    @Before
    public void runBeforeTestMethod() {
    	apq = new AccessProspectsQuery();
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

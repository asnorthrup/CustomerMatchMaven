package customerMatcher;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.CarolinaCAT.busIntel.matching.SqlServCTEReportingQuery;

public class SQLserverQueryTest {
	
	SqlServCTEReportingQuery sq = null;
	
	
    @Test
	public void testResultsExist() {
    	String statement = "SELECT TOP (100) [customer_num] FROM [CTEReporting].[dbo].[Customers]; ";
    	sq = new SqlServCTEReportingQuery(statement);
    	
    	try {
			assertEquals(sq.getResultSetMetaData().getColumnCount(),1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

package com.CarolinaCAT.busIntel.matching;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBSquery {
	//variable for the connection called "con"
	private Connection con;
	private ResultSet result;
	private PreparedStatement dbsCustomers;
	private ResultSetMetaData rsmd;
	/**
	 * Creates a DBS query
	 * @param queryCode string for a query to be run
	 * @param ODBCconn string for the name of the odbc connection
	 * @throws ClassNotFoundException class not found exception
	 * @throws SQLException sql exception
	 */
	public DBSquery(String queryCode, String ODBCconn) throws ClassNotFoundException, SQLException{
		//access driver from JAR file
		try {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ClassNotFoundException("No driver installed for AS400 JDBC");
		}

		try {
			con = DriverManager.getConnection("jdbc:as400:"+ODBCconn+";prompt=true");
			dbsCustomers = con.prepareStatement(queryCode);
			result = dbsCustomers.executeQuery();
			rsmd = result.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new SQLException("DBS SQL statement failed, verify schema and table prefixes");
		}
	}
	
	public ResultSet getResultSet(){
		return result;	
	}
	
	public ResultSetMetaData getResultSetMetaData(){
		return rsmd;
	}
	
	
}

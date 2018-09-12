package com.CarolinaCAT.busIntel.matching;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class AccessProspectsQuery {
	//variable for the connection called "con"
	private Connection con;
	private Statement s;
	private ResultSet result;
	//private PreparedStatement getProspects;
	private ResultSetMetaData rsmd;
	
	/**
	 * Query for MS Access data source
	 * @param qry string for query
	 * @param dbPath path the find a MS access database
	 * @throws ClassNotFoundException class exception for not finding access database driver
	 * @throws SQLException exception in SQL statement during execution
	 */
	public AccessProspectsQuery(String qry, String dbPath) throws ClassNotFoundException, SQLException{
		String dbLoc = null;
		if (dbPath == null){
			dbLoc = "jdbc:ucanaccess://P:/Business Intelligence/SalesLinkCustomers.accdb";
		} else {
			//string is read in as: P:\Business Intelligence\SalesLinkCustomers.accdb, must use absolute path
			String replaceDbPath = dbPath.replace('\\', '/');
			//dbLoc = "jdbc:ucanaccess://" + dbLoc;
			dbLoc = "jdbc:ucanaccess://" + replaceDbPath;
		}
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e1) {
			throw new ClassNotFoundException("Error with MS access drivers");
		} /* often not required for Java 6 and later (JDBC 4.x) */
		try {
			con=DriverManager.getConnection(dbLoc);
			s= con.createStatement();
			//getProspects = con.prepareStatement();
			//result = s.executeQuery("SELECT SaleslinkCustomers.* FROM SaleslinkCustomers WHERE (((SaleslinkCustomers.CustomerNo) Like '$%'))");
			result = s.executeQuery(qry);
			rsmd = result.getMetaData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			throw new SQLException("Failed MS Access SQL query. Check inputs and format/name of MS Access data table");
		} 
	}
	
	public ResultSet getResultSet(){
		return result;	
	}
	
	public ResultSetMetaData getResultSetMetaData(){
		return rsmd;
	}
	
	
}

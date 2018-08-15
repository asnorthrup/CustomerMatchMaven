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
	
	public AccessProspectsQuery(String qry, String dbPath){
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
			System.out.println("Error in MS Access SQL statement or file dataase construction");
			e1.printStackTrace();
		} 
	}
	
	public ResultSet getResultSet(){
		return result;	
	}
	
	public ResultSetMetaData getResultSetMetaData(){
		return rsmd;
	}
	
	
}

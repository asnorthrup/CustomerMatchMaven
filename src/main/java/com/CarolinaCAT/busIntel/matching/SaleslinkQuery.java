package com.CarolinaCAT.busIntel.matching;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SaleslinkQuery {
	//variable for the connection called "con"
	private Connection con;
	private ResultSet result;
	private PreparedStatement getProspects;
	private ResultSetMetaData rsmd;
	
	public SaleslinkQuery(String queryCode){
		//access driver from JAR file
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection("jdbc:sqlserver://D09SLPRDDB.acnms.com;integratedSecurity=true"); 
			getProspects = con.prepareStatement(queryCode);
			result = getProspects.executeQuery();
			rsmd = result.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(){
		return result;	
	}
	
	public ResultSetMetaData getResultSetMetaData(){
		return rsmd;
	}
	
	
}

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
	
	public DBSquery(String queryCode){
		//access driver from JAR file
		try {
			Class.forName("com.ibm.as400.access.AS400JDBCDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection("jdbc:as400:DBSPROD;prompt=true");
			dbsCustomers = con.prepareStatement(queryCode);
			result = dbsCustomers.executeQuery();
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

package com.CarolinaCAT.busIntel.matching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlServCTEReportingQuery {
	//variable for the connection called "con"
	private Connection con;
	private ResultSet result;
	private PreparedStatement customers;
	private ResultSetMetaData rsmd;
	
	public SqlServCTEReportingQuery(String queryCode){
        String connectionUrl = "jdbc:sqlserver://CTESQL03:1433;databaseName=CTEReporting;Integratedsecurity=true";
       
        
        try  {
        	con = DriverManager.getConnection(connectionUrl);
            customers = con.prepareStatement(queryCode);
            result = customers.executeQuery();
            rsmd = result.getMetaData();
        } catch (SQLException e) {
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

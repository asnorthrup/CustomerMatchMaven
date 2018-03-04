package com.CarolinaCAT.busIntel.matching;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import me.xdrop.fuzzywuzzy.FuzzySearch;




public class Main {
	/**Min Addr Score in fuzzy matcher */
	public static final int MIN_ADDR_SCORE = 90;
	public static final int MIN_CUSTNAME_SCORE = 90;
	//creates a composite match score to assign to a potential match
	private static double aggrScore( double phoneScore, double addrScore, double custNmScore ){
		double score = 0;
		//matching phone means definite match
		if (phoneScore == 100){
			return 100;
		} else {
			score = ( addrScore > custNmScore ) ? addrScore : custNmScore;
		} 
		return score;
	}
	
	public static void main(String[] args) throws Exception
	{

		String queryCode = "SELECT CUNO, CUNM, CUNM2, PRCUNO, CUADD2, PHNO, ZIPCD9 "
				+ "FROM D09IL01.LIBD09.CIPNAME0 "
				+ "WHERE CUNO NOT LIKE ('I%')";
		
		DBSquery customersQuery = new DBSquery(queryCode);
		
		
		/*Execute query and store customers in an array list called customerList*/
		//ResultSetMetaData stores properties of a ResultSet object, including column count
		 
		int columnCount = customersQuery.getResultSetMetaData().getColumnCount();
		ArrayList<CustomerObj> dbsCustomerList = new ArrayList<CustomerObj>(columnCount); 
		long approxDBSCustomers = 60000;
		long numReadIn = 1;
		ResultSet result = customersQuery.getResultSet(); 
		while(result.next()){
			//create the customer object
			//TODO add ZIP to query
			//TODO add physical address
			CustomerObj co = new CustomerObj(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
			//add to arraylist of customer objects
			dbsCustomerList.add(co);
			//give an indicator to user as to how far along in reading goes
			if((numReadIn % 1000) == 0){
				double pct = ((double)numReadIn / approxDBSCustomers) * 100;
				System.out.println("Approx " + pct + "% DBS loaded") ;
			}
			numReadIn++;
		}
		System.out.println("100% DBS loaded!") ;
		/*Have all DBS customers read into an array called customerList, now need to get excel file path and populate that*/
		//need to go through the potential customers
		//remember must escape sequence back slash with a backslash
		ExcelWorkbook wbOfCusts = new ExcelWorkbook("C:\\Users\\anorthrup\\Documents\\Product List 20171218.xlsx");
		for ( excelCustomerObj c : wbOfCusts.customersInWB){
			//c is a potential customer, check for match in customerList
			for (CustomerObj dbsCust : dbsCustomerList){
				//check the 'c' customer against dbs customers to find matches
				boolean potentialMatch = false;
				//Scores are for each DBS customer
				//Future enhancement could include a way to narrow out DBS customers, but doesn't really seem likely
				double phoneScore = 0;
				double addrScore = 0;
				double custNmScore = 0;
				//need to short circuit if already true -- actually don't do this, you'll use match score to figure out
				if (dbsCust.phone != null && c.phone != null){
					if ( c.phone.equals( dbsCust.phone) ) { 
						potentialMatch = true; //probably unecessary, but just do for now
						phoneScore = 100;
					}
				}
				if (dbsCust.address != null && c.address != null){
					//TODO match score on address should be very high
					if (c.address.toLowerCase().contains(" box ")){
						//if its a PO BOX, then need to match with a zip code
						String addrPlusZip = c.address + " " + c.zip;
						String DBSaddrPlusZip = dbsCust.address + " " + dbsCust.zip;
						if ( !potentialMatch && (addrScore = FuzzySearch.ratio( addrPlusZip, DBSaddrPlusZip )) > MIN_ADDR_SCORE ) {
							potentialMatch = true;
						}
					} else if ( !potentialMatch && (addrScore = FuzzySearch.ratio( c.address, dbsCust.address )) > MIN_ADDR_SCORE ) { 
						potentialMatch = true; 
					}
				}
				if (dbsCust.name != null && c.name != null){
					if ( !potentialMatch && (custNmScore = FuzzySearch.partialRatio( c.name, dbsCust.name ) ) > MIN_CUSTNAME_SCORE ) { 
						potentialMatch = true; 
					}
				}
				//TODO add influencers into the scoring
				
				//if it is a potential match add it to c, which has an array list of potentialDBSCustomers
				//this means it passes certain threshold
				if (potentialMatch){
					createDBSCustomerwithMatchScore(c, dbsCust, phoneScore, addrScore, custNmScore);
				}
			} //end of loop checking each DBS record for matches
		} //end of list of customers in excel file
		
		
	}

	/**
	 * Method takes a excel customer object, that has a matched DBS customer object and adds it to the excel customer objects
	 * potential match list with an aggregated score. This method creates a deep copy of the DBS customer object so it can add the
	 * customer with a specific score.
	 * @param excelCO excel customer object that has a potential DBS customer matched for it
	 * @param matchedCO DBS customer object that is potential match
	 * @param pScore phone number score of match
	 * @param aScore address score of match
	 * @param nScore customer name score of match
	 */
	//TODO think about the storage vs access of the dbs customers. Creating potentially a lot of space use for customers taht aren't
	//really being used
	private static void createDBSCustomerwithMatchScore(excelCustomerObj excelCO, CustomerObj matchedCO, double pScore, double aScore, double nScore) {
		// TODO Auto-generated method stub
		//create a deep copy of this DBS customer and add a match score
		CustomerObj matchedDBSCust = new CustomerObj(matchedCO.cuno, matchedCO.name, null, matchedCO.parent, matchedCO.address, matchedCO.phone, matchedCO.zip); 
		for(String inf : matchedCO.influencers){
			matchedDBSCust.addInfluencer(inf);
		}
		//create match score then add to potential customer list for Customer
		matchedDBSCust.setMatchScore( aggrScore( pScore, aScore, nScore ) );
		excelCO.addPotentialDBSCustomer(matchedDBSCust);
	}
}
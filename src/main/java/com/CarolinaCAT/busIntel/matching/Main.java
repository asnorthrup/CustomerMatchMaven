package com.CarolinaCAT.busIntel.matching;


import java.sql.ResultSet;
import java.util.ArrayList;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Java Program to read an excel sheet and create customer matches
 * @author ANorthrup
 *
 */
public class Main {
	/**Min Addr Score in fuzzy matcher */
	public static final int MIN_ADDR_SCORE = 90;
	public static final int MIN_CUSTNAME_SCORE = 90;
	//creates a composite match score to assign to a potential match

	
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
		ExcelWorkbook wbOfCusts = new ExcelWorkbook("C:\\Users\\anorthrup\\Documents\\EDA_Export_FullFile_20180329.xlsx");
		int pctCompleteCounter = 1;
		for ( excelCustomerObj c : wbOfCusts.customersInWB){
			//c is a potential customer, check for match in customerList
			if((pctCompleteCounter % 10) == 0){
				double pct = ((double)pctCompleteCounter / wbOfCusts.customersInWB.size()) * 100;
				System.out.println("Approx " + pct + " % of Matching Complete") ;
			}
			for (CustomerObj dbsCust : dbsCustomerList){
				//check the 'c' customer against dbs customers to find matches
				boolean exactMatch = false;
				boolean partialMatchAddr = false;
				boolean partialMatchName = false;
				//Scores are for each DBS customer
				//Future enhancement could include a way to narrow out DBS customers, but doesn't really seem likely
				int phoneScore = 0;
				int addrScore = 0;
				int custNameScore = 0;
				//need to short circuit if already true -- actually don't do this, you'll use match score to figure out
				
				phoneScore = MatcherHelpers.getPhoneScore(dbsCust.phone, c.phone);
				if ( dbsCust.phone != null && c.phone != null && dbsCust.phone.length() > 6 && c.phone.length() > 6 && dbsCust.phone.equals(c.phone)){
					phoneScore = 100;
					exactMatch = true;
					if (exactMatch){
						//TODO create enum for match type
						createDBSCustomerwithMatchScore(c, dbsCust, phoneScore, "Phone");
					}
				}
				if (!exactMatch){
					addrScore = MatcherHelpers.getAddressScore(dbsCust.address, dbsCust.zipCode, c.address, c.zipCode);
					if (addrScore == 100){ exactMatch = true; }
					if(exactMatch){
						createDBSCustomerwithMatchScore(c, dbsCust, addrScore, "Address");
					}
					if (addrScore > MIN_ADDR_SCORE){ partialMatchAddr = true; }
				}
				if(!exactMatch){
//					if(dbsCust.name.contains("BLYTHE")){
//						System.out.println("Start debug");
//					}
					custNameScore = MatcherHelpers.getNameScore(dbsCust.name, c.name);
					if (custNameScore == 100){ exactMatch = true; }
					if(exactMatch){
						createDBSCustomerwithMatchScore(c, dbsCust, custNameScore,"Customer Name");
					}
					if (custNameScore > MIN_CUSTNAME_SCORE){ partialMatchName = true; }
				}
				
				//TODO no exact matches found, so need to create a composite score if there is a partial match? probably need a composite score for each match type
				if(partialMatchAddr && partialMatchName){
					createDBSCustomerwithMatchScore(c, dbsCust,MatcherHelpers.aggrScore(addrScore, custNameScore), "Composite");				
				} else if (partialMatchName){
					createDBSCustomerwithMatchScore(c, dbsCust,custNameScore, "Customer Name");
				} else if (partialMatchAddr){
					createDBSCustomerwithMatchScore(c, dbsCust, addrScore, "Address");
				}

			} //end of loop checking each DBS record for matches
			pctCompleteCounter++;
		} //end of list of customers in excel file
		wbOfCusts.addSheetOfMatches("C:\\Users\\anorthrup\\Documents\\EDA_Export_FullFile_20180329_match.xlsx");
		System.out.println("Done!") ;
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
	private static void createDBSCustomerwithMatchScore(excelCustomerObj excelCO, CustomerObj matchedCO, double matchScore, String matchType) {
		// TODO Auto-generated method stub
		//create a deep copy of this DBS customer and add a match score
		CustomerObj matchedDBSCust = new CustomerObj(matchedCO.cuno, matchedCO.name, null, matchedCO.parent, matchedCO.address, matchedCO.phone, matchedCO.zipCode); 
		for(String inf : matchedCO.influencers){
			matchedDBSCust.addInfluencer(inf);
		}
		//create match score then add to potential customer list for Customer
		matchedDBSCust.setMatchScore( matchScore );
		matchedDBSCust.setMatchType( matchType );
		excelCO.addPotentialDBSCustomer(matchedDBSCust);
	}
}

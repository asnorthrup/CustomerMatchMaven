package com.CarolinaCAT.busIntel.matching;


import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.CarolinaCAT.busIntel.view.MatcherStart;
import com.CarolinaCAT.busIntel.view.ProgressBar;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Java Program to read an excel sheet and create customer matches
 * @author ANorthrup
 *
 */
public class CustomerMatcher {
	/**Min Addr Score in fuzzy matcher */
	private int MIN_ADDR_SCORE = 90;
	private int MIN_CUSTNAME_SCORE = 90;
	
	public void setMIN_ADDR_SCORE(int mIN_ADDR_SCORE) {
		MIN_ADDR_SCORE = mIN_ADDR_SCORE;
	}

	public void setMIN_CUSTNAME_SCORE(int mIN_CUSTNAME_SCORE) {
		MIN_CUSTNAME_SCORE = mIN_CUSTNAME_SCORE;
	}

	
	//creates a composite match score to assign to a potential match

	
	public CustomerMatcher(String inputFileNameAndPath, String outputFileNameAndPath, ProgressBar progBarFrame, int[] inputs, String tabName, int minNameScore) throws Exception
	{
		MIN_CUSTNAME_SCORE = minNameScore;
		//DBS Query setup
		String queryCode = "SELECT CUNO, CUNM, CUNM2, PRCUNO, CUADD2, PHNO, ZIPCD9 "
				+ "FROM D09IL01.LIBD09.CIPNAME0 "
				+ "WHERE CUNO NOT LIKE ('I%')";
		//System.out.println("started query build"); //DEBUG
		DBSquery customersQuery = new DBSquery(queryCode);
		
		//Saleslink query set up -- Remove for now
		//String prospectQueryCode = "SELECT [CustomerNo],[CustomerName],isnull([Address1],'')+' '+isnull([Address2],'')+' '+isnull([Address3],''),"
		//		+ "[PostalCode],[Phone] FROM [AppDb_SalesLink].[dbo].[SalesLink_vCustomer] WHERE [CustomerNo] like ('$%')";
		//SaleslinkQuery prospectsQuery = new SaleslinkQuery(prospectQueryCode);
		
		// (NOTE THIS DIDN'T SEEM NECESSARY -- REMOVE AFTER TESTING)
//		int prospectColumnCount = prospectsQuery.getResultSetMetaData().getColumnCount();
//		int dbsColumnCount = customersQuery.getResultSetMetaData().getColumnCount();
//		int size = dbsColumnCount;
		
		/*Execute customer queries and store customers in an array list called customerList*/
		//ResultSetMetaData stores properties of a ResultSet object, including column count
		
		int approxDBSCustomers = 80000;
		int numReadIn = 1;
		int initArrayListCap = (int) (.25 * 80000);
		ArrayList<CustomerObj> ourCustomers = new ArrayList<CustomerObj>(initArrayListCap); 
		
		
		ResultSet result = customersQuery.getResultSet(); 
		while(result.next()){
			//create the customer object
			//TODO add physical address
			CustomerObj co = new CustomerObj(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
			//add to arraylist of customer objects
			ourCustomers.add(co);
			//give an indicator to user as to how far along in reading goes
			if((numReadIn % 1000) == 0){
				double pct = ((double)numReadIn / approxDBSCustomers) * 100;
				System.out.println("Approx " + pct + "% DBS loaded") ;
				progBarFrame.setPBImportDBS((int) pct);
			}
			numReadIn++;
		}
		
		/////////////////////////////////***   SALESLINK QUERY   ***///////////////////////////
		//TODO check this worked
		//Parse the Saleslink query
		//Num, name, addr, zip, phone
//		ResultSet prospectResult = prospectsQuery.getResultSet();
//		while(prospectResult.next()){
//			CustomerObj co = new CustomerObj(prospectResult.getString(1), prospectResult.getString(2), null, null, prospectResult.getString(3), prospectResult.getString(5), prospectResult.getString(4));
//			ourCustomers.add(co);
//			//give an indicator to user as to how far along in reading goes
//			if((numReadIn % 1000) == 0){
//				double pct = ((double)numReadIn / approxDBSCustomers) * 100;
//				System.out.println("Approx " + pct + "% DBS loaded") ;
//				progBarFrame.setPBImportDBS((int) pct);
//			}
//			numReadIn++;
//		}
		/////////////////////////////////*** END SALESLINK QUERY   ***////////////////////////////
		
		progBarFrame.setPBImportDBS(100);
		System.out.println("100% DBS loaded!") ;
		/*Have all DBS customers read into an array called customerList, now need to get excel file path and populate that*/
		//need to go through the potential customers
		//remember must escape sequence back slash with a backslash
		ExcelWorkbook wbOfCusts = new ExcelWorkbook(inputFileNameAndPath, progBarFrame, inputs, tabName);
		int pctCompleteCounter = 1;
		for ( excelCustomerObj c : wbOfCusts.customersInWB){
			//c is a potential customer, check for match in customerList
			if((pctCompleteCounter % 10) == 0){
				double pct = ((double)pctCompleteCounter / wbOfCusts.customersInWB.size()) * 100;
				System.out.println("Approx " + pct + " % of Matching Complete") ;
				progBarFrame.setPBGenMatches((int) pct);
			}
			for (CustomerObj dbsCust : ourCustomers){
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
				
				//TODO check influencers after influencers are loaded
				
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
		wbOfCusts.addSheetOfMatches(outputFileNameAndPath);
		progBarFrame.setPBGenMatches(100);
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

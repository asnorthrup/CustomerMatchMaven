package com.CarolinaCAT.busIntel.matching;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	private int MIN_ADDR_SCORE = 100;
	/**Min customer name score*/
	private int MIN_CUSTNAME_SCORE = 90;
	
	//Note - this isn't set yet
	/**
	 * Set the minimum address match score for a potential match
	 * @param MIN_ADDR_SCORE int for what to set the minimum address score to for potential match
	 */
	public void setMIN_ADDR_SCORE(int MIN_ADDR_SCORE) {
		this.MIN_ADDR_SCORE = MIN_ADDR_SCORE;
	}

	/**
	 * Set the minimum customer name match score for a potential match, this is set by a spinner for customer name
	 * @param MIN_CUSTNAME_SCORE int for what to set the minimum name score to for potential match
	 */
	public void setMIN_CUSTNAME_SCORE(int MIN_CUSTNAME_SCORE) {
		this.MIN_CUSTNAME_SCORE = MIN_CUSTNAME_SCORE;
	}

	/**
	 * Constructor for the customer matcher class, this class initiates the matching
	 * @param inputFileNameAndPath as string for the xlsx file to read in customers from
	 * @param outputFileNameAndPath as a string for the file (that will be written to xlsx) in the same path as the input file
	 * @param progBarFrame this is the jFrame that gets updated to show progress of the matcher
	 * @param inputs an array of ints to indicate which columns user indicated key values are in
	 * @param tabName a string to indicate what the name of the tab the data is in
	 * @param minNameScore int send in by minimum name spinner set by user
	 * @throws Exception
	 */
	public CustomerMatcher(String inputFileNameAndPath, String outputFileNameAndPath, ProgressBar progBarFrame, int[] inputs, String tabName, int minNameScore) throws Exception
	{
		//class used to make name translations
		Translators translator = new Translators();
		
		MIN_CUSTNAME_SCORE = minNameScore;
		//DBS Query string setup
		String queryCode = "SELECT CIP.CUNO, CIP.CUNM, CIP.CUNM2, CIP.PRCUNO, CIP.PHNO, "
				+ "CASE WHEN length(trim(CIP.CUADD2)) > 0 THEN trim(CIP.CUADD2) WHEN length(trim(CIP.CUADD3)) > 0 THEN trim(CIP.CUADD3) "
				+ "WHEN length(trim(CIP.CUADD1)) > 0 THEN trim(CIP.CUADD1) Else 'No Bill Adr' END AS BILL_ADR, CIP.ZIPCD9, "
				+ "CASE WHEN length(trim(PHYS.CUADD2)) > 0 THEN trim(PHYS.CUADD2) WHEN length(trim(PHYS.CUADD3)) > 0 THEN trim(PHYS.CUADD3) "
				+ "WHEN length(trim(PHYS.CUADD1)) > 0 THEN trim(PHYS.CUADD1) ELSE 'No Phys Adr' END AS PHYS_ADR, PHYS.ZIPCD9 AS PHYSZIP "
				+ "FROM D09IL01.libd09.CIPNAME0 CIP LEFT JOIN libd09.CIPLADR0 PHYS ON (CIP.CUNO = PHYS.CUNO) "
				+ "WHERE CIP.CUNO NOT LIKE ('I%')";

		
		
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
		
		int approxDBSCustomers = 60000;
		int numReadIn = 1;
		int custHashSize = (int) (1.3 * approxDBSCustomers); //recommended size is number of expected / .75 for hash
		//ArrayList<CustomerObj> ourCustomers = new ArrayList<CustomerObj>(initArrayListCap);
		HashMap<String, CustomerObj> ourCustomers = new HashMap<String, CustomerObj>(custHashSize);
		
		
		ResultSet result = customersQuery.getResultSet();
		//maybe the .next should be in try/catch
		while(result.next()){
			//create the customer object
			CustomerObj co = new CustomerObj(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9));
			//use the translator to modify customer name and customer address if it has PO Box
			if(co.name != null){
				co.name_translated = translator.customerNameTranslations(co.name);
				co.name_translated = translator.stripBeginning(co.name_translated);
				co.name_translated = translator.stripEndings(co.name_translated);
			}
			if(co.billAddress != null && !co.billAddress.equals("No Bill Adr")){
				co.billAddress = translator.modPOBox(co.billAddress);
			}
			if(co.physAddress != null && !co.physAddress.equals("No Phys Adr")){
				co.physAddress = translator.modPOBox(co.physAddress);
			}
			//add to hasmap of customer objects for our customer
			ourCustomers.put(co.cuno, co);
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
		ExcelWorkbook wbOfCusts = new ExcelWorkbook(inputFileNameAndPath, progBarFrame, inputs, tabName, translator);
		translator = null; //free up space now that translator is no longer needed
		
		int pctCompleteCounter = 1; //counter for pct complete calculator
		for ( excelCustomerObj cExcelCust : wbOfCusts.customersInWB){
			//c is a potential customer, check for match in customerList
			if((pctCompleteCounter % 10) == 0){
				double pct = ((double)pctCompleteCounter / wbOfCusts.customersInWB.size()) * 100;
				System.out.println("Approx " + pct + " % of Matching Complete") ;
				progBarFrame.setPBGenMatches((int) pct);
			}			
			
			for (Map.Entry<String, CustomerObj> dbsCust : ourCustomers.entrySet()){
				//check the 'c' customer against dbs customers to find matches
				boolean exactMatch = false;
				//TODO need a partial billing match and addr match?
				boolean partialMatchPhysAddr = false;
				boolean partialMatchBillAddr = false;
				boolean partialMatchName = false;
				//Scores are for each DBS customer
				//Future enhancement could include a way to narrow out DBS customers, but doesn't really seem likely
				int phoneScore = 0;
				int addrScore = 0;
				int custNameScore = 0;
				//create phone score
				//TODO why are we creating a phone score and also checking if equal
				if(dbsCust.getValue().phone != null && cExcelCust.phone != null){
					phoneScore = MatcherHelpers.getPhoneScore(dbsCust.getValue().phone, cExcelCust.phone);
				}
				//TODO - why didn't phone number 336-838-7368 match 3368387368
				if ( dbsCust.getValue().phone != null && cExcelCust.phone != null && dbsCust.getValue().phone.length() > 6 && cExcelCust.phone.length() > 6 && dbsCust.getValue().phone.equals(cExcelCust.phone)){
					phoneScore = 100;
					exactMatch = true;
					if (exactMatch){
						//TODO create enum for match type
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, phoneScore, "Phone");
					}
				}
				//check excel customer address(es) against DBS customer billing address (reminder -- excel customer billing/physical is just placeholder for
				//holding two addresses, doesn't specifically mean it is a billing or physical address
				//TODO still evaluate this logic for address scores
				if (!exactMatch){
					if(cExcelCust.billAddress != null && dbsCust.getValue().billAddress != null){
						addrScore = MatcherHelpers.getAddressScore(dbsCust.getValue().billAddress, dbsCust.getValue().billZipCode, 
								cExcelCust.billAddress, cExcelCust.billZipCode);
					}
					if(addrScore < 100 && cExcelCust.physAddress != null && dbsCust.getValue().billAddress != null){ //TODO output will only be billing address of excel customer
						int score = MatcherHelpers.getAddressScore(dbsCust.getValue().billAddress, dbsCust.getValue().billZipCode, 
								cExcelCust.physAddress, cExcelCust.physZipCode);
						if( score > addrScore ){ addrScore = score; }
					}
					if(addrScore == 100){
						exactMatch = true;
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, addrScore, "Billing Address");
					}
					if (addrScore > MIN_ADDR_SCORE && !exactMatch){ partialMatchBillAddr = true; }
				}
				//check excel customer address(es) against DBS customer physical address
				if (!exactMatch){
					int physAddrScore = 0;
					if(cExcelCust.physAddress != null && dbsCust.getValue().physAddress != null){
						physAddrScore = MatcherHelpers.getAddressScore(dbsCust.getValue().physAddress, dbsCust.getValue().physZipCode, 
								cExcelCust.physAddress, cExcelCust.physZipCode);
					}
					if(physAddrScore < 100 && cExcelCust.billAddress != null && dbsCust.getValue().physAddress != null){ //TODO output will only be billing address of excel customer
						int score = MatcherHelpers.getAddressScore(dbsCust.getValue().billAddress, dbsCust.getValue().billZipCode, 
								cExcelCust.physAddress, cExcelCust.physZipCode);
						if(score > physAddrScore){ physAddrScore = score; }
					}
					//TODO if exact match happens, make sure neither partial match is set
					if(physAddrScore == 100){
						exactMatch = true;
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, physAddrScore, "Physical Address");
					}
					//check if physical address score is greater than billing
					//TODO what to do if there are two partial matches
					if( !exactMatch && physAddrScore > addrScore ){ 
						addrScore = physAddrScore; 
						if (physAddrScore > MIN_ADDR_SCORE){ //at the moment, this isn't used, b/c min is 100
							partialMatchPhysAddr = true; 
						} //This hasn't been used yet b/c address is set to 100
					}
				}
				if(!exactMatch){
					custNameScore = MatcherHelpers.getNameScore(dbsCust.getValue().name_translated, cExcelCust.name_translated);
					if (custNameScore == 100){ exactMatch = true; }
					if(exactMatch){
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, custNameScore,"Customer Name");
					}
					if (custNameScore > MIN_CUSTNAME_SCORE){ partialMatchName = true; }
				}
				
				//TODO check influencers after influencers are loaded
				
				//TODO no exact matches found, so need to create a composite score if there is a partial match? probably need a composite score for each match type
				if (!exactMatch){
					if( (partialMatchBillAddr || partialMatchPhysAddr) && partialMatchName){
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, MatcherHelpers.aggrScore(addrScore, custNameScore), "Composite");				
					} else if (partialMatchName){
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, custNameScore, "Customer Name");
					} else if (partialMatchBillAddr){
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, addrScore, "Billing Address");
					} else if (partialMatchPhysAddr){
						createDBSCustomerwithMatchScore(cExcelCust, dbsCust.getValue().cuno, addrScore, "Physical Address");
					}
				}
			} //end of loop checking each DBS record for matches
			pctCompleteCounter++;
		} //end of list of customers in excel file
		
		//use sheet of matches to generate output to file path
		wbOfCusts.addSheetOfMatches(outputFileNameAndPath, ourCustomers);
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
	private static void createDBSCustomerwithMatchScore(excelCustomerObj excelCO, String custNum, double matchScore, String matchType) {
		// TODO Auto-generated method stub
		//create a deep copy of this DBS customer and add a match score
		PotentialMatch potentialMatchedDBSCust = new PotentialMatch(custNum, matchScore, matchType);
		//TODO deal with influencers differently
//		for(String inf : matchedCO.influencers){
//			matchedDBSCust.addInfluencer(inf);
//		}
		//create match score then add to potential customer list for Customer
		
		//TODO create hashmap of customer objects, just save score, type, and customer num; Need to create a dbs match object
//		matchedDBSCust.setMatchScore( matchScore );
//		matchedDBSCust.setMatchType( matchType );
		excelCO.addPotentialDBSCustomer(potentialMatchedDBSCust);
	}
}

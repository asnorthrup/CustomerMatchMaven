package com.CarolinaCAT.busIntel.matching;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import com.CarolinaCAT.busIntel.view.MatcherStart;
import com.CarolinaCAT.busIntel.view.ProgressBar;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Java Program to read an excel sheet and create customer matches
 * @author ANorthrup
 *
 */

public class ReadCustomerData extends SwingWorker<HashMap<String, CustomerObj>,Void> {
	/**Min Addr Score in fuzzy matcher */
	private int MIN_ADDR_SCORE = 100;
	/**Min customer name score*/
	private int MIN_CUSTNAME_SCORE = 90;
	
	//from the initialization method
	private String dBSOdbcConn;
	private String dealerSchema;
	private String dbPath;
	private Boolean onlyProspects;
	private int CustomerEst;
	private String inputFileNameAndPath;
	private String outputFileNameAndPath;
	private ProgressBar progBarFrame;  //may not need
	private int[] inputs;
	private String tabName;
	private Translators translator;
	
	private HashMap<String, CustomerObj> ourCustomers;
	
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
	public ReadCustomerData(String dBSOdbcConn, String dealerSchema, String dbPath, Boolean onlyProspects, 
			int CustomerEst,String inputFileNameAndPath, String outputFileNameAndPath, ProgressBar progBarFrame, 
			int[] inputs, String tabName, int minNameScore, Translators translator) throws Exception
	{
		//Set variables
		MIN_CUSTNAME_SCORE = minNameScore;
		this.dBSOdbcConn=dBSOdbcConn;
		this.dealerSchema=dealerSchema;
		this.dbPath=dbPath;
		this.onlyProspects=onlyProspects;
		this.CustomerEst=CustomerEst;
		this.inputFileNameAndPath=inputFileNameAndPath;
		this.outputFileNameAndPath=outputFileNameAndPath;
		this.progBarFrame=progBarFrame;  //may not need
		this.inputs=inputs;
		this.tabName=tabName;
		this.translator = translator;
		
		
		//below should be background task
		
	}

	@Override
	protected HashMap<String, CustomerObj> doInBackground() throws Exception {

		//Start query
		String queryCode  = null;
		DBSquery customersQuery = null;
		AccessProspectsQuery prospectsQuery = null;
		//DBS Query string setup
		if (dBSOdbcConn != null && dealerSchema != null){ //TODO need to get dealer specific CIPNAME and CIPLADR location
			queryCode = "SELECT CIP.CUNO, CIP.CUNM, CIP.CUNM2, CIP.PRCUNO, CIP.PHNO, "
				+ "CASE WHEN length(trim(CIP.CUADD2)) > 0 THEN trim(CIP.CUADD2) WHEN length(trim(CIP.CUADD3)) > 0 THEN trim(CIP.CUADD3) "
				+ "WHEN length(trim(CIP.CUADD1)) > 0 THEN trim(CIP.CUADD1) Else 'No Bill Adr' END AS BILL_ADR, CIP.ZIPCD9, "
				+ "CASE WHEN length(trim(PHYS.CUADD2)) > 0 THEN trim(PHYS.CUADD2) WHEN length(trim(PHYS.CUADD3)) > 0 THEN trim(PHYS.CUADD3) "
				+ "WHEN length(trim(PHYS.CUADD1)) > 0 THEN trim(PHYS.CUADD1) ELSE 'No Phys Adr' END AS PHYS_ADR, PHYS.ZIPCD9 AS PHYSZIP "
				+ "FROM " + dealerSchema + ".CIPNAME0 CIP LEFT JOIN " + dealerSchema + ".CIPLADR0 PHYS ON (CIP.CUNO = PHYS.CUNO) "
				+ "WHERE CIP.CUNO NOT LIKE ('I%')";
			customersQuery = new DBSquery(queryCode, dBSOdbcConn);
		}
		String qry = null;
		if (dbPath != null){
			if( onlyProspects ){
				qry = "SELECT SaleslinkCustomers.* FROM SaleslinkCustomers WHERE (((SaleslinkCustomers.CustomerNo) Like '$%'))";
			} else {
				qry = "SELECT SaleslinkCustomers.* FROM SaleslinkCustomers";
			}		
			prospectsQuery = new AccessProspectsQuery( qry, dbPath );
			//prospect results: Custnum, Cust name, phys addr, bill addr, addr3, city, zip, phone
		}
		
		int approxDBSCustomers = 70000;
		
		if ( CustomerEst > 0 ){
			approxDBSCustomers = CustomerEst;
		}
		
		int numReadIn = 1;
		int custHashSize = (int) (1.3 * approxDBSCustomers); //recommended size is number of expected / .75 for hash
		//ArrayList<CustomerObj> ourCustomers = new ArrayList<CustomerObj>(initArrayListCap);
		ourCustomers = new HashMap<String, CustomerObj>(custHashSize);
		
		if(customersQuery != null){
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
		}
		
		if(prospectsQuery != null){
			//get prospect result set and parse from the MS Access query of prospects (already embedded in the Access Prospects query object
			ResultSet prospectRS = prospectsQuery.getResultSet();	
			while(prospectRS.next()){
				CustomerObj co = new CustomerObj(prospectRS.getString(1), prospectRS.getString(2), null, null, prospectRS.getString(8),prospectRS.getString(4), prospectRS.getString(7), prospectRS.getString(3), prospectRS.getString(7));
				//perform translations on the customer object
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
			
				//add to hashmap of customer objects for our customer
				ourCustomers.put(co.cuno, co);
				if((numReadIn % 1000) == 0){
					double pct = ((double)numReadIn / approxDBSCustomers) * 100;
					System.out.println("Approx " + pct + "% DBS loaded") ;
					progBarFrame.setPBImportDBS((int) pct);
				}
				numReadIn++;
			}
		}
		
		progBarFrame.setPBImportDBS(100);
		System.out.println("100% DBS loaded!") ;
		
		
		//*****************SHOULD BE ITS OWN CLASS**************/
		
		/*Have all DBS customers read into an array called customerList, now need to get excel file path and populate that*/
		ExcelWorkbook wbOfCusts = new ExcelWorkbook(inputFileNameAndPath, progBarFrame, inputs, tabName, translator);
		translator = null; //free up space now that translator is no longer needed
		
		//***********SHOULD BE ITS OWN CLASS****************/
		
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
		return null;
	}
	
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
	 * Method takes a excel customer object, that has a matched DBS customer object and adds it to the excel customer objects
	 * potential match list with an aggregated score. This method creates a deep copy of the DBS customer object so it can add the
	 * customer with a specific score.
	 * @param excelCO excel customer object that has a potential DBS customer matched for it
	 * @param matchedCO DBS customer object that is potential match
	 * @param pScore phone number score of match
	 * @param aScore address score of match
	 * @param nScore customer name score of match
	 */
	private static void createDBSCustomerwithMatchScore(excelCustomerObj excelCO, String custNum, double matchScore, String matchType) {
		//create a deep copy of this DBS customer and add a match score
		PotentialMatch potentialMatchedDBSCust = new PotentialMatch(custNum, matchScore, matchType);
		//TODO deal with influencers differently
		excelCO.addPotentialDBSCustomer(potentialMatchedDBSCust);
	}
}

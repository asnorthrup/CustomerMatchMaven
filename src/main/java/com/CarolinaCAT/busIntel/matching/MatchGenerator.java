package com.CarolinaCAT.busIntel.matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingWorker;

public class MatchGenerator extends SwingWorker<ArrayList<excelCustomerObj>,Void>{
	
	//set inputs
	private ArrayList<excelCustomerObj> listOfUnknownCustsToMatch;
	private HashMap<String, CustomerObj> ourCustomers;
	private String outputFileNameAndPath;
	
	/**Min Addr Score in fuzzy matcher */
	private int MIN_ADDR_SCORE = 100;
	/**Min customer name score*/
	private int MIN_CUSTNAME_SCORE = 90;
	
	public MatchGenerator(ArrayList<excelCustomerObj> wbOfCusts, HashMap<String, CustomerObj> ourCustomers, int minAdrScore, int minNameScore){
		this.listOfUnknownCustsToMatch = wbOfCusts;
		this.ourCustomers = ourCustomers;
		this.outputFileNameAndPath = outputFileNameAndPath;
		if( minAdrScore != -1 ){
			MIN_ADDR_SCORE = minAdrScore;
		}
		if( minNameScore != -1 ){
			MIN_CUSTNAME_SCORE = minNameScore;
		}
	}
	
	@Override
	protected ArrayList<excelCustomerObj> doInBackground() throws Exception {
		int pctCompleteCounter = 1; //counter for pct complete calculator
		for ( excelCustomerObj cExcelCust : listOfUnknownCustsToMatch){
			//c is a potential customer, check for match in customerList
			if((pctCompleteCounter % 10) == 0){
				double pct = ((double)pctCompleteCounter / listOfUnknownCustsToMatch.size()) * 100;
				System.out.println("Approx " + pct + " % of Matching Complete") ;
				if( (int) pct > 0 && (int) pct < 100 ){
					setProgress((int) pct);
				}
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
	
		setProgress(100);
		System.out.println("Done!") ;
		return listOfUnknownCustsToMatch;
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
}

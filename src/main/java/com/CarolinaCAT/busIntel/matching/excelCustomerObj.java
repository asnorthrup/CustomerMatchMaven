package com.CarolinaCAT.busIntel.matching;


import java.util.ArrayList;

/**Extends a customer object, but this is a customer that is read in from excel*/
public class excelCustomerObj extends CustomerObj {
	//row of custCellRow
	public int custCellRow;
	/**Each customer in excel will have a list of potential matches from DBS, this variable stores those customers in arraylist*/
	public ArrayList<PotentialMatch> potenDBSMatches;
	
	//this will need a cell, won't have a second nm2, wont have a pc

	//TODO why a name 2?
	/**
	 * Constructor for a (excel sheet's) customer that is read in from excel
	 * @param c as the (excel sheet's) customer number
	 * @param nm as the (excel sheet's) customer name (first half if applicable)
	 * @param nm2 as the (excel sheet's) customer name that didn't fit in first (second half if applicable)
	 * @param pc as parent of the (excel sheet's) customer number if exists
	 * @param addr as street address of (excel sheet's) customer, this will be billing address of customer object
	 * @param zipCode as zip code of (excel sheet's) customer, this will be billing zip of customer object
	 * @param addr2 (excel sheet) address 2, may not be given
	 * @param zipCode2 (excel sheet) zip code 2, may not be given
	 * @param ph as the phone number of the (excel sheet's) customer
	 * @param cr as the current row the iterator is on in the excel sheet
	 */
	public excelCustomerObj(String c, String nm, String nm2, String pc,
			String addr, String zipCode, String addr2 , String zipCode2, String ph, int cr) {
		//only use billing address
		super(c, nm, nm2, pc, ph, addr, zipCode, addr2, zipCode2);
		custCellRow = cr;
		//created the excelCustomerObj
		potenDBSMatches= new ArrayList<PotentialMatch>(); 
	}
	
	//use this to add potential DBS matches
	/**
	 * Adds a potential match to the excel customer. Must create a copy first that has a match score associated with it
	 * @param matchedDBSCust customer object with a match score
	 */
	public void addPotentialDBSCustomer(PotentialMatch matchedDBSCust){
		potenDBSMatches.add(matchedDBSCust);
	}

}

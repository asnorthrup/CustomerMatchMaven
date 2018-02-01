package com.CarolinaCAT.busIntel.matching;


import java.util.ArrayList;

public class excelCustomerObj extends CustomerObj {
	//row of custCellRow
	public int custCellRow;
	public ArrayList<CustomerObj> potenDBSMatches;
	
	//this will need a cell, won't have a second nm2, wont have a pc
	public excelCustomerObj(String c, String nm, String nm2, String pc,
			String addr, String zipCode,String ph, int cr) {
		super(c, nm, nm2, pc, addr, ph, zipCode);
		// TODO Auto-generated constructor stub
		custCellRow = cr;
		//created the excelCustomerObj
		potenDBSMatches= new ArrayList<CustomerObj>(); 
	}
	
	//use this to add potential DBS matches
	public void addPotentialDBSCustomer(CustomerObj cust){
		potenDBSMatches.add(cust);
	}

}

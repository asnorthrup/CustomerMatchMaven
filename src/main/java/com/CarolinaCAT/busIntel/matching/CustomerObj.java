package com.CarolinaCAT.busIntel.matching;


import java.util.ArrayList;

import javax.swing.DebugGraphics;

public class CustomerObj{
	public String cuno;
	public String name;
	//TODO use this instead for matching
	public String name_translated;
	public String parent;
	public String billAddress;
	public String billZipCode;
	public String physAddress;
	public String physZipCode;
	//TODO need to add second address and second ZIP
	public String phone;
	//we will be doing influencers differently -- an arraylist is too much overhead for this purpose
	//public ArrayList<String> influencers; //use this to store all of the influencers for the customer
//	public double matchScore;
//	public String matchType;


	/**
	 * Constructor for a customer objects
	 * @param custNum String for customer number
	 * @param nm String for company name of customer
	 * @param nm2 String for second company name of customer
	 * @param parentNum parentNum String for parent customer number
	 * @param ph String for phone number
	 * @param billAddr String for billing address
	 * @param billZipCode String for billing zip code
	 * @param physAddr String for physical address
	 * @param physZipCode String for billing zip code
	 */
	public CustomerObj(String custNum, String nm, String nm2, String parentNum, String ph, String billAddr, String billZipCode, String physAddr, String physZipCode){
		//trim and check for length before assigning
		if (custNum != null && custNum.trim().length()>0){
			cuno = custNum.trim();
		}
		if (nm != null && nm.trim().length()>0){
			//need to append cnm2 if it exists
			if (nm2 != null && nm2.trim().length()>0){
				name = nm.trim() + " " + nm2.trim();
			} else {
				name = nm.trim();
			}
		}
		if (parentNum != null && parentNum.trim().length()>0){
			parent = parentNum;
		}
		//set billing addr and zip
		if (billAddr != null && billAddr.trim().length()>0){
			//TODO Need to do some translations of road to Rd, Avenue to Ave, etc.
			this.billAddress = billAddr.trim();
		}
		if (billZipCode != null && billZipCode.trim().length()>0){
			this.billZipCode = billZipCode.trim();
			this.billZipCode = formatZip(this.billZipCode);
		}
		
		//set phys addr and zip
		if (physAddr != null && physAddr.trim().length()>0){
			//TODO Need to do some translations of road to Rd, Avenue to Ave, etc.
			this.physAddress = physAddr.trim();
		}
		if (physZipCode != null && physZipCode.trim().length()>0){
			this.physZipCode = physZipCode.trim();
			this.physZipCode = formatZip(this.physZipCode);
		}
		//if one one zip is supplied, use it for both
		if (this.billAddress != null && this.billZipCode == null && this.physZipCode != null){
			this.billZipCode = this.physZipCode;
		} else if (this.physAddress != null && this.physZipCode == null && this.billZipCode != null) {
			this.physZipCode = this.billZipCode;
		}
		
		if (ph != null && ph.trim().length()>0){
			phone = formatPhone(ph);
		}
		//TODO handle influencers differently
	}
	
	private String formatZip(String zipCode) {
		if(zipCode.equals("00000") || zipCode.equals("99999")){
			return null;
		} else if (zipCode.length() == 5){
			return zipCode;
		} else if ( zipCode.length() != 5 && zipCode.length() > 5 ){
			zipCode = zipCode.substring(0, 5);
			if(zipCode.equals("00000") || zipCode.equals("99999")){
				return null;
			}
			return zipCode;
		}
		return null;
	}
	
	//remove everything except nums from phone number
	private String formatPhone(String s){
		String stt= s.replaceAll("\\D+","");
		if(stt.equals("0000000000") || stt.equals("9999999999") || stt.length() != 10){
			return "N/A";
		} else {
			return stt;
		}
	}
	
	//OLDER CODE for POBox Mod
	//address transformations
//	private void modPOBox(){
//		if( address.contains("Post Office") ){
//			address = address.replace("Post", "P.");
//			address = address.replace("Office", "O.");
//		} else if ( address.substring(0,3).equals("PO ") ){
//			address = address.replace("PO ", "P.O. ");
//		}
//		//TODO go through different versions of PO Box to change to P.O. Box
//	}
	
	//TODO change this from using brute force to creating a global(?) array/hash (probably a static array) of lookups
	//based on the text files.
	//extract name from DBA
//	private void nameTranslations(){
//		//TODO this is where we will be using the translators class
//		int index;
//		name = name.toUpperCase();
//		if ( (index = name.lastIndexOf("DBA") ) != - 1){
//			if( name.length() > index + 3){
//				name = name.substring(index + 3).trim();
//			} else {
//				name = "No Company Name"; //if nothing listed after dba
//			}
//		} else if ((index = name.lastIndexOf("D/B/A") ) != - 1){
//			//System.out.println(name);
//			if(name.length()> index + 6 ){
//				name = name.substring(index + 6).trim(); //some records have D/B/A and then blank
//			} else {
//				name = "No Company Name";
//			}
//			
//		} 
//		//removes Cash Sale from customer name - this probably could be refined
//
//		if (name.contains("CASH SALE")){
//			name = name.replace("CASH SALE","");
//			name = name.trim();
//		} else if (name.contains("CASH SA LE")){
//			name = name.replace("CASH SA LE","");
//			name = name.trim();
//		} else if (name.contains("CASH S ALE")){
//			name = name.replace("CASH S ALE","");
//			name = name.trim();
//		} else if (name.contains("CASH SAL E")){
//			name = name.replace("CASH SAL E","");
//			name = name.trim();
//		} else if (name.contains("CA SH SALE")){
//			name = name.replace("CA SH SALE","");
//			name = name.trim();
//		}else if (name.contains("CAS H SALE")){
//			name = name.replace("CAS H SALE","");
//			name = name.trim();
//		}else if (name.contains("C ASH SALE")){
//			name = name.replace("C ASH SALE","");
//			name = name.trim();
//		}
//	}
	
	
	
	//Careful - If match score gets updated as it goes through list and then adds to array then no good. There doesn't need to be a match
	//score assigned to DBS customer, in fact, there shouldn't
//	public void setMatchScore(double score){
//		matchScore = score;
//	}
//	public void setMatchType(String type){
//		matchType = type;
//	}
	
	public void printCustomer(){
		System.out.println(cuno + " " + name);
	}
	

}

package com.CarolinaCAT.busIntel.matching;


import java.util.ArrayList;

public class CustomerObj{
	public String cuno;
	public String name;
	public String parent;
	public String address;
	public String zip;
	public String phone;
	public ArrayList<String> influencers; //use this to store all of the influencers for the customer
	private double matchScore;
	//for customers read in from excel, need a cell reference
	
	//constructor for customer object
	/**
	 * Constructor for customer object
	 * @param custNum String for customer number 
	 * @param nm String for company name of customer
	 * @param nm2 String for second company name of customer
	 * @param parentNum String for parent customer number
	 * @param addr String for address
	 * @param ph String for phone number
	 * @param zipCode String for zip code
	 */
	public CustomerObj(String custNum, String nm, String nm2, String parentNum, String addr, String ph, String zipCode){
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
		replaceDBA();
		}
		if (parentNum != null && parentNum.trim().length()>0){
			parent = parentNum;
		}
		if (addr != null && addr.trim().length()>0){
			address = addr.trim();
			modPOBox();
		}
		if (zipCode != null && zipCode.trim().length()>0){
			zip = zip.trim();
			if (zip.length() != 5){
				zip = zip.substring(0, Math.min(zip.length(), 5));
			}
		}
		if (ph != null && ph.trim().length()>0){
			phone = formatPhone(ph);
		}
		if (zipCode != null && zipCode.trim().length()>0){
			zip = formatZip(zipCode.trim());
		}
		//set up for influencers, if exist, initial constructor has, which is plenty for most
		influencers = new ArrayList<String>();
	}
	
	//add an influencer
	public void addInfluencer(String inf){
		influencers.add(inf);
	}
	
	//Careful - If match score gets updated as it goes through list and then adds to array then no good. There doesn't need to be a match
	//score assigned to DBS customer, in fact, there shouldn't
	public void setMatchScore(double score){
		matchScore = score;
	}
	
	private String formatZip(String zipCode) {
		if(zipCode.equals("00000") || zipCode.equals("99999")){
			return null;
		} else if (zipCode.length() == 5){
			return zipCode;
		} else if ( zipCode.length() != 5 && zipCode.length() > 5 ){
			return zipCode.substring(0, 4);
		}
		return null;
	}
	
	//remove everything except nums from phone number
	private String formatPhone(String s){
		String stt= s.replaceAll("\\D+","");
		if(stt.equals("0000000000")){
			return "N/A";
		} else {
			return stt;
		}
	}
	
	//address transformations
	private void modPOBox(){
		if( address.contains("Post Office") ){
			address = address.replace("Post", "P.");
			address = address.replace("Office", "O.");
		}
	}
	
	//extract name from DBA
	private void replaceDBA(){
		int index;
		if ( (index = name.lastIndexOf("DBA") ) != - 1){
			name = name.substring(index + 3).trim();
		} else if ((index = name.lastIndexOf("D/B/A") ) != - 1){
			name = name.substring(index + 3).trim();
		} else {
			//do nothing
		}
		//removes Cash Sale from customer name - this probably could be refined
		if (name.contains("CASH SALE")){
			name = name.replace("CASH SALE","");
			name = name.trim();
		} else if (name.contains("CASH SA LE")){
			name = name.replace("CASH SA LE","");
			name = name.trim();
		} else if (name.contains("CASH S ALE")){
			name = name.replace("CASH S ALE","");
			name = name.trim();
		} else if (name.contains("CASH SAL E")){
			name = name.replace("CASH SAL E","");
			name = name.trim();
		} else if (name.contains("CA SH SALE")){
			name = name.replace("CA SH SALE","");
			name = name.trim();
		}else if (name.contains("CAS H SALE")){
			name = name.replace("CAS H SALE","");
			name = name.trim();
		}else if (name.contains("C ASH SALE")){
			name = name.replace("C ASH SALE","");
			name = name.trim();
		}
	}
	
	public void printCustomer(){
		System.out.println(cuno + " " + name);
	}
	

}

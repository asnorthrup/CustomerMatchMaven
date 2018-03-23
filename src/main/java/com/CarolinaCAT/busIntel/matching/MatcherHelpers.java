package com.CarolinaCAT.busIntel.matching;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class MatcherHelpers {
	/**
	 * Creates a composite score out of two non-exact matching matches of customer name and address
	 * @param addrScore score of address
	 * @param custNmScore score of name
	 * @return average of two matching scores rounded
	 */
	public static int aggrScore( double addrScore, double custNmScore ){
		int score = 0;
		//matching phone means definite match
		score = (int) Math.round( (addrScore + custNmScore) / 2);
		return score;
	}


	public static int getPhoneScore(String phoneDBS, String phoneCust) {
		if (phoneDBS != null && phoneCust != null){
			if ( phoneCust.equals( phoneDBS) ) { 
				return 100;
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	//TODO this should be more robust to check street and city/county if it exists
	public static int getAddressScore(String addressDBS, String zipCodeDBS,
		String addressCust, String zipCust) {
		if (addressDBS != null && addressCust != null){
			//TODO match score on address should be very high
			if (addressCust.toLowerCase().contains(" box ")){
				//if its a PO BOX, then need to match with a zip code
				String addrPlusZip = addressCust.trim() + " " + zipCust.trim();
				String DBSaddrPlusZip = addressDBS.trim() + " " + zipCodeDBS.trim();
				return FuzzySearch.ratio( addrPlusZip, DBSaddrPlusZip );
			}
		return FuzzySearch.ratio( addressCust, addressDBS );
		}
		return 0; //one of addresses was null
	}


	public static int getNameScore(String nameDBS, String nameCust) {
		String transNameDBS = nameDBS.trim().toUpperCase();
		String transNameCust = nameCust.trim().toUpperCase();
		if (nameDBS != null && nameCust != null){
			return FuzzySearch.ratio( transNameDBS, transNameCust );
		}
		return 0;
	}
}

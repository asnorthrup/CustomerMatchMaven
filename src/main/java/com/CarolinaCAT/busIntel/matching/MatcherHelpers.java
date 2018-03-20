package com.CarolinaCAT.busIntel.matching;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class MatcherHelpers {
	
	public static double aggrScore( double phoneScore, double addrScore, double custNmScore ){
		double score = 0;
		//matching phone means definite match
		if (phoneScore == 100){
			return 100;
		} else {
			score = ( addrScore > custNmScore ) ? addrScore : custNmScore;
		} 
		return score;
	}


	public static double getPhoneScore(String phoneDBS, String phoneCust) {
		// TODO Auto-generated method stub
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

	public static int getAddressScore(String addressDBS, String zipCodeDBS,
		String addressCust, String zipCust) {
		if (addressDBS != null && addressCust != null){
			//TODO match score on address should be very high
			if (addressCust.toLowerCase().contains(" box ")){
				//if its a PO BOX, then need to match with a zip code
				String addrPlusZip = addressCust + " " + zipCust;
				String DBSaddrPlusZip = addressDBS + " " + zipCodeDBS;
				Double addrScore;
				return FuzzySearch.ratio( addrPlusZip, DBSaddrPlusZip );
			}
		return FuzzySearch.ratio( addressCust, addressDBS );
		}
		return 0; //one of addresses was null
	}
}

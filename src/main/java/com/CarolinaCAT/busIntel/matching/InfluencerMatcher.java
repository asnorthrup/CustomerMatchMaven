package com.CarolinaCAT.busIntel.matching;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class InfluencerMatcher {
	//TODO when matching influencers, look at ZIP and area code of customer for probable matches
	HashMap<String, String> influencers;
	//result set should have an influencer and customer number
	//TODO maybe the has key needs to be a string of name plus zip code
	public InfluencerMatcher(ResultSet influencerQryResult){
		influencers = new HashMap<String, String>();
		try {
			while(influencerQryResult.next()){
				//key should be 3 things, zip code name and customer number
				influencers.put(influencerQryResult.getString(1),influencerQryResult.getString(2)); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Need to pass it a customer name and zip code witht hat customer name
	//returns a customer number
	/**
	 * Method uses the influencer name to lookup customer number to return
	 * @param influencerName string for the name of the influencer to check
	 * @return string for customer number, which needs to be checked against the customer list of objects and its zip code
	 */
	public String getCustomerByInfluencer(String influencerName){
		return influencers.get(influencerName);
		//TODO what if there are multiple influencers with same name
	}
	
}

package com.CarolinaCAT.busIntel.matching;

public class PotentialMatch {
	
	public String customerNum;
	public double matchScore;
	public String matchType;

	
	PotentialMatch(String custNum, double matchScore, String matchType){
		this.customerNum = custNum;
		this.matchScore = matchScore;
		this.matchType = matchType;
	}

}

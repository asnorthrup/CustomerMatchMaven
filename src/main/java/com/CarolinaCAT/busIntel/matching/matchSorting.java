package com.CarolinaCAT.busIntel.matching;

import java.util.Comparator;

public class matchSorting implements Comparator<PotentialMatch> {
	//TODO check how compare works for sort order, -1 vs 1 means what
	public int compare(PotentialMatch a, PotentialMatch b){
		//return negative means its before in the sort list, like an int
		if((a.matchScore - b.matchScore)>0){
			//a's match score is greater than b's
			return -1;
		} else if ((a.matchScore - b.matchScore)<0){
			return 1;
		}
		return 0;
	}
	
}

package com.CarolinaCAT.busIntel.matching;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Translators {
	
	private ArrayList<String> transList;
	private ArrayList<String> begList;
	private ArrayList<String> endList;
	
	public Translators(){
		ArrayList<String> transList = new ArrayList<String>();
		ArrayList<String> begList = new ArrayList<String>();
		ArrayList<String> endList = new ArrayList<String>();
		
		Scanner s;
		try {
			s = new Scanner(new File("/customerMatcher/DoingBusAsLookups"));
			while (s.hasNextLine()){
				endList.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	
	public String stripBeginning(String custName){
		
	}
	
	public String stripEndings(String custName){
		
	}
	
	
	public String modPOBox(String addr){
		
	}
}

package com.CarolinaCAT.busIntel.matching;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Translators {
	
	private List<StringPair> transList;
	private ArrayList<String> dbaList;
	private ArrayList<String> extraAtEndList;
	private ArrayList<String> poBoxList;
	
	/**
	 * Initialize translator
	 */
	public Translators(){
		transList = new ArrayList<StringPair>();
		dbaList = new ArrayList<String>();
		extraAtEndList = new ArrayList<String>();
		poBoxList = new ArrayList<String>();
		
		//read in for part only to keep at end
		Scanner s;
		try {
			s = new Scanner(new File("DoingBusAsLookups.txt"));
			while (s.hasNextLine()){
				dbaList.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//read in for part to strip off the end of customer name
		try {
			s = new Scanner(new File("RemoveEnding.txt"));
			while (s.hasNextLine()){
				extraAtEndList.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//read in for name translations to perform before matching
		try {
			s = new Scanner(new File("NameTranslations.txt"));
			while (s.hasNextLine()){
				String temp = s.nextLine().trim();
				int spaceCount = 0;
				for (char c : temp.toCharArray()) { //should only be two spaces in string
				    if (c == ' ') {
				         spaceCount++;
				    }
				}
				if (spaceCount == 2 && temp.contains(" TO ")){
					String[] arr = temp.split(" TO ");
					StringPair p = new StringPair(arr[0],arr[1]);
					transList.add(p);
				} else {
					//TODO note to user, wasn't read in because didn't match format
				}

			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//read in for PO box tranlations to perform before matching
		try {
			s = new Scanner(new File("POboxTranslations.txt"));
			while (s.hasNextLine()){
				poBoxList.add(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**This class means that there are things at the beginning that do not correspond to the name of the company.
	 * Most common example is doing business as "DBA" or some version.
	 * 
	 * @param custName
	 * @return string of just what customer name should be (after D/B/A - or the like)
	 */
	public String stripBeginning(String custName){
		//TODO what should index initially be set to?
		//Need to check various versions of this
		int index = -1 ;
		custName = custName.toUpperCase();
		for (String trans: dbaList){
			trans = trans.trim();
			if ( (index = custName.lastIndexOf(trans) ) != - 1){
				if( custName.length() > index + trans.length()){
					custName = custName.substring(index + trans.length()).trim();
					return custName;
				} else {
					custName = "No Company Name"; //if nothing listed after dba
					return custName;
				}
			}
		}
		return custName;
	}
	
	/**This class means that there are things at the end that do not correspond to the name of the company.
	 * Most common example is -Cash Sale or - CAS H SALE.
	 * 
	 * @param custName
	 * @return string of just what customer name should be (before Cash SAle - or the like)
	 */
	public String stripEndings(String custName){
		//TODO what should index initially be set to?
		//Need to check various versions of this
		int index = -1 ;
		custName = custName.toUpperCase();
		for (String trans: extraAtEndList){
			trans = trans.trim();
			if ( (index = custName.lastIndexOf(trans) ) != - 1){
				if( custName.length() > index ){
					custName = custName.substring(0, index);
					while(custName != null && !Character.isLetter(custName.charAt(custName.length() - 1))){
						custName = custName.substring(0, custName.length() - 1);
					}
					return custName;
				} else {
					custName = "No Company Name"; //if nothing listed after dba
					return custName;
				}
			}
		}
		return custName;
	}
	
	public String customerNameTranslations(String custName){
		custName = custName.toUpperCase();
		String [] arr = custName.split(" ");
		for(StringPair pairs: transList){
			//TODO this isn't right, need to check first, second, last words
			for (int i=0; i < arr.length; i++){
				if( arr[i].equals(pairs.first) ){
					arr[i] = pairs.second;
				}
			}
		}
		return String.join(" ", arr);
	}
	
	
	
	public String modPOBox(String addr){
		addr = addr.toUpperCase();
		for(String trans: poBoxList){
			if(addr.contains(trans)){
				addr = addr.replace(trans, "P.O. BOX");
			}
		}		
		return addr;
	}
	
}
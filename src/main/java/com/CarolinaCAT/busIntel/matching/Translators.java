package com.CarolinaCAT.busIntel.matching;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Translators {
	
	private ArrayList<String> transList;
	private ArrayList<String> dbaList;
	private ArrayList<String> extraAtEndList;
	
	public Translators(){
		ArrayList<String> transList = new ArrayList<String>();
		ArrayList<String> dbaList = new ArrayList<String>();
		ArrayList<String> extraAtEndList = new ArrayList<String>();
		
		//read in for part only to keep at end
		Scanner s;
		try {
			s = new Scanner(new File("/customerMatcher/DoingBusAsLookups"));
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
			s = new Scanner(new File("/customerMatcher/RemoveEnding"));
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
			s = new Scanner(new File("/customerMatcher/NameTranslations"));
			while (s.hasNextLine()){
				extraAtEndList.add(s.nextLine());
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
	}
	
	/**This class means that there are things at the end that do not correspond to the name of the company.
	 * Most common example is -Cash Sale or - CAS H SALE.
	 * 
	 * @param custName
	 * @return string of just what customer name should be (before Cash SAle - or the like)
	 */
	public String stripEndings(String custName){
		
	}
	
	
	public String modPOBox(String addr){
		
	}
}

int index;
name = name.toUpperCase();
if ( (index = name.lastIndexOf("DBA") ) != - 1){
	if( name.length() > index + 3){
		name = name.substring(index + 3).trim();
	} else {
		name = "No Company Name"; //if nothing listed after dba
	}
} else if ((index = name.lastIndexOf("D/B/A") ) != - 1){
	//System.out.println(name);
	if(name.length()> index + 6 ){
		name = name.substring(index + 6).trim(); //some records have D/B/A and then blank
	} else {
		name = "No Company Name";
	}
	
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

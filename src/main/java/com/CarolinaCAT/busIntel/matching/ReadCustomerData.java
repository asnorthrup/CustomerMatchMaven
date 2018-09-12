package com.CarolinaCAT.busIntel.matching;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.CarolinaCAT.busIntel.view.MatcherStart;
import com.CarolinaCAT.busIntel.view.NewProgressBar;
import com.CarolinaCAT.busIntel.view.ProgressBar;

import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Java Program to read an excel sheet and create customer matches
 * @author ANorthrup
 *
 */

/**Class to read in customer data from the data sources specified (and passed in) by the GUI.
 * The class returns a hash map of customer objects with a string as the key based on the customer number 
 * and customer object (CustomerObj) as the value.
 */
public class ReadCustomerData extends SwingWorker<HashMap<String, CustomerObj>,Void> {	
	//from the initialization method
	/*String for the dbs ODBC connection name*/
	private String dBSOdbcConn;
	/*String for the dbs schema name*/
	private String dealerSchema;
	/*String for the additional ms access database path name*/
	private String dbPath;
	/*boolean to indicate only get prospects from ms access*/
	private Boolean onlyProspects;
	private int CustomerEst;
//	private String inputFileNameAndPath;
//	private String outputFileNameAndPath;
//	private ProgressBar progBarFrame;  //may not need
//	private int[] inputs;
//	private String tabName;
	private Translators translator;
	
	/*hash map of customers from the databases*/
	private HashMap<String, CustomerObj> ourCustomers;
	
	/**
	 * Constructor for the reading of customers class, this class initiates the matching
	 * @param inputFileNameAndPath as string for the xlsx file to read in customers from
	 * @param outputFileNameAndPath as a string for the file (that will be written to xlsx) in the same path as the input file
	 * @param progBarFrame this is the jFrame that gets updated to show progress of the matcher
	 * @param inputs an array of ints to indicate which columns user indicated key values are in
	 * @param tabName a string to indicate what the name of the tab the data is in
	 * @param minNameScore int send in by minimum name spinner set by user
	 * @throws Exception
	 */
	public ReadCustomerData(String dBSOdbcConn, String dealerSchema, String dbPath, Boolean onlyProspects, 
			int CustomerEst,String inputFileNameAndPath, String outputFileNameAndPath, NewProgressBar progBarFrame, 
			int[] inputs, String tabName, int minNameScore, Translators translator) throws Exception
	{
		//Set variables
		this.dBSOdbcConn=dBSOdbcConn;
		this.dealerSchema=dealerSchema;
		this.dbPath=dbPath;
		this.onlyProspects=onlyProspects;
		this.CustomerEst=CustomerEst;
//		this.inputFileNameAndPath=inputFileNameAndPath;
//		this.outputFileNameAndPath=outputFileNameAndPath;
//		this.progBarFrame=progBarFrame;  //may not need
//		this.inputs=inputs;
//		this.tabName=tabName;
		this.translator = translator;

		
	}

	
	/** This class, a swing worker, returns a hashmap of customers from a DBS query and a access database if provided
	 * (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected HashMap<String, CustomerObj> doInBackground() throws Exception{
		String queryCode  = null;
		DBSquery customersQuery = null;
		AccessProspectsQuery prospectsQuery = null;
		//DBS query setup
		if (dBSOdbcConn != null && dealerSchema != null){ //TODO need to get dealer specific CIPNAME and CIPLADR location
			queryCode = "SELECT CIP.CUNO, CIP.CUNM, CIP.CUNM2, CIP.PRCUNO, CIP.PHNO, "
				+ "CASE WHEN length(trim(CIP.CUADD2)) > 0 THEN trim(CIP.CUADD2) WHEN length(trim(CIP.CUADD3)) > 0 THEN trim(CIP.CUADD3) "
				+ "WHEN length(trim(CIP.CUADD1)) > 0 THEN trim(CIP.CUADD1) Else 'No Bill Adr' END AS BILL_ADR, CIP.ZIPCD9, "
				+ "CASE WHEN length(trim(PHYS.CUADD2)) > 0 THEN trim(PHYS.CUADD2) WHEN length(trim(PHYS.CUADD3)) > 0 THEN trim(PHYS.CUADD3) "
				+ "WHEN length(trim(PHYS.CUADD1)) > 0 THEN trim(PHYS.CUADD1) ELSE 'No Phys Adr' END AS PHYS_ADR, PHYS.ZIPCD9 AS PHYSZIP "
				+ "FROM " + dealerSchema + ".CIPNAME0 CIP LEFT JOIN " + dealerSchema + ".CIPLADR0 PHYS ON (CIP.CUNO = PHYS.CUNO) "
				+ "WHERE CIP.CUNO NOT LIKE ('I%')";
			try{
				customersQuery = new DBSquery(queryCode, dBSOdbcConn);
			} catch (Exception e){
				
			}
		}
		//MS access database query setup
		String qry = null;
		if (dbPath != null && !dbPath.equals("No File Choosen")){
			if( onlyProspects ){
				qry = "SELECT SaleslinkCustomers.* FROM SaleslinkCustomers WHERE (((SaleslinkCustomers.CustomerNo) Like '$%'))";
			} else {
				qry = "SELECT SaleslinkCustomers.* FROM SaleslinkCustomers";
			}		
			prospectsQuery = new AccessProspectsQuery( qry, dbPath );
			//prospect results: Custnum, Cust name, phys addr, bill addr, addr3, city, zip, phone
		}
		
		int approxDBSCustomers = 70000;
		if ( CustomerEst > 0 ){
			approxDBSCustomers = CustomerEst;
		}
		
		int numReadIn = 1;
		int custHashSize = (int) (1.3 * approxDBSCustomers); //recommended size is number of expected / .75 for hash
		ourCustomers = new HashMap<String, CustomerObj>(custHashSize);
		//read results of DBS query
		if(customersQuery != null){
			ResultSet result = customersQuery.getResultSet();
			//maybe the .next should be in try/catch
			while(result.next()){
				if(isCancelled()){break;}
				//create the customer object
				CustomerObj co = new CustomerObj(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getString(8), result.getString(9));
				//use the translator to modify customer name and customer address if it has PO Box
				if(co.name != null){
					co.name_translated = translator.customerNameTranslations(co.name);
					co.name_translated = translator.stripBeginning(co.name_translated);
					co.name_translated = translator.stripEndings(co.name_translated);
				}
				if(co.billAddress != null && !co.billAddress.equals("No Bill Adr")){
					co.billAddress = translator.modPOBox(co.billAddress);
				}
				if(co.physAddress != null && !co.physAddress.equals("No Phys Adr")){
					co.physAddress = translator.modPOBox(co.physAddress);
				}
				//add to hashmap of customer objects for our customer
				ourCustomers.put(co.cuno, co);
				//give an indicator to user as to how far along in reading goes
				if((numReadIn % 1000) == 0){
					double pct = ((double)numReadIn / approxDBSCustomers) * 100;
					System.out.println("Approx " + pct + "% DBS loaded");
					if( (int) pct > 0 && (int) pct < 100 ){
						setProgress((int) pct);
					}
					
				}
				numReadIn++;
			}
		}
		//read results of ms access query
		if(prospectsQuery != null){
			//get prospect result set and parse from the MS Access query of prospects (already embedded in the Access Prospects query object
			ResultSet prospectRS = prospectsQuery.getResultSet();	
			while(prospectRS.next()){
				if(isCancelled()){break;}
				CustomerObj co = new CustomerObj(prospectRS.getString(1), prospectRS.getString(2), null, null, prospectRS.getString(8),prospectRS.getString(4), prospectRS.getString(7), prospectRS.getString(3), prospectRS.getString(7));
				//perform translations on the customer object
				if(co.name != null){
					co.name_translated = translator.customerNameTranslations(co.name);
					co.name_translated = translator.stripBeginning(co.name_translated);
					co.name_translated = translator.stripEndings(co.name_translated);
				}
				if(co.billAddress != null && !co.billAddress.equals("No Bill Adr")){
					co.billAddress = translator.modPOBox(co.billAddress);
				}
				if(co.physAddress != null && !co.physAddress.equals("No Phys Adr")){
					co.physAddress = translator.modPOBox(co.physAddress);
				}
			
				//add to hashmap of customer objects for our customer
				ourCustomers.put(co.cuno, co);
				if((numReadIn % 1000) == 0){
					double pct = ((double)numReadIn / approxDBSCustomers) * 100;
					System.out.println("Approx " + pct + "% DBS loaded") ;
					if( (int) pct > 0 && (int) pct < 100 ){
						setProgress((int) pct);
					}
				}
				numReadIn++;
			}
		}
		if(!isCancelled()){
			setProgress(100);
			System.out.println("100% DBS loaded!") ;
		}
		return ourCustomers;
	}	
	
}

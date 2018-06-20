package com.CarolinaCAT.busIntel.matching;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Facet;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.FacetImpl;

import com.CarolinaCAT.busIntel.view.MatcherStart;
import com.CarolinaCAT.busIntel.view.ProgressBar;

public class ExcelWorkbook {
	private long lastRowIndex;
	private int lastColIndex;
	public ArrayList<excelCustomerObj> customersInWB;
	//Excel workbook with data and where matches will be added
	public XSSFWorkbook wb;
	private File file;
	private OPCPackage pkg;

	/**
	 * Constructor that takes an excel path and creates an object called an excel workbook.
	 * @param path to xlsx file as string for where to find the file with potential matches
	 * @param inputs 
	 * @param tabName 
	 */
	

	public ExcelWorkbook(String path, ProgressBar progBarFrame, int[] inputs, String tabName, Translators translator){
		//check that this is a xlsx file
		if(path.trim().substring(path.trim().length() - 1) != "x"){
			//TODO figure out throwing an invalid file type exception and tell user it must be excel xlsx file
		}
		file = new File(path);
		//opPackage = null;
		FileInputStream excelFile = null;
		try {
			pkg = OPCPackage.open(file);
		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Sheet myExcelSheet = null;
		try {
			//TODO do I need to close file input stream?
			//excelFile = new FileInputStream(file);
			
			//opPackage = OPCPackage.open(file.getAbsolutePath());
			//wb = new XSSFWorkbook(excelFile);
			boolean wsExists = false;
			wb = new XSSFWorkbook(pkg);
		    if (wb.getNumberOfSheets() != 0) {
		        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
		           if (wb.getSheetName(i).equals(tabName)) {
		                wsExists = true;
		            }
		        }
		    }

			if (wsExists){
				myExcelSheet = wb.getSheet(tabName);
			} else {

			}

		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} //finally {
//			if (excelFile != null) {
//				try {
//					excelFile.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		populateCustomers(myExcelSheet, progBarFrame, inputs, translator);
		try {
			pkg.close();
		} catch (IOException e) {
			System.out.println("File not closeable because open by another program");
		}
	}	
	
	private void populateCustomers(Sheet myExcelSheet, ProgressBar progBarFrame, int[] inputs, Translators translator) {
		//array of inputs is passed in to show which column each value is in for the excel sheet
		int startRow = inputs[0]; //rows are 0 based index, user entered as 1 based
		int colCustName = inputs[1]; 
		int colCustName2 = inputs[2];
		int colCustInfluencer = inputs[3];
		int colCustInfluencer2 = inputs[4];
		int colCustInfluencer3 = inputs[5];
		int colCustInfluencer4 = inputs[6];
		int colCustAddr = inputs[7];
		int colCustAddr2 = inputs[8];
		int colCustPhone = inputs[9];
		int colCustZip = inputs[10];
		int colCustZip2 = inputs[11];
		
		int numInSheet = myExcelSheet.getLastRowNum();
		int physInSheet = myExcelSheet.getPhysicalNumberOfRows();
		int physFirstInSheet = myExcelSheet.getFirstRowNum();
		System.out.println(myExcelSheet.getSheetName());
		customersInWB = new ArrayList<excelCustomerObj>(numInSheet);
		
		//set up to process sheet
		//note the first row will likely be header, skip 1 with iterator
		//TODO make sure matching goes all the way to the end of the excel sheet
		for (int i= startRow  - 1; i<=numInSheet; i++){
			if((i % 10) == 0){
				double pct = ((double)i / numInSheet) * 100;
				System.out.println("Read in"+ pct + "of Excel file");
				progBarFrame.setPBReadCusts((int) pct);
			}
			Row row = myExcelSheet.getRow( i );
			String nm = null;
			String inf = null;
			String inf2 = null;
			String addr = null;
			String addr2 = null;
			String zipCode = null;
			String zipCode2 = null;
			String ph = null;
			if (row == null){
				//whole row is blank - this shouldn't exist
			} else {
				//read in customer name
				Cell companyName = null;
				Cell companyName2 = null;
				if(colCustName != -1 && colCustName2 == -1){ //only a first
					companyName = row.getCell(colCustName);
					if(companyName != null){
						nm = companyName.getStringCellValue();
					}
				} else if (colCustName != -1 && colCustName2 != -1) {
					companyName = row.getCell(colCustName);
					companyName2 = row.getCell(colCustName2);
					if(companyName != null && companyName2 != null){
						nm = companyName.getStringCellValue() + companyName2.getStringCellValue();
					} else if (companyName != null && companyName2 == null) {
						nm = companyName.getStringCellValue();
					} else if (companyName == null && companyName2 != null) {
						nm = companyName2.getStringCellValue();
					}
				}

				//TODO once implement influencers, then we need to check all the influencers and first/last name
				if(colCustInfluencer != -1){ //only if we are using influencer
					Cell cinf = row.getCell(colCustInfluencer);
					if (cinf != null){
						inf = cinf.getStringCellValue();
					}
				}
				if(colCustAddr != -1){
					Cell caddr = row.getCell(colCustAddr);
					if (caddr != null){
						addr = caddr.getStringCellValue();
					}
				}
				if(colCustAddr2 != -1){
					Cell caddr2 = row.getCell(colCustAddr2);
					if (caddr2 != null){
						addr2 = caddr2.getStringCellValue();
					}
				}
				if(colCustZip != -1){
					Cell czip = row.getCell(colCustZip);
					if (czip != null){
						zipCode = czip.getStringCellValue();
					}
				}
				if(colCustZip2 != -1){
					Cell czip2 = row.getCell(colCustZip2);
					if (czip2 != null){
						zipCode = czip2.getStringCellValue();
					}
				}
				if(colCustPhone != -1){
					Cell cph = row.getCell(colCustPhone);
					if (cph != null){
						ph = cph.getStringCellValue();
					}
				}
			}
			//check if nm, inf, addr or ph has info in it
			if (nm != null || inf != null || addr !=null || addr2 !=null || ph != null){
				//create an excel customer object and add it to arraylist
				//i+1 is because row indexing starts at 0
				excelCustomerObj cust = new excelCustomerObj(null, nm, null, null, addr, zipCode, addr2, zipCode2, ph, i + 1);
				if(cust.name != null){
					cust.name_translated = translator.customerNameTranslations(cust.name);
					cust.name_translated = translator.stripBeginning(cust.name_translated);
					cust.name_translated = translator.stripEndings(cust.name_translated);
				}
				if(cust.billAddress != null){
					cust.billAddress = translator.modPOBox(cust.billAddress);
				}
				if(cust.physAddress != null){
					cust.physAddress = translator.modPOBox(cust.physAddress);
				}
				customersInWB.add(cust);
			}
		}
		progBarFrame.setPBReadCusts(100);
		//completed read in columns in workbook
	}
	
	/**
	 * Populate a worksheet in the original workbook with the highest rated match. Before calling this function, the customers should
	 * have been matched with a DBS customer (if one exists) and assigned a specific match score.
	 * @param ourCustomers 
	 */
	//TODO finish this logic, still need to output the excel customer snad the top match to excel. Also, need to write test cases
	public void addSheetOfMatches(String path, HashMap<String, CustomerObj> ourCustomers){
		XSSFWorkbook outputBook = new XSSFWorkbook();
		//may not be able to name by passing string in
		Sheet matchesSheet = outputBook.createSheet("DBSmatches");

		
		//create header for excel file
		setHeaders(matchesSheet);
		int rowCounter = 1;
		for( excelCustomerObj excelCust : customersInWB ){
			//Helper for checking how fast writer is working
//			if((rowCounter % 10) == 0){
//				double pct = ((double)rowCounter / customersInWB.size()) * 100;
//				System.out.println("Matching"+ pct + "Pct Complete");
//			}
//			
			Row r = null;
			r = matchesSheet.createRow( rowCounter );
			if (excelCust.potenDBSMatches.isEmpty()){
				//no match, just copy Excel Customer data
				setExcelCustToRow(r, excelCust, null, null);
			} else {
				//sort all the potential matches by match score
				if ( excelCust.potenDBSMatches.size() > 1 ){
					Collections.sort(excelCust.potenDBSMatches,new matchSorting());
				}
				PotentialMatch bestPotentialMatch = excelCust.potenDBSMatches.get(0);
				
				//populate excel sheet 'matchesSheet' with customer and matches, use the potential customer num to get out of dbs customer hash
				setExcelCustToRow(r, excelCust, ourCustomers.get(bestPotentialMatch.customerNum), bestPotentialMatch);
			}
			rowCounter++;
		}
		
		//TODO KEEP REVIEWING/FOLLOWING CODE FROM HERE
		//create an output stream for writing
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(path);
			outputBook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e1) {
			// if file cannot be found, alert user
			JOptionPane.showMessageDialog(null, 
                    "File not available - Please close file and restart", 
                    "Cannot Write Warning", 
                    JOptionPane.WARNING_MESSAGE);
			//TODO remove stack trace
			e1.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	//topMatch is the customer object that has been identified as the best object, need to use bestpotential match type to figure out if this is phys addr or billing
	private void setExcelCustToRow(Row r, excelCustomerObj xlCust, CustomerObj topMatch, PotentialMatch bestPotentialMatch){
		String tmpPhone = null;
		if (topMatch == null){
			Cell c = r.createCell(0);
			//c.setCellType(STRING);
			c.setCellValue(xlCust.name);
			c = r.createCell(1);
			c.setCellValue(xlCust.billAddress);
			c = r.createCell(2);
			c.setCellValue(xlCust.billZipCode);
			c = r.createCell(3);
			if(xlCust.phone != null && xlCust.phone.length()>6){
				tmpPhone = xlCust.phone.substring(0,3)+"-"+xlCust.phone.substring(3,6)+"-"+xlCust.phone.substring(6);
				c.setCellValue(tmpPhone);
			}
		} else {
			Cell c = r.createCell(0);
			c.setCellValue(xlCust.name);
			c = r.createCell(1);
			c.setCellValue(xlCust.billAddress);
			c = r.createCell(2);
			c.setCellValue(xlCust.billZipCode);
			c = r.createCell(3);
			if(xlCust.phone != null && xlCust.phone.length()>6){
				tmpPhone = xlCust.phone.substring(0,3)+"-"+xlCust.phone.substring(3,6)+"-"+xlCust.phone.substring(6);
				c.setCellValue(tmpPhone);
			}
			c = r.createCell(4);
			c.setCellValue(topMatch.cuno);
			c = r.createCell(5);
			c.setCellValue(topMatch.name);
			c = r.createCell(6);
			if(bestPotentialMatch.matchType.equals("Physical Address")){
				c.setCellValue(topMatch.physAddress);
			} else { //anything other than physical match address, output billing address
				c.setCellValue(topMatch.billAddress);
			}
			c = r.createCell(7);
			//Determine which address and zip to use, physical or billing
			if(bestPotentialMatch.matchType.equals("Physical Address")){
				c.setCellValue(topMatch.physZipCode);
			} else { //anything other than physical match address, output billing address
				c.setCellValue(topMatch.billZipCode);
			}
			c = r.createCell(8);
			if(topMatch.phone != null && topMatch.phone.length() == 10){
				tmpPhone = topMatch.phone.substring(0,3)+"-"+topMatch.phone.substring(3,6)+"-"+topMatch.phone.substring(6);
				c.setCellValue(tmpPhone);
			}
			c = r.createCell(9);
			c.setCellValue(topMatch.parent);
			c = r.createCell(10);
			c.setCellValue(bestPotentialMatch.matchType);
			c = r.createCell(11);
			c.setCellValue(bestPotentialMatch.matchScore);
		}
	}
	
	//helper method to set up headers
	private void setHeaders(Sheet matchSheet){		
		
		Row r = null;
		r = matchSheet.createRow(0);
		Cell hdr = r.createCell(0);
		hdr.setCellValue("Excel CustomerName");
		hdr = r.createCell(1);
		hdr.setCellValue("Excel CustomerAddress");
		hdr = r.createCell(2);
		hdr.setCellValue("Excel CustomerZipCode");
		hdr = r.createCell(3);
		hdr.setCellValue("Excel CustomerPhone");
		hdr = r.createCell(4);
		hdr.setCellValue("DBS Customer Num");
		hdr = r.createCell(5);
		hdr.setCellValue("DBS Customer Name");
		hdr = r.createCell(6);
		hdr.setCellValue("DBS Customer Address");		
		hdr = r.createCell(7);
		hdr.setCellValue("DBS Customer ZipCode");
		hdr = r.createCell(8);
		hdr.setCellValue("DBS Customer Phone");
		hdr = r.createCell(9);
		hdr.setCellValue("DBS Parent Customer Num");
		//eventually may do something with influencer at this point
		hdr = r.createCell(10);
		hdr.setCellValue("DBS Match Type");
		hdr = r.createCell(11);
		hdr.setCellValue("DBS Customer MatchScore");
	}

	//TODO are these used?
	/****Start of SETTERS and GETTERS*****/
	public long getLastRowIndex() {
		return lastRowIndex;
	}

	public void setLastRowIndex(long lastRowIndex) {
		this.lastRowIndex = lastRowIndex;
	}

	public int getLastColIndex() {
		return lastColIndex;
	}

	public void setLastColIndex(int lastColIndex) {
		this.lastColIndex = lastColIndex;
	}
	
	//probably want last column and last row...
	

}

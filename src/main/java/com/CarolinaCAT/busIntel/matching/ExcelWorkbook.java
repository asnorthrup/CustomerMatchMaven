package com.CarolinaCAT.busIntel.matching;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWorkbook {
	private long lastRowIndex;
	private int lastColIndex;
	public ArrayList<excelCustomerObj> customersInWB;
	//Excel workbook with data and where matches will be added
	public XSSFWorkbook wb;
	//constructor for the Excel workbook class
	public ExcelWorkbook(String path){
		File file = new File(path);
		OPCPackage opPackage = null;
		try {
			opPackage = OPCPackage.open(file.getAbsolutePath());
		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		wb = null;
		//if(fp != null){
		try {
			wb = new XSSFWorkbook(opPackage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet myExcelSheet = wb.getSheet("Results");
		
		populateCustomers(myExcelSheet);
	}	
	
	private void populateCustomers(Sheet myExcelSheet) {
		int startRow = 1; //rows are 0 based index
		int colCustName = 0; //col A
		int colCustInfluencer = 1; //col B
		int colCustAddr = 3; //col D
		int colCustPhone = 10; //col k
		int colCustZip = 6; //col G
		
		//TODO set up a column for a user to tell the program where paste the values of dbs customers in
		//TODO when writing back, do you select the parent or the customer?
		int numInSheet = myExcelSheet.getLastRowNum();
		customersInWB = new ArrayList<excelCustomerObj>(numInSheet);
		
		//set up to process sheet
		//note the first row will likely be header, skip 1 with iterator
		for (int i= startRow; i<numInSheet; i++){
			Row row = myExcelSheet.getRow( i );
			String nm = null;
			String inf = null;
			String addr = null;
			String zipCode = null;
			String ph = null;
			if (row == null){
				//whole row is blank
			} else {
				Cell cnm = row.getCell(colCustName);
				if(cnm != null){
					nm = cnm.getStringCellValue();
				}
				Cell cinf = row.getCell(colCustInfluencer);
				if (cinf != null){
					inf = cinf.getStringCellValue();
				}
				Cell caddr = row.getCell(colCustAddr);
				if (caddr != null){
					addr = caddr.getStringCellValue();
				}
				Cell czip = row.getCell(colCustZip);
				if (czip != null){
					zipCode = czip.getStringCellValue();
				}
				Cell cph = row.getCell(colCustPhone);
				if (cph != null){
					ph = cph.getStringCellValue();
				}
			}
			//check if nm, inf, addr or ph has info in it
			if (nm != null || inf != null || addr !=null || ph != null){
				//create an excel customer object and add it to arraylist
				//TODO what is all of the nulls, check on this
				excelCustomerObj cust = new excelCustomerObj(null, nm, null, null, addr, zipCode, ph, i);
				customersInWB.add(cust);
			}
		}
		//completed read in columns in workbook
	}
	
	/**
	 * Populate a worksheet in the original workbook with the highest rated match. Before calling this function, the customers should
	 * have been matched with a DBS customer (if one exists) and assigned a specific match score.
	 */
	//TODO finish this logic, still need to output the excel customer snad the top match to excel. Also, need to write test cases
	public void addSheetOfMatches(){
		//may not be able to name by passing string in
		Sheet matchesSheet = wb.createSheet("DBSmatches");
		Row r = null;
		Cell c =null;
		int rowCounter = 0;
		for( excelCustomerObj excelCust : customersInWB){
			if (excelCust.potenDBSMatches.size() > 0){
				//for this excelCust, check for highest match score
				Collections.sort(excelCust.potenDBSMatches,new matchSorting());				
			} else {
				//else there aren't any matches
			}

			//populate excel sheet 'matchesSheet' with customer and matches
			r = matchesSheet.createRow(rowCounter);
			c = r.createCell(0);
			c.setCellValue(/*TODO*/);
			rowCounter++;
		}
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

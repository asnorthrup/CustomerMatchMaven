package com.CarolinaCAT.busIntel.matching;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
	private OPCPackage opPackage;

	/**
	 * Constructor that takes an excel path and creates an object called an excel workbook.
	 * @param path to xlsx file as string for where to find the file with potential matches
	 * @param inputs 
	 */
	public ExcelWorkbook(String path, ProgressBar progBarFrame, int[] inputs){
		//check that this is a xlsx file
		if(path.trim().substring(path.trim().length() - 1) != "x"){
			//TODO figure out throwing an invalid file type exception and tell user it must be excel xlsx file
		}
		file = new File(path);
		opPackage = null;
		try {
			FileInputStream excelFile = new FileInputStream(file);
			//opPackage = OPCPackage.open(file.getAbsolutePath());
			wb = new XSSFWorkbook(excelFile);
			//The name of the worksheet in the file
			//TODO need to ask user for sheet name
			Sheet myExcelSheet = wb.getSheet("Results");
			populateCustomers(myExcelSheet, progBarFrame, inputs);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}	
	
	private void populateCustomers(Sheet myExcelSheet, ProgressBar progBarFrame, int[] inputs) {
		//array of inputs is passed to 
		//TODO must check if these are equal to -1 when setting up excel customer obs
		//TODO start row can't be null
		int startRow = inputs[0]; //rows are 0 based index
		int colCustName = inputs[1]; //col A
		int colCustInfluencer = inputs[2]; //col B
		int colCustAddr = inputs[3]; //col D
		int colCustPhone = inputs[4]; //col k
		int colCustZip = inputs[5]; //col G
		
		int numInSheet = myExcelSheet.getLastRowNum();
		customersInWB = new ArrayList<excelCustomerObj>(numInSheet);
		
		//set up to process sheet
		//note the first row will likely be header, skip 1 with iterator
		for (int i= startRow; i<=numInSheet; i++){
			if((i % 10) == 0){
				double pct = ((double)i / numInSheet) * 100;
				System.out.println("Read in"+ pct + "of Excel file");
				progBarFrame.setPBReadCusts((int) pct);
			}
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
				if(colCustZip != -1){
					Cell czip = row.getCell(colCustZip);
					if (czip != null){
						zipCode = czip.getStringCellValue();
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
			if (nm != null || inf != null || addr !=null || ph != null){
				//create an excel customer object and add it to arraylist
				//TODO what is all of the nulls, check on this
				//i+1 is because row indexing starts at 0
				excelCustomerObj cust = new excelCustomerObj(null, nm, null, null, addr, zipCode, ph, i + 1);
				customersInWB.add(cust);
			}
		}
		progBarFrame.setPBReadCusts(100);
		//completed read in columns in workbook
	}
	
	/**
	 * Populate a worksheet in the original workbook with the highest rated match. Before calling this function, the customers should
	 * have been matched with a DBS customer (if one exists) and assigned a specific match score.
	 */
	//TODO finish this logic, still need to output the excel customer snad the top match to excel. Also, need to write test cases
	public void addSheetOfMatches(String path){
		XSSFWorkbook outputBook = new XSSFWorkbook();
		//may not be able to name by passing string in
		Sheet matchesSheet = outputBook.createSheet("DBSmatches");

		
		//create header for excel file
		setHeaders(matchesSheet);
		int rowCounter = 1;
		for( excelCustomerObj excelCust : customersInWB){
			//Helper for checking how fast writer is working
//			if((rowCounter % 10) == 0){
//				double pct = ((double)rowCounter / customersInWB.size()) * 100;
//				System.out.println("Matching"+ pct + "Pct Complete");
//			}
//			
			Row r = null;
			r = matchesSheet.createRow(rowCounter);
			if (excelCust.potenDBSMatches.isEmpty()){
				//no match, just copy Excel Customer data
				setExcelCustToRow(r, excelCust, null);
								
			} else {
				if (excelCust.potenDBSMatches.size() > 1){
					Collections.sort(excelCust.potenDBSMatches,new matchSorting());
				}
				CustomerObj bestMatch = excelCust.potenDBSMatches.get(0);
				//populate excel sheet 'matchesSheet' with customer and matches
				setExcelCustToRow(r, excelCust, bestMatch);
			}
			rowCounter++;
		}
	        

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
		//OLD CODE
//		try {
//			fileOut = new FileOutputStream(file);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		
		//TODO need to figure out how to save, the sheet isn't getting added to the workbook
//		if ( fileOut != null){
//			try {
//				wb.write(fileOut);
//				opPackage.close();
//				fileOut.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				fileOut.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}
	
	private void setExcelCustToRow(Row r, excelCustomerObj xlCust, CustomerObj topMatch){
		String tmpPhone = null;
		if (topMatch == null){
			Cell c = r.createCell(0);
			//c.setCellType(STRING);
			c.setCellValue(xlCust.name);
			c = r.createCell(1);
			c.setCellValue(xlCust.address);
			c = r.createCell(2);
			c.setCellValue(xlCust.zipCode);
			c = r.createCell(3);
			if(xlCust.phone != null && xlCust.phone.length()>6){
				tmpPhone = xlCust.phone.substring(0,3)+"-"+xlCust.phone.substring(3,6)+"-"+xlCust.phone.substring(6);
				c.setCellValue(tmpPhone);
			}
		} else {
			Cell c = r.createCell(0);
			c.setCellValue(xlCust.name);
			c = r.createCell(1);
			c.setCellValue(xlCust.address);
			c = r.createCell(2);
			c.setCellValue(xlCust.zipCode);
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
			c.setCellValue(topMatch.address);
			c = r.createCell(7);
			c.setCellValue(topMatch.zipCode);
			c = r.createCell(8);
			if(topMatch.phone != null && topMatch.phone.length() == 10){
				tmpPhone = topMatch.phone.substring(0,3)+"-"+topMatch.phone.substring(3,6)+"-"+topMatch.phone.substring(6);
				c.setCellValue(tmpPhone);
			}
			c = r.createCell(9);
			c.setCellValue(topMatch.parent);
			c = r.createCell(10);
			c.setCellValue(topMatch.matchType);
			c = r.createCell(11);
			c.setCellValue(topMatch.matchScore);
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

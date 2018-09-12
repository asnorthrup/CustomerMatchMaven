package com.CarolinaCAT.busIntel.matching;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OutputWorkbook {
	/**
	 * Populate a worksheet in the original workbook with the highest rated match. Before calling this function, the customers should
	 * have been matched with a DBS customer (if one exists) and assigned a specific match score.
	 * @param ourCustomers 
	 * @throws IOException 
	 */
	//TODO finish this logic, still need to output the excel customer snad the top match to excel. Also, need to write test cases
	public OutputWorkbook(String path, HashMap<String, CustomerObj> ourCustomers, ArrayList<excelCustomerObj> customersInWB) throws IOException{
		XSSFWorkbook outputBook = new XSSFWorkbook();
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
			throw new FileNotFoundException("File not available, close file if open");
		} catch (IOException e){
			throw new IOException("File not available, close file if open");
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

}

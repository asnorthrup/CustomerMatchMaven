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
import javax.swing.SwingWorker;

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
import com.CarolinaCAT.busIntel.view.NewProgressBar;
import com.CarolinaCAT.busIntel.view.ProgressBar;

//Probably return excel customer obj
public class ExcelWorkbook{
	private long lastRowIndex;
	private int lastColIndex;
	private ArrayList<excelCustomerObj> customersInWB;

	//Excel workbook with data and where matches will be added
	public XSSFWorkbook wb;
	private File file;
	private OPCPackage pkg;
	
	//set input variables
	private String path;
	private int[] inputs;
	private String tabName;
	private Translators translator;

	/**
	 * Constructor that takes an excel path and creates an object called an excel workbook.
	 * @param path to xlsx file as string for where to find the file with potential matches
	 * @param inputs 
	 * @param tabName 
	 */
	

	public ExcelWorkbook(String path, int[] inputs, String tabName, Translators translator){
		this.path = path;
		this.inputs = inputs;
		this.tabName = tabName;
		this.translator = translator;
		readInData();
	}	
	
	private void readInData() {
		// TODO Auto-generated method stub
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
		populateCustomers(myExcelSheet, inputs, translator);
		try {
			pkg.close();
		} catch (IOException e) {
			System.out.println("File not closeable because open by another program");
		}
	}	
	
	private void populateCustomers(Sheet myExcelSheet, int[] inputs, Translators translator) {
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
		//completed read in columns in workbook
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
	/**
	 * @return the customersInWB
	 */
	public ArrayList<excelCustomerObj> getCustomersInWB() {
		return customersInWB;
	}

	

}

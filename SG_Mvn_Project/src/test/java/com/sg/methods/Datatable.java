package com.sg.methods;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import com.sg.driver.DriverScript;

public class Datatable extends DriverScript{
	/*************************************
	 * Method Name	: getExcelTestData()
	 * Purpose		: to read the test data from the module specific excel files
	 * 
	 *************************************/
	public Map<String, String> getExcelTestData(String moduleName, String sheetName, String logicalName){
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row1 = null;
		Row row2 = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Map<String, String> objData = null;
		Calendar cal = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		String sKey = null;
		String sValue = null;
		int rowNum = 0;
		int colNum = 0;
		int dataRoWNum = 0;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\"+moduleName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeReport(null, "Fail", "Failed to find the '"+sheetName+"' sheet");
				Assert.fail("Failed to find the '"+sheetName+"' sheet");
			}
			
			
			//find the presence of the given logicalName in the first column
			rowNum = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rowNum; r++) {
				row1 = sh.getRow(r);
				cell1 = row1.getCell(0);
				if(cell1.getStringCellValue().equalsIgnoreCase(logicalName)) {
					dataRoWNum = r;
					break;
				}
			}
			
			
			//If logicalName is present, then read columns as Key & data as values
			if(dataRoWNum > 0) {
				row1 = sh.getRow(0);
				row2 = sh.getRow(dataRoWNum);
				
				colNum = row1.getPhysicalNumberOfCells();
				for(int c=0; c<colNum; c++) {
					cell1 = row1.getCell(c);
					cell2 = row2.getCell(c);
					
					sKey = cell1.getStringCellValue();
					
					//Format the cell2 data
					if(cell2==null || cell2.getCellType()==CellType.BLANK) {
						sValue = "";
					}
					else if(cell2.getCellType()==CellType.BOOLEAN) {
						sValue = String.valueOf(cell2.getBooleanCellValue());
					}
					else if(cell2.getCellType()==CellType.STRING) {
						sValue = cell2.getStringCellValue();
					}
					else if(cell2.getCellType()==CellType.NUMERIC) {
						if(DateUtil.isCellDateFormatted(cell2) == true) {
							double dt = cell2.getNumericCellValue();
							cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));
							
							//If day is <10, then add zero as prefix
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							//If month is <10, then add zero as prefix
							if((cal.get(Calendar.MONTH)+1) < 10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							sYear = String.valueOf(cal.get(Calendar.YEAR));
							sValue = sDay + "/" + sMonth + "/" + sYear;
						}else {
							sValue = String.valueOf(cell2.getNumericCellValue());
						}
					}
					objData.put(sKey, sValue);
				}
				return objData;
			}else {
				reports.writeReport(null, "Fail", "Failed to find the logical name '"+logicalName+"' in the TestData excel sheet");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in the 'getExcelTestData()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeReport(null, "Exception", "Assertion Exception in 'getExcelTestData()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell1 = null;
				cell2 = null;
				row1 = null;
				row2 = null;
				sh = null;
				wb.close();
				wb = null;
				cal = null;
				sDay = null;
				sMonth = null;
				sYear = null;
				sKey = null;
				sValue = null;
			}catch(Exception e) {
				return null;
			}
		}
	}
	
	
	
	/*************************************
	 * Method Name	: getRowCout()
	 * Purpose		: to get the used row counts from the excel files
	 * 
	 *************************************/
	public String getCellData(String filePath, String sheetName, String colName, int rowNum) {
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		String strData = null;
		int colNum = 0;
		Calendar cal = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeReport(null, "Fail", "Failed to find the '"+sheetName+"' sheet");
				Assert.fail("Failed to find the '"+sheetName+"' sheet");
			}
			
			//Find the column number based on the column name
			row = sh.getRow(0);
			for(int c=0; c<row.getLastCellNum(); c++) {
				cell = row.getCell(c);
				if(cell.getStringCellValue().equalsIgnoreCase(colName)) {
					colNum = c;
					break;
				}
			}
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			switch(cell.getCellType()) {
				case BLANK:
					strData = "";
					break;
				case STRING:
					strData = cell.getStringCellValue();
					break;
				case BOOLEAN:
					strData = String.valueOf(cell.getBooleanCellValue());
					break;
				case NUMERIC:
					if(DateUtil.isCellDateFormatted(cell) == true) {
						double dt = cell.getNumericCellValue();
						cal = Calendar.getInstance();
						cal.setTime(DateUtil.getJavaDate(dt));
						
						//If day is <10, then add zero as prefix
						if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
							sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
						}else {
							sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
						}
						
						//If month is <10, then add zero as prefix
						if((cal.get(Calendar.MONTH)+1) < 10) {
							sMonth = "0" + (cal.get(Calendar.MONTH)+1);
						}else {
							sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
						}
						
						sYear = String.valueOf(cal.get(Calendar.YEAR));
						strData = sDay + "/" + sMonth + "/" + sYear;
					}else {
						strData = String.valueOf(cell.getNumericCellValue());
					}
					break;
			}
			return strData;
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in the 'getCellData()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeReport(null, "Exception", "Assertion Exception in 'getCellData()' method. " + e);
			return null;
		}
		finally {
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				cal = null;
				sDay = null;
				sMonth = null;
				sYear = null;
			}catch(Exception e) {
				reports.writeReport(null, "Exception", "Exception in the 'getCellData()' method. " + e);
				return null;
			}
		}
	}
	
	
	
	/*************************************
	 * Method Name	: createDataProvider()
	 * Purpose		: to read the test data from the excel files & convert to Object[][]
	 * 
	 *************************************/
	public Object[][] createDataProvider(String fileName, String sheetName){
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		Object data[][] = null;
		List<String> colNames = null;
		Map<String, String> cellData = null;
		int rowNum = 0;
		int colNum = 0;
		int executionCount = 0;
		int actualCount = 0;
		Calendar cal = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		String strData = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\"+fileName+"\\Controller.xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			if(sh==null) {
				reports.writeReport(null, "Fail", "Failed to find the '"+sheetName+"' sheet");
				Assert.fail("Failed to find the '"+sheetName+"' sheet");
			}
			
			rowNum = sh.getPhysicalNumberOfRows();
			row = sh.getRow(0);
			colNum = row.getPhysicalNumberOfCells();
			
			//find the count of the test cases with the status YES
			for(int r=0; r<rowNum; r++) {
				row = sh.getRow(r);
				cell = row.getCell(3);
				if(cell.getStringCellValue().equalsIgnoreCase("Yes")) {
					executionCount++;
				}
			}
			
			
			//construct Object[][] & read all the column names and store them in List
			data = new Object[executionCount][1];
			colNames = new ArrayList<String>();
			row = sh.getRow(0);
			for(int c=0; c<colNum; c++) {
				cell = row.getCell(c);
				colNames.add(c, cell.getStringCellValue());
			}
			
			
			//Create a Map object for each row values
			for(int r=0; r<rowNum; r++) {
				row = sh.getRow(r);
				cell = row.getCell(3);
				if(cell.getStringCellValue().equalsIgnoreCase("Yes")) {
					cellData = new HashMap<String, String>();
					for(int c=0; c<colNum; c++) {
						cell = row.getCell(c);
						switch(cell.getCellType()) {
						case BLANK:
							strData = "";
							break;
						case STRING:
							strData = cell.getStringCellValue();
							break;
						case BOOLEAN:
							strData = String.valueOf(cell.getBooleanCellValue());
							break;
						case NUMERIC:
							if(DateUtil.isCellDateFormatted(cell) == true) {
								double dt = cell.getNumericCellValue();
								cal = Calendar.getInstance();
								cal.setTime(DateUtil.getJavaDate(dt));
								
								//If day is <10, then add zero as prefix
								if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
									sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
								}else {
									sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
								}
								
								//If month is <10, then add zero as prefix
								if((cal.get(Calendar.MONTH)+1) < 10) {
									sMonth = "0" + (cal.get(Calendar.MONTH)+1);
								}else {
									sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
								}
								
								sYear = String.valueOf(cal.get(Calendar.YEAR));
								strData = sDay + "/" + sMonth + "/" + sYear;
							}else {
								strData = String.valueOf(cell.getNumericCellValue());
							}
							break;
						}
						cellData.put(colNames.get(c), strData);
					}
					data[actualCount][0] = cellData;
					actualCount++;
				}
			}
			return data;
		}catch(Exception e) {
			reports.writeReport(null, "Exception", "Exception in the 'createDataProvider()' method. " + e);
			return null;
		}catch(AssertionError e) {
			reports.writeReport(null, "Exception", "Assertion Exception in 'createDataProvider()' method. " + e);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				data = null;
				colNames = null;
				cellData = null;
				cal = null;
				sDay = null;
				sMonth = null;
				sYear = null;
				strData = null;
			}catch(Exception e) {}
		}
	}
}

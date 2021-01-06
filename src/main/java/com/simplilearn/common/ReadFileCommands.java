package com.simplilearn.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFileCommands {
	
	public static String readContextProperty(String key) {
		String configFile = "./src/main/java/com/simplilearn/common/config.properties";
		InputStream inputFile;
		Properties prop = new Properties();
		
		try {
			inputFile = new FileInputStream(configFile);
			prop.load(inputFile);
		}
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}
		
		return prop.getProperty(key);
	}
	
	public static Object[][] readTestDataFromExcel(String sheetName) {
		String path = "./testdata/";
		String fileName = "data.xlsx";
		Object[][] dataSet;
		
		switch (sheetName) {
			case "validlogin":
			case "invalidlogin":
			case "searchtext":
			case "searchbymanufacturer":
				dataSet = new Object[1][2];
				break;
			case "newaccountnocountry":
				dataSet = new Object[2][10];
				break;
			case "order":
				dataSet = new Object[1][11];
				break;
			case "review":
				dataSet = new Object[1][5];
				break;	
			case "shareproduct":
				dataSet = new Object[1][4];
				break;
			case "newaccountvalid":
			case "newaccountinvalidzipcode":
			default:
				dataSet = new Object[2][11];
		}
		try {
			FileInputStream inputStream = new FileInputStream(path + fileName);
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			Sheet newAccountValidSheet = workBook.getSheet(sheetName);	
			int rows = newAccountValidSheet.getLastRowNum();		

			for (int row=1; row <=rows; row++) {
				Row rowData = newAccountValidSheet.getRow(row);
				for (int col=0; col < rowData.getLastCellNum(); col++) {
					Cell cellData = rowData.getCell(col);
					dataSet[row-1][col] = cellData.getStringCellValue();
				}
			}
		}		
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}
		
		return dataSet;
	}

}

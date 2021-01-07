package com.simplilearn.common;

import java.io.*;
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
		String emailKeyNumber = "emailKeyNumber.txt";
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
			Sheet excelSheet = workBook.getSheet(sheetName);
			int rows = excelSheet.getLastRowNum();

			for (int row=1; row <=rows; row++) {
				Row rowData = excelSheet.getRow(row);
				for (int col=0; col < rowData.getLastCellNum(); col++) {
					Cell cellData = rowData.getCell(col);
					if (sheetName.equals("newaccountvalid") && (col==0)) {
						BufferedReader fileReader = new BufferedReader(new FileReader(path + emailKeyNumber));
						String emailNum = fileReader.readLine();
						fileReader.close();

						FileWriter fileWriter = new FileWriter(path + emailKeyNumber);
						fileWriter.write(String.valueOf(Integer.parseInt(emailNum) + 1));
						fileWriter.close();

						String emailValue = cellData.getStringCellValue();
						int atSignPos = emailValue.toString().indexOf("@");
						dataSet[row - 1][col] = emailValue.toString().substring(0, atSignPos);
						dataSet[row - 1][col] = dataSet[row - 1][col] +
								emailNum.toString() +
								emailValue.toString().substring(atSignPos, emailValue.toString().length());

					}
					else{
						dataSet[row - 1][col] = cellData.getStringCellValue();
					}
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

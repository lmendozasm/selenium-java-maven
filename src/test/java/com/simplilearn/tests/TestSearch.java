package com.simplilearn.tests;

import static com.simplilearn.common.OtherCommon.logs;
import static com.simplilearn.common.OtherCommon.takeScreenshot;
import static com.simplilearn.common.ReadFileCommands.readTestDataFromExcel;
import static com.simplilearn.common.ReadFileCommands.readContextProperty;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.simplilearn.pages.MainPage;

public class TestSearch extends BaseTest {
	MainPage mainPage;
	SoftAssert softAssert = new SoftAssert();
	Integer counter = 1;

	@BeforeTest
	public void InitializePageObjects() {
		mainPage = new MainPage(webDriver);
		PageFactory.initElements(webDriver, mainPage);
	}

	@Test(dataProvider = "readSearchTextData")
	public void SearchBySearchBox(String searchText, String result) {
		mainPage.searchText(searchText);
		softAssert.assertTrue(mainPage.getNumberOfItemsFound().contains(result));
		softAssert.assertAll();
	}

	@Test(dataProvider = "readSearchByManufacturerData")
	public void SearchByManufacturer(String result, String item) {
		mainPage.chooseWomenCategory();
		mainPage.searchByManufacturer();
		softAssert.assertTrue(mainPage.getNumberOfItemsFound().contains(result));
		softAssert.assertTrue(mainPage.productOfManufacturerIsDisplayed(item));
		softAssert.assertAll();
	}

	@DataProvider
	private Object[][] readSearchTextData() {
		return readTestDataFromExcel(readContextProperty("searchtextTab"));
	}
	
	@DataProvider
	private Object[][] readSearchByManufacturerData(){
		return readTestDataFromExcel(readContextProperty("searchbymanufacturerTab"));
	}

	@AfterMethod
	private void runAfterMethod() {
		takeScreenshot(webDriver, this.getClass().getName() + counter );
		counter++;
	}
	
	@AfterMethod
	public void logFailures(ITestResult result) {
		if (result.FAILURE == result.getStatus()) {
			logs.info(result.getName());
			logs.info(result.getThrowable());
			takeScreenshot(webDriver, "errors/" + result.getName());
		}
		else
			logs.info(result.getName() + " test passed. ");		
	}
	
}
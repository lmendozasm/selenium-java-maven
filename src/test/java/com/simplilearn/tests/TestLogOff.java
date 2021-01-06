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
import com.simplilearn.pages.SignInPage;

public class TestLogOff extends BaseTest {
	MainPage mainPage;
	SignInPage signInPage;
	SoftAssert softAssert = new SoftAssert();

	@BeforeTest
	public void InitializePageObjects() {
		mainPage = new MainPage(webDriver);
		PageFactory.initElements(webDriver, mainPage);

		signInPage = new SignInPage(webDriver);
		PageFactory.initElements(webDriver, signInPage);
	}

	@Test(dataProvider = "readValidLoginData")
	public void LogOff(String email, String password) {
		mainPage.signIn();
		signInPage.signInAnAccount(email, password);
		softAssert.assertTrue(mainPage.msgAfterAccountSignInSuccessIsDisplayed());
		mainPage.signOut();
		softAssert.assertTrue(signInPage.signInIsDisplayed());
		softAssert.assertAll();
	}

	@DataProvider
	private Object[][] readValidLoginData() {
		return readTestDataFromExcel(readContextProperty("validloginTab"));
	}
	
	@AfterMethod
	public void runAfterMethod() {
		takeScreenshot(webDriver, this.getClass().getName());
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

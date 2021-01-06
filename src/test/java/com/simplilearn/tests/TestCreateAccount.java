package com.simplilearn.tests;

import static com.simplilearn.common.OtherCommon.takeScreenshot;
import static com.simplilearn.common.ReadFileCommands.readTestDataFromExcel;
import static com.simplilearn.common.OtherCommon.logs;
import static com.simplilearn.common.ReadFileCommands.readContextProperty;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.simplilearn.common.EnumsTitle;
import com.simplilearn.pages.CreateAccountPage;
import com.simplilearn.pages.MainPage;
import com.simplilearn.pages.SignInPage;

public class TestCreateAccount extends BaseTest {
	MainPage mainPage;
	SignInPage signInPage;
	CreateAccountPage createAccountPage;
	SoftAssert softAssert = new SoftAssert();
	Integer counter = 1;

	@BeforeTest
	public void InitializePageObjects() {
		mainPage = new MainPage(webDriver);
		PageFactory.initElements(webDriver, mainPage);

		signInPage = new SignInPage(webDriver);
		PageFactory.initElements(webDriver, signInPage);

		createAccountPage = new CreateAccountPage(webDriver);
		PageFactory.initElements(webDriver, createAccountPage);
	}

	@Test(dataProvider = "readValidAccountData")
	public void CreateNewAccount(String email, String title, String firstName, String lastName, String password,
			String address, String city, String state, String postalCode, String country, String phoneNumber) {

		mainPage.signIn();
		signInPage.createAnAccount(email);
		createAccountPage.selectTitle(title).specifyName(firstName, lastName).specifyPassword(password)
				.specifyAddressDetails(address, city, state, postalCode, country).specifyMobilePhone(phoneNumber)
				.register();
		softAssert.assertTrue(mainPage.msgAfterAccountSignInSuccessIsDisplayed());
		softAssert.assertAll();

		takeScreenshot(webDriver, this.getClass().getName() + counter);
		counter++;

		mainPage.signOut();
	}

	@Test(dataProvider = "readAccountDataNoCountry")
	public void CreateNewAccountWithoutCountrySpecified(String email, String title, String firstName, String lastName,
			String password, String address, String city, String state, String postalCode, String phoneNumber) {

		mainPage.signIn();
		signInPage.createAnAccount(email);
		createAccountPage.selectTitle(title).specifyName(firstName, lastName).specifyPassword(password)
				.specifyAddressDetails(address, city, state, postalCode, "").specifyMobilePhone(phoneNumber).register();
		softAssert.assertTrue(createAccountPage.errorAlertMessageIsDisplayed());
		softAssert.assertAll();

		takeScreenshot(webDriver, this.getClass().getName() + counter);
		counter++;
	}

	@Test(dataProvider = "readAccountDataInvalidZipCode")
	public void CreateNewAccountWithPostCodeLessThan4Chars(String email, String title, String firstName,
			String lastName, String password, String address, String city, String state, String postalCode,
			String country, String phoneNumber) {

		mainPage.signIn();
		signInPage.createAnAccount(email);
		createAccountPage.selectTitle(title).specifyName(firstName, lastName).specifyPassword(password)
				.specifyAddressDetails(address, city, state, postalCode, country).specifyMobilePhone(phoneNumber)
				.register();
		softAssert.assertTrue(createAccountPage.errorAlertMessageIsDisplayed());
		softAssert.assertAll();

		takeScreenshot(webDriver, this.getClass().getName() + counter);
		counter++;
	}

	@AfterMethod
	public void logFailures(ITestResult result) {
		if (result.FAILURE == result.getStatus()) {
			logs.info(result.getName());
			logs.info(result.getThrowable());
			takeScreenshot(webDriver, "errors/" + result.getName());
		} else
			logs.info(result.getName() + " test passed. ");
	}

	@DataProvider
	private Object[][] readValidAccountData() {
		return readTestDataFromExcel(readContextProperty("newaccountvalidTab"));
	}

	@DataProvider
	private Object[][] readAccountDataNoCountry() {
		return readTestDataFromExcel(readContextProperty("newaccountnocountryTab"));
	}

	@DataProvider
	private Object[][] readAccountDataInvalidZipCode() {
		return readTestDataFromExcel(readContextProperty("newaccountinvalidzipcodeTab"));
	}

}
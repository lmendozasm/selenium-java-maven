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

import com.simplilearn.pages.AccountPage;
import com.simplilearn.pages.MainPage;
import com.simplilearn.pages.SignInPage;

public class TestWriteReview extends BaseTest {
	MainPage mainPage;
	SignInPage signInPage;
	AccountPage accountPage;
	SoftAssert softAssert = new SoftAssert();

	@BeforeTest
	public void InitializePageObjects() {
		mainPage = new MainPage(webDriver);
		PageFactory.initElements(webDriver, mainPage);

		signInPage = new SignInPage(webDriver);
		PageFactory.initElements(webDriver, signInPage);

		accountPage = new AccountPage(webDriver);
		PageFactory.initElements(webDriver, accountPage);
	}

	@Test(dataProvider = "readReviewData")
	public void WriteReviewOnOrder(String email, String password, String orderId, String productToReview,
			String reviewMsg) {
		mainPage.signIn();
		signInPage.signInAnAccount(email, password);
		softAssert.assertTrue(mainPage.signOutIsDisplayed());
		softAssert.assertTrue(mainPage.msgAfterAccountSignInSuccessIsDisplayed());
		accountPage.chooseOrderHistory().chooseOrder(orderId)
				.chooseProductToReview(productToReview)
				// Write review more than 50 characters
				.writeReview(reviewMsg).submitMessage();
		softAssert.assertTrue(
				accountPage.messageIsSaved(reviewMsg));
		softAssert.assertAll();

		takeScreenshot(webDriver, this.getClass().getName());

		mainPage.signOut();
		signInPage.goToHome();
	}

	@DataProvider
	public Object[][] readReviewData() {
		return readTestDataFromExcel(readContextProperty("reviewTab"));
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

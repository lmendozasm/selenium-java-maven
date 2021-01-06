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

import com.simplilearn.common.EnumsShareType;
import com.simplilearn.pages.MainPage;
import com.simplilearn.pages.SignInPage;

public class TestShareProduct extends BaseTest {
	MainPage mainPage;
	SignInPage signInPage;
	SoftAssert softAssert = new SoftAssert();
	Integer counter = 1;
	
	@BeforeTest
	public void InitializePageObjects() {
		mainPage = new MainPage(webDriver);
		PageFactory.initElements(webDriver, mainPage);

		signInPage = new SignInPage(webDriver);
		PageFactory.initElements(webDriver, signInPage);
	}

	@Test(dataProvider="readShareProductData")
	public void ShareProductToPinterest(String category, String item, String email, String password) {
		shareProduct(EnumsShareType.Pinterest, category, item, email, password);
	}

	@Test(dataProvider="readShareProductData")
	public void ShareProductToGooglePlus(String category, String item, String email, String password) {
		shareProduct(EnumsShareType.GooglePlus, category, item, email, password);
	}

	@Test(dataProvider="readShareProductData")
	public void ShareProductToFacebook(String category, String item, String email, String password) {
		shareProduct(EnumsShareType.Facebook, category, item, email, password);
	}

	private void shareProduct(EnumsShareType shareType, String category, String item, String email, String password) {
		mainPage.chooseWomenCategory();
		softAssert.assertTrue(mainPage.correctCategoryIsDisplayed(category));
		mainPage.chooseItemInCategory("Blouse");
		softAssert.assertTrue(mainPage.correctItemIsDisplayed(item));

		switch (shareType) {
		case Pinterest:
			mainPage.clickPinterest();
			takeScreenshot(webDriver, this.getClass().getName() + counter);
			counter++;
			mainPage.switchToPopupWindow();
			mainPage.loginToPinterest(email, password);
			break;
		case GooglePlus:
			mainPage.clickGooglePlus();
			takeScreenshot(webDriver, this.getClass().getName() + counter);
			counter++;
			mainPage.switchToPopupWindow();
			mainPage.loginToGooglePlus(email, password);
			break;
		case Facebook:
			mainPage.clickFacebook();
			takeScreenshot(webDriver, this.getClass().getName() + counter);
			counter++;
			mainPage.switchToPopupWindow();
			mainPage.loginToFacebook(email, password);
			break;
		default:
		}

		mainPage.closeItemFrame();
		mainPage.signIn();
		signInPage.signInAnAccount(email, password);
		softAssert.assertTrue(mainPage.msgAfterAccountSignInSuccessIsDisplayed());
		mainPage.signOut();
		softAssert.assertTrue(signInPage.signInIsDisplayed());
		softAssert.assertAll();
	}
	
	@DataProvider
	private Object[][] readShareProductData() {
		return readTestDataFromExcel(readContextProperty("shareproductTab"));
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
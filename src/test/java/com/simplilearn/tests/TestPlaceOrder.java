package com.simplilearn.tests;

import static com.simplilearn.common.OtherCommon.logs;
import static com.simplilearn.common.OtherCommon.takeScreenshot;
import static com.simplilearn.common.ReadFileCommands.readTestDataFromExcel;
import static com.simplilearn.common.ReadFileCommands.readContextProperty;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.simplilearn.pages.MainPage;
import com.simplilearn.pages.ShoppingCartPage;
import com.simplilearn.pages.SignInPage;

public class TestPlaceOrder extends BaseTest {
	MainPage mainPage;
	SignInPage signInPage;
	ShoppingCartPage shoppingCartPage;
	SoftAssert softAssert = new SoftAssert();

	@BeforeTest
	public void InitializePageObjects() {
		mainPage = new MainPage(webDriver);
		PageFactory.initElements(webDriver, mainPage);

		signInPage = new SignInPage(webDriver);
		PageFactory.initElements(webDriver, signInPage);

		shoppingCartPage = new ShoppingCartPage(webDriver);
		PageFactory.initElements(webDriver, shoppingCartPage);
	}

	@Test(dataProvider = "readOrderData")
	public void Purchase3Products(String category1, String item1, String category2, String item2, String category3,
			String item3, String email, String password, String msgAfterSignIn, String msgAfterChoosingBankWire,
			String msgInConfirmPage) {

		// Add item from Women Category
		mainPage.chooseWomenCategory();
		softAssert.assertTrue(mainPage.correctCategoryIsDisplayed(category1));
		mainPage.chooseItemInCategory(item1);
		softAssert.assertTrue(mainPage.correctItemIsDisplayed(item1));
		mainPage.addToCart();
		softAssert.assertTrue(mainPage.correctMsgIsDisplayedAfterAddedToCard());
		mainPage.continueShopping();

		// Add item from Dresses Category
		mainPage.chooseDressesCategory();
		softAssert.assertTrue(mainPage.correctCategoryIsDisplayed(category2));
		mainPage.chooseItemInCategory(item2);
		softAssert.assertTrue(mainPage.correctItemIsDisplayed(item2));
		mainPage.addToCart();
		softAssert.assertTrue(mainPage.correctMsgIsDisplayedAfterAddedToCard());
		mainPage.continueShopping();

		// Add item from T-shirts Category
		mainPage.chooseTshirtsCategory();
		softAssert.assertTrue(mainPage.correctCategoryIsDisplayed(category3));
		mainPage.chooseItemInCategory(item3);
		softAssert.assertTrue(mainPage.correctItemIsDisplayed(item3));
		mainPage.addToCart();
		softAssert.assertTrue(mainPage.correctMsgIsDisplayedAfterAddedToCard());
		mainPage.proceedToCheckOut();

		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("window.scrollBy(0,500);");

		// Complete order in Shopping Cart
		shoppingCartPage.proceedToCheckOut();
		signInPage.signInAnAccount(email, password);
		softAssert.assertTrue(shoppingCartPage.correctMsgIsDisplayedAfterSignIn(msgAfterSignIn));

		js.executeScript("window.scrollBy(0,500);");

		shoppingCartPage.processAddress().checkTermsAgreement().processCarrier().chooseBankWire();
		softAssert.assertTrue(shoppingCartPage.correctMsgIsDisplayedAfterChoosingPaymentMethod(msgAfterChoosingBankWire));
		shoppingCartPage.confirmOrder();
		softAssert.assertTrue(shoppingCartPage.correctHeadingIsDisplayedInOrderConfirmPage(msgInConfirmPage));
		softAssert.assertAll();

		takeScreenshot(webDriver, this.getClass().getName());

		mainPage.signOut();
		signInPage.goToHome();
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
	
	@DataProvider
	private Object[][] readOrderData() {
		return readTestDataFromExcel(readContextProperty("orderTab"));
	}
}

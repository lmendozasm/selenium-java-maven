package com.simplilearn.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingCartPage {
	WebDriver webDriver;
	WebDriverWait waitObj;

	@FindBy (css = "a[class='button btn btn-default standard-checkout button-medium']") WebElement proceedToCheckButton;
	@FindBy (css = "h3[class='page-subheading']") WebElement addressHeader;
	@FindBy (name = "processAddress") WebElement processAddressButton;
	@FindBy (css = "input[id='cgv']") WebElement termsCheckbox;
	@FindBy (name = "processCarrier") WebElement processCarrier;
	@FindBy (className = "bankwire") WebElement bankwireButton;
	@FindBy (css = "h3[class='page-subheading']") WebElement paymentHeader;
	@FindBy (xpath = "//span[contains(.,'I confirm my order')]") WebElement confirmButton;
	@FindBy (css = "h1[class='page-heading']") WebElement orderConfirmHeader;
	
	public ShoppingCartPage(WebDriver driver) {
		webDriver = driver;
		waitObj = new WebDriverWait(webDriver, 10);
	}
	
	public void proceedToCheckOut() {
		proceedToCheckButton.click();
	}
	
	public ShoppingCartPage processAddress() {
		waitObj.until(ExpectedConditions.elementToBeClickable(processAddressButton));
		processAddressButton.click();
		return this;
	}
	
	public ShoppingCartPage checkTermsAgreement() {
		termsCheckbox.click();
		return this;
	}
	
	public ShoppingCartPage processCarrier() {
		processCarrier.click();
		return this;
	}
	
	public ShoppingCartPage chooseBankWire() {
		bankwireButton.click();
		return this;
	}
	
	public void confirmOrder() {
		confirmButton.click();
	}
	
	// Methods called for assertion
	
	public Boolean correctMsgIsDisplayedAfterSignIn(String msg) {
		return addressHeader.getText().equalsIgnoreCase(msg);
	}
	
	public Boolean correctMsgIsDisplayedAfterChoosingPaymentMethod(String msg) {
		return paymentHeader.getText().equalsIgnoreCase(msg);
	}
	
	public Boolean correctHeadingIsDisplayedInOrderConfirmPage(String msg) {
		return orderConfirmHeader.getText().equalsIgnoreCase(msg);
	}
}

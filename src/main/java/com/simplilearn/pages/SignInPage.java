package com.simplilearn.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage {
	WebDriver webDriver;
	
	@FindBy (id = "email_create") WebElement createAcctEmailTextBox;
	@FindBy (id = "SubmitCreate") WebElement createAcctSubmitButton;
	@FindBy (id = "email") WebElement emailTextBox;
	@FindBy (id = "passwd") WebElement passwordTextBox;
	@FindBy (id = "SubmitLogin") WebElement loginButton;
	@FindBy (className = "home") WebElement homeButton;
	@FindBy (className = "login") WebElement signInButton;
	@FindBy (css = "div[class$='alert-danger']") WebElement alertErrorMsg;

	public SignInPage(WebDriver driver) {
		webDriver = driver;
	}
	
	public void createAnAccount(String email) {
		
		createAcctEmailTextBox.sendKeys(email);
		createAcctSubmitButton.click();
		
	}
	
	public void signInAnAccount(String email, String password) {
		
		emailTextBox.sendKeys(email);
		passwordTextBox.sendKeys(password);
		loginButton.click();
		
	}
	
	public void goToHome() {
		homeButton.click();
	}
	
	// Methods called for assertion

	public Boolean signInIsDisplayed() {
		return signInButton.isDisplayed();
	}
	
	public Boolean alertMessageIsDisplayed() {
		return alertErrorMsg.findElement(By.tagName("p")).getText().contains("error");
	}
	
	public Boolean correctBackgroundColorOfMessageIsDisplayed() {
		return alertErrorMsg.getCssValue("background-color").contains("rgba(243, 81, 92, 1");
	}

}

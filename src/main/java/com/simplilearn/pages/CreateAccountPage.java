package com.simplilearn.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.simplilearn.common.EnumsTitle;

public class CreateAccountPage {
	WebDriver webDriver;
	
	@FindBy (id = "id_gender1") WebElement genderMrOptionButton;
	@FindBy (id = "id_gender2") WebElement genderMrsOptionButton;
	@FindBy (id = "customer_firstname") WebElement firstNameTextBox;
	@FindBy (id = "customer_lastname") WebElement lastNameTextBox;
	@FindBy (id = "passwd") WebElement passwordTextBox;
	@FindBy (id = "address1") WebElement address1TextbBox;
	@FindBy (id = "city") WebElement cityTextBox;
	@FindBy (id = "id_state") WebElement stateDropDown;
	@FindBy (id = "postcode") WebElement postCodeTextBox;
	@FindBy (id = "id_country") WebElement countryDropDown;
	@FindBy (id = "phone_mobile") WebElement phoneMobileTextBox;
	@FindBy (id = "submitAccount") WebElement submitAccountButton;
	@FindBy (css = "div[class$='alert-danger']") WebElement alertErrorMsg;
	
	public CreateAccountPage(WebDriver driver) {
		webDriver = driver;
	}

	public CreateAccountPage selectTitle(String title) {

		if (title == EnumsTitle.Mr.toString())
			genderMrOptionButton.click();
		else if (title == EnumsTitle.Mrs.toString())
			genderMrsOptionButton.click();

		return this;
	}

	public CreateAccountPage specifyName(String firstName, String lastName) {

		firstNameTextBox.sendKeys(firstName);
		lastNameTextBox.sendKeys(lastName);
		return this;

	}

	public CreateAccountPage specifyPassword(String password) {

		passwordTextBox.sendKeys(password);
		return this;

	}

	public CreateAccountPage specifyAddressDetails(String address, String city, String state, String zipCode,
			String country) {

		address1TextbBox.sendKeys(address);
		cityTextBox.sendKeys(city);

		Select stateSelect = new Select(stateDropDown);
		stateSelect.selectByVisibleText(state);

		postCodeTextBox.sendKeys(zipCode);

		Select countrySelect = new Select(countryDropDown);
		if (country.isEmpty())
			countrySelect.selectByValue(country);
		else
			countrySelect.selectByVisibleText(country);

		return this;

	}

	public CreateAccountPage specifyMobilePhone(String phone) {

		phoneMobileTextBox.sendKeys(phone);
		return this;

	}

	public void register() {

		submitAccountButton.click();

	}
	
	// Methods called for assertion
	
	public Boolean errorAlertMessageIsDisplayed() {
		return alertErrorMsg.findElement(By.tagName("p")).getText().contains("error");
	}
}

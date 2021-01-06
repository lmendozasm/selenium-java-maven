package com.simplilearn.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AccountPage {
	WebDriver webDriver;
	
	@FindBy (xpath = "//span[contains(.,'Order history and details')]") WebElement orderHistoryDetailsButton;
	@FindBy (xpath = "//table[@id='order-list']/tbody/tr") List<WebElement> orderDetailsTable;
	@FindBy (name = "id_product") WebElement productDropdown;
	@FindBy (name = "msgText") WebElement reviewTextBox;
	@FindBy (css = "button[name='submitMessage']") WebElement submitMessageButton;
	@FindBy (xpath = "//table[@class='detail_step_by_step table table-bordered']/tbody/tr") List<WebElement> messagesTable; 
	
	public AccountPage(WebDriver driver) {
		webDriver = driver;
	}
	
	public AccountPage chooseOrderHistory () {
		orderHistoryDetailsButton.click();
		return this;
	}
	
	public AccountPage chooseOrder (String oderId) {
		Boolean found = false;
		for (int row = 1; row <= orderDetailsTable.size(); row ++) {
			List<WebElement> columnDataList = webDriver.findElements(By.xpath("//table[@id='order-list']/tbody/tr[" + row + "]/td"));
			for (WebElement columnData : columnDataList) {
				if (columnData.getText().contains(oderId)){
					columnData.click();
					found = true;
					break;
				}
			}
			if (found)
				break;
		}
		return this;
	}

	public AccountPage chooseProductToReview (String product) {
		Select producSelect = new Select(productDropdown);
		producSelect.selectByVisibleText(product);
		return this;
	}
	
	public AccountPage writeReview (String msg) {
		reviewTextBox.sendKeys(msg);
		return this;
	}
	
	public AccountPage submitMessage () {
		submitMessageButton.click();
		return this;
	}

	//Methods called for assertion
	public Boolean messageIsSaved(String msg) {
		Boolean found = false;
		for (int row = 1; row <= messagesTable.size(); row ++) {
			List<WebElement> columnDataList = webDriver.findElements(By.xpath("//table[@class='detail_step_by_step table table-bordered']/tbody/tr[" + row + "]/td"));
			for (WebElement columnData : columnDataList) {
				if (columnData.getText().contains(msg)){
					found = true;
					break;
				}
			}
			if (found)
				break;
		}
		return found;	
	}
}

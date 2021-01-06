package com.simplilearn.pages;

import static com.simplilearn.common.ReadFileCommands.readContextProperty;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
	WebDriver webDriver;
	WebDriverWait waitObj;
	String parent;
	
 	@FindBy (css = "a[class='login']") WebElement signInButton;
 	@FindBy (css = "a[class='logout']") WebElement signOutButton;
 	@FindBy (id = "search_query_top") WebElement searchTextBox;
 	@FindBy (name = "submit_search") WebElement searchButton;
 	@FindBy (xpath = "//a[contains(.,'Women')]") WebElement womenCategoryLink;
 	@FindBy (xpath = "//a[contains(@class,'subcategory-name')][contains(.,'Dresses')]") WebElement dressesCategoryLink;
 	@FindBy (xpath = "//a[contains(@class,'subcategory-name')][contains(.,'Tops')]") WebElement topsCategoryLink;
 	@FindBy (xpath = "//a[contains(@class,'subcategory-name')][contains(.,'T-shirts')]") WebElement tshirtsCategoryLink;
 	@FindBy (className = "category-name") WebElement categoryNameText;
 	@FindBy (css = "iframe[class='fancybox-iframe']") WebElement itemDetailsFrame;
	@FindBy (css = "h1[itemprop='name']") WebElement itemNameText;
	@FindBy (name = "Submit") WebElement addToCardButton;
	@FindBy (css = "h2") WebElement addedToCartMessage;
	@FindBy (css = "span[title='Continue shopping']") WebElement continueShoppingButton;
	@FindBy (css = "a[title='Proceed to checkout']") WebElement proceedToCheckoutButton;
	@FindBy (css = "button[class='btn btn-default btn-pinterest']") WebElement pinterestButton;
	@FindBy (css = "button[class='btn btn-default btn-google-plus']") WebElement googlePlusButton;
	@FindBy (css = "button[class='btn btn-default btn-facebook']") WebElement facebookButton;
	@FindBy (xpath = "//a[@class='fancybox-item fancybox-close']") WebElement closeItemButton;
	@FindBy (id = "layered_manufacturer_1") WebElement manufacturerCheckBox;
	@FindBy (className = "info-account") WebElement welcomeAccountMsg;
	@FindBy (className = "product-count") WebElement searchProductCountResult;
	
	public MainPage(WebDriver driver) {
		webDriver = driver;
		waitObj = new WebDriverWait(webDriver, 60);
		parent = webDriver.getWindowHandle();
	}
	
	public void signIn() {
		signInButton.click();
	}
	
	public void signOut() {
		signOutButton.click();
	}
	
	public void searchText (String string) {
		searchTextBox.sendKeys(string);
		searchButton.click();
	}
	
	public void searchByManufacturer () {
		if (!manufacturerCheckBox.isSelected())
			manufacturerCheckBox.click();
	}
	
	public void chooseWomenCategory() {
		womenCategoryLink.click();
	}
	
	public void chooseItemInCategory(String item) {
		webDriver.findElement(By.cssSelector("a[title='" + item + "'][itemprop='url']")).click();
		webDriver.switchTo().frame(itemDetailsFrame);
	}
	
	public void chooseDressesCategory() {
		womenCategoryLink.click();
		dressesCategoryLink.click();
	}
	
	public void chooseTshirtsCategory() {
		womenCategoryLink.click();
		topsCategoryLink.click();
		tshirtsCategoryLink.click();
	}

	public void addToCart() {
		addToCardButton.click();
	}
	
	public void continueShopping() {
		waitObj.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
		continueShoppingButton.click();
	}
	
	public void proceedToCheckOut() {
		waitObj.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
		proceedToCheckoutButton.click();
		webDriver.switchTo().parentFrame();
	}
	
	public void clickPinterest() {
		pinterestButton.click();
	}
	
	public void clickGooglePlus() {
		googlePlusButton.click();
	}
	
	public void clickFacebook() {
		facebookButton.click();
	}
	
	public void closeItemFrame() {
		webDriver.switchTo().window(parent);
		webDriver.switchTo().parentFrame();
		closeItemButton.click();
	}
	
	public void switchToPopupWindow() {		
		Set<String> allWindows = webDriver.getWindowHandles();
		for (String element:allWindows) {
			if (!element.equalsIgnoreCase(parent)) 
				webDriver.switchTo().window(element);
		}
	}
	
	public void loginToPinterest(String email, String password) {
		// Since 2 different login can show up, will do try catch so it will still proceed
		try {
			webDriver.findElement(By.cssSelector("div[data-test-id='simple-login-button']")).findElement(By.cssSelector("button[type='button']")).click();
			webDriver.findElement(By.id("email")).sendKeys(email);
			webDriver.findElement(By.id("password")).sendKeys(password);
			webDriver.findElement(By.cssSelector("button[class='red SignupButton active']")).click();
		}
		catch (Exception e) {		
		}
		finally {
			webDriver.close();			
		}
	}
	
	public void loginToGooglePlus(String email, String password) {
		WebElement emailTextBox = webDriver.findElement(By.cssSelector("input[type='email']"));
		emailTextBox.sendKeys(email);
		emailTextBox.sendKeys(Keys.ENTER);
		
		// Page says "Google+ is no longer available for consumer (personal) and brand accounts"		
		// So just close pop-up Google+ window
		webDriver.close();
	}
	
	public void loginToFacebook(String email, String password) {
		webDriver.findElement(By.name("email")).sendKeys(email);
		webDriver.findElement(By.name("pass")).sendKeys(password);
		webDriver.findElement(By.id("loginbutton")).click();
		webDriver.close();
	}
	
	// Methods called for assertion

	public Boolean msgAfterAccountSignInSuccessIsDisplayed() {
		return welcomeAccountMsg.getText().contains(readContextProperty("accountSignInSuccessMsg"));
	}
	
	public String getNumberOfItemsFound() {
		return searchProductCountResult.getText();
	}
	
	public Boolean productOfManufacturerIsDisplayed(String productName) {
		return webDriver.findElement(By.cssSelector("a[title='"+ productName +"']")).isDisplayed();
	}
	
	public Boolean signOutIsDisplayed() {
		return signOutButton.isDisplayed();
	}
	
	public Boolean correctCategoryIsDisplayed(String category) {
		return categoryNameText.getText().contains(category);
	}
	
	public Boolean correctItemIsDisplayed(String item) {
		return itemNameText.getText().contains(item);
	}
	
	public Boolean correctMsgIsDisplayedAfterAddedToCard() {
		return addedToCartMessage.getText().contains(readContextProperty("addCartSuccessMsg"));
	}
}

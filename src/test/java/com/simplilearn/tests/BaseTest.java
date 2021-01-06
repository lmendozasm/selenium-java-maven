package com.simplilearn.tests;

import static com.simplilearn.common.BrowserCommands.getDriver;
import static com.simplilearn.common.ReadFileCommands.readContextProperty;
import static com.simplilearn.common.OtherCommon.logs;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class BaseTest {
	static WebDriver webDriver;
	
	//@BeforeSuite
	//public void startUpBrowser() {
	//	String browserCmd = "webdriver.chrome.driver";
	//	String browserCmdFile = "./drivers/chromedriver.exe";
	
	@Parameters({"browserCmd","browserCmdFile"})
	@BeforeSuite
	public void startUpBrowser(String browserCmd, String browserCmdFile) {

		webDriver = getDriver(browserCmd, browserCmdFile);
		webDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		webDriver.manage().window().fullscreen();
		webDriver.get(readContextProperty("url"));
		
		logs = Logger.getLogger(this.getClass().getName());
	}
	
	@AfterSuite
	public void closeBrowser() {
		webDriver.close();
	}
	
}

package com.simplilearn.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserCommands {
	private static WebDriver webDriver;
	
	public static WebDriver getDriver(String browserCmd, String browserCmdFile) {
		if (webDriver == null) {
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			webDriver = new ChromeDriver();
		}
		return webDriver;
	}
}

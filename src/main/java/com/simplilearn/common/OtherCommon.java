package com.simplilearn.common;

import static com.simplilearn.common.OtherCommon.logs;
import static com.simplilearn.common.OtherCommon.takeScreenshot;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class OtherCommon {
	public static Logger logs;

	public static void takeScreenshot(WebDriver webdriver, String fileName) {
		TakesScreenshot screenShot = (TakesScreenshot)webdriver;
		File sourceFile = screenShot.getScreenshotAs(OutputType.FILE);
		String filePathName = "./screenshots/" + fileName + ".png";
		File destFile = new File(filePathName);
		try {
			Files.copy(sourceFile, destFile);
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}

}

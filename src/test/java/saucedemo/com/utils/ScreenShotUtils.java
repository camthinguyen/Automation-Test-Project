package saucedemo.com.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import saucedemo.com.testcases.BaseTest;

public class ScreenShotUtils {
	static final String imageFolder = "screenshots/";
	static final String ext = ".png";
	static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
	static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);

	public static void takeScreenshot(String fileName) {
		File src = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(getFullFilePath(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getMethodName(String displayName) {
		return displayName.substring(0, displayName.length() - 2);
	}

	public static String getFullFilePath(String fileName) {
		return imageFolder + fileName + LocalDateTime.now().format(DATETIME_FORMATTER) + ext;
	}
}

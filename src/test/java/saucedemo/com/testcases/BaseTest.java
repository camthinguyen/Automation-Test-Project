package saucedemo.com.testcases;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public static WebDriver driver;
	public static final String URL_login = "https://www.saucedemo.com/";
	String version = "124.0.2";
	
	@BeforeAll
	public void setUp() {
		WebDriverManager.firefoxdriver().browserVersion(version).setup();
		boolean headlessMode = Boolean.parseBoolean(System.getProperty("headless"));
		FirefoxOptions options = new FirefoxOptions();
		
		if (headlessMode == true)
			options.addArguments("-headless");
		driver = new FirefoxDriver(options);
		driver.get(URL_login);
	}

	@AfterAll
	public void tearDown() {
		driver.quit();
	}
}

package saucedemo.com.testcases;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
	public static WebDriver driver  = new FirefoxDriver();
	public static final String URL_login = "https://www.saucedemo.com/";
	
    @BeforeAll
	public void setUp() {
		driver.get(URL_login);
	}

	@AfterAll
	public void tearDown() {		
		driver.quit();
	}
}

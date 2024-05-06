package saucedemo.com.testcases;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {
	public static WebDriver driver;
	public static final String URL_login = "https://www.saucedemo.com/";
	
    @BeforeAll
	public void setUp() {
    	WebDriverManager.firefoxdriver().browserVersion("124.0.2").setup();
    	driver  = new FirefoxDriver();
		driver.get(URL_login);
	}

	@AfterAll
	public void tearDown() {		
		driver.quit();
	}
}

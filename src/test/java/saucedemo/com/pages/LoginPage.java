package saucedemo.com.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import saucedemo.com.utils.WaitUtils;


public class LoginPage {
	
	@FindBy (id = "user-name")
	WebElement userName;
	
	@FindBy (id = "password")
	WebElement password;
	
	@FindBy (id = "login-button")
	WebElement loginBtn;
	
	@FindBy (xpath = "//div[@class='error-message-container error']/h3")
	WebElement loginErrorMsg;
	
	@FindBy (css = "div.error-message-container.error button.error-button")
	WebElement closeErrorBtn;
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginWithUserNamePassword(String userName, String password) {
		this.userName.sendKeys(userName);
		this.password.sendKeys(password);
		this.loginBtn.click();
	}
	
	public String getLoginErrorMessage() {
		WaitUtils.waitForElementDisplay(driver, this.loginErrorMsg);
		return this.loginErrorMsg.getText();
	}
	
	public void closeLoginErrorMessage() {
		WaitUtils.waitForElementDisplay(driver, this.closeErrorBtn);
		this.closeErrorBtn.click();
	}
	
	public boolean checkErrorMessageExisting() {		
		List<WebElement> elements = driver.findElements(By.cssSelector("div.error-message-container.error>h3"));
		if (elements.size() == 0)
			return false;
		return true;
	}
	
	public void resetLoginTextField() {
		userName.clear();
		password.clear();
	}
}

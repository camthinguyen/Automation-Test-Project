package saucedemo.com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import saucedemo.com.utils.WaitUtils;

public class MenuComponent {
	@FindBy (id = "react-burger-menu-btn")
	WebElement menuBgBtn;
	
	@FindBy (id = "inventory_sidebar_link")
	WebElement inventoryLink;
	
	@FindBy (id = "logout_sidebar_link")
	WebElement logoutLink;
	
	
	WebDriver driver;

	public MenuComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void logout() {
		menuBgBtn.click();
		WaitUtils.waitForElementDisplay(driver, logoutLink);
		logoutLink.click();
	}
}

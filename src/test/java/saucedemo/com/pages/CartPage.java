package saucedemo.com.pages;

import java.util.List;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import saucedemo.com.utils.ProductUtils;

public class CartPage {
	@FindBy(className = ".header_secondary_container>span")
	WebElement cartTitle;
	
	@FindAll(value = { @FindBy (css = "div.cart_item")})
	List<WebElement> items;
	
	@FindAll(value = { @FindBy (xpath = "//button[text()='Remove']")})
	List<WebElement> removeBtns;
	
	@FindBy(id = "continue-shopping")
	WebElement continueShopping;
	
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public int countNumberItemInCart() {
		return items.size();
	}
	
	public void clickContinueShopping() {
		continueShopping.click();
	}
	
	public void removeProducts(String...productNames) {
		Stream.of(productNames).forEach(productName -> driver.findElement(By.id(ProductUtils.convertProductNameToRemoveProductButtonId(productName))).click());
	}	
	
	public void removeAllProducts() {
		removeBtns.stream().forEach(removeBtn ->removeBtn.click());
	}
}

package saucedemo.com.pages;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import saucedemo.com.utils.ProductUtils;
import saucedemo.com.utils.WaitUtils;

public class ProductPage {

	@FindBy(css = "div.header_secondary_container>span")
	WebElement productTitle;
	
	@FindBy(css = "a.shopping_cart_link")
	WebElement viewCartLink;
	
	WebDriver driver;

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getTitle() {
		return productTitle.getText();
	}
	
	public boolean checkRemoveButtonDisplayedAndEnabled(String... productNames) {
		List<WebElement> removeBtns = Stream.of(productNames).map(name -> driver.findElement(By.id(ProductUtils.convertProductNameToRemoveProductButtonId(name)))).collect(Collectors.toList());
		return removeBtns.stream().allMatch(removeBtn -> removeBtn.isDisplayed() && removeBtn.isEnabled());
	}
	
	public boolean checkAddButtonDisplayedAndEnabled(String... productNames) {
		List<WebElement> addBtns = Stream.of(productNames).map(name -> driver.findElement(By.id(ProductUtils.convertProductNameToAddProductButtonId(name)))).collect(Collectors.toList());
		return addBtns.stream().allMatch(removeBtn -> removeBtn.isDisplayed() && removeBtn.isEnabled());
	}
	
	public int getNumberOfProductShownOnCartIcon() {
		String numberOfItem = viewCartLink.getText();
		if(!numberOfItem.equals(""))
			return Integer.parseInt(viewCartLink.getText());
		return 0;
	}
	
	public void clickLinkToViewCart() {
		viewCartLink.click();
	}
	
	public void waitForProductTitleDisplayed() {
		WaitUtils.waitForElementDisplay(driver, productTitle);
	}
	
	public void addProductToCart(String... productNames) {
		Stream.of(productNames).forEach(productName -> driver.findElement(By.id(ProductUtils.convertProductNameToAddProductButtonId(productName))).click());
	}
	
	public void removeProduct(String... productNames) {
		Stream.of(productNames).forEach(productName -> driver.findElement(By.id(ProductUtils.convertProductNameToRemoveProductButtonId(productName))).click());
	}	
}

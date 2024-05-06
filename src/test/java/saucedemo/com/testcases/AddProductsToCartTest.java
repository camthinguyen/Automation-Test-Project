package saucedemo.com.testcases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import saucedemo.com.extension.CaptureScreenShotExtension;
import saucedemo.com.extension.LoggingExtension;
import saucedemo.com.pages.CartPage;
import saucedemo.com.pages.LoginPage;
import saucedemo.com.pages.ProductPage;
import saucedemo.com.pages.MenuPage;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(CaptureScreenShotExtension.class)
@ExtendWith(LoggingExtension.class)
public class AddProductsToCartTest extends BaseTest {
	LoginPage loginPage;
	ProductPage productPage;
	CartPage cartPage;
	MenuPage menuPage;

	@BeforeEach
	public void loginForTest() {
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		
		loginPage.loginWithUserNamePassword("standard_user", "secret_sauce");
		productPage.waitForProductTitleDisplayed();
	}

	@Test
	@DisplayName("Successfully added one product to the cart")
	public void test01AddProductToCart() {
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
		
		String productName = "Sauce Labs Backpack";
		
		productPage.addProductToCart(productName);
		assertTrue(productPage.checkRemoveButtonDisplayedAndEnabled(productName),  "Verify 'Remove' button should be shown for added product");
		assertEquals(1, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", 1));
		
		productPage.clickLinkToViewCart();
		assertEquals(1, cartPage.countNumberItemInCart(), "Verify number of product items in cart page");
	}

	@Test
	@DisplayName("Successfully added multiple products to the cart")
	public void test02AddMultiProductsToCart() {
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
		
		String[] productNames = new String[] { "Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt" };
		int numerOfProduct = productNames.length;

		productPage.addProductToCart(productNames);
		assertTrue(productPage.checkRemoveButtonDisplayedAndEnabled(productNames), "Verify 'Remove' button displayed for added products");
		assertEquals(numerOfProduct, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", numerOfProduct));
		
		productPage.clickLinkToViewCart();
		assertEquals(numerOfProduct, cartPage.countNumberItemInCart(), "Verify number of product items in cart page");
	}

	@Test
	@DisplayName("Successfully remove a product by clicking on 'Remove' button on the Product page")
	public void test03RemoveProductFromProductPage() {
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
		
		String[] productNames = new String[] { "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt" };
		int numerOfProduct = productNames.length;
		
		productPage.addProductToCart(productNames);
		assertTrue(productPage.checkRemoveButtonDisplayedAndEnabled(productNames), "Verify 'Remove' button displayed for added products");
		assertEquals(numerOfProduct, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", numerOfProduct));

		productPage.removeProduct(productNames[0]);
		assertTrue(productPage.checkAddButtonDisplayedAndEnabled(productNames[0]), "Verify 'Add to cart' button displayed for removed product");
		assertEquals(numerOfProduct - 1, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", numerOfProduct-1));
		
		productPage.clickLinkToViewCart();
		assertEquals(numerOfProduct - 1, cartPage.countNumberItemInCart(), "Verify number of product items in cart page");
	}

	@Test
	@DisplayName("Successfully remove all added products by clicking on 'Remove' button on the Product page")
	public void test04RemoveAllProductsFromProductPage() {
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
		
		String[] productNames = new String[] { "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt" };
		int numerOfProduct = productNames.length;

		productPage.addProductToCart(productNames);
		assertTrue(productPage.checkRemoveButtonDisplayedAndEnabled(productNames), "Verify 'Remove' button displayed added products");
		assertEquals(numerOfProduct, productPage.getNumberOfProductShownOnCartIcon(), "Verify %n displayed on top of cart icon");

		productPage.removeProduct(productNames);
		assertTrue(productPage.checkAddButtonDisplayedAndEnabled(productNames), "Verify 'Add to cart' buttons displayed for removed products");
		assertEquals(0, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", 0));
		
		productPage.clickLinkToViewCart();
		assertEquals(0, cartPage.countNumberItemInCart(), "Verify number of product items in cart page");
	}

	@Test
	@DisplayName("Successfully remove 1 product on the Cart page")
	public void test05RemoveProductFromCart() {
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
		
		String[] product = new String[] { "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket" };
		String[] removedProduct = new String[] { "Sauce Labs Fleece Jacket" };
		int numerOfProduct = product.length;
		int numerOfRemovedProduct = removedProduct.length;

		productPage.addProductToCart(product);
		assertTrue(productPage.checkRemoveButtonDisplayedAndEnabled(product), "Verify 'Remove' button displayed added products");

		productPage.clickLinkToViewCart();
		assertEquals(numerOfProduct, cartPage.countNumberItemInCart(), "Verify number of product items in cart page");

		cartPage.removeProducts(removedProduct);
		assertEquals(numerOfProduct - numerOfRemovedProduct, cartPage.countNumberItemInCart(), "Verify number of product items in cart page after removing products");

		cartPage.clickContinueShopping();
		assertTrue(productPage.checkAddButtonDisplayedAndEnabled(removedProduct), "Verify 'Add to cart' buttons displayed for removed products");
		assertEquals(numerOfProduct - numerOfRemovedProduct, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", numerOfProduct - numerOfRemovedProduct));
	}

	@Test
	@DisplayName("Successfully remove all products on the Cart page")
	public void test06RemoveAllProductsFromCart() {
		productPage = new ProductPage(driver);
		cartPage = new CartPage(driver);
		
		String[] product = new String[] { "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket" };
		int numerOfProduct = product.length;


		productPage.addProductToCart(product);
		assertTrue(productPage.checkRemoveButtonDisplayedAndEnabled(product), "Verify 'Remove' button displayed added products");

		productPage.clickLinkToViewCart();
		assertEquals(numerOfProduct, cartPage.countNumberItemInCart(), "Verify number of product items in cart page");

		cartPage.removeProducts(product);
		assertEquals(0, cartPage.countNumberItemInCart(), "Verify number of product items in cart page after remove all products");

		cartPage.clickContinueShopping();
		assertTrue(productPage.checkAddButtonDisplayedAndEnabled(product), "Verify 'Add to cart' buttons displayed for removed products on product page");
		assertEquals(0, productPage.getNumberOfProductShownOnCartIcon(), String.format("Verify %n displayed on top of cart icon", 0));
	}

	@Test
	@DisplayName("User's cart displays items for each individual user")
	public void test07VerifyProductCartFromDifferentUser() {
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		menuPage = new MenuPage(driver);
		
		String[] product = new String[] { "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket" };
		int numerOfProduct = product.length;

		productPage.addProductToCart(product);
		assertEquals(numerOfProduct, productPage.getNumberOfProductShownOnCartIcon(), String.format("Cart for user standard_user contains %n items", productPage.getNumberOfProductShownOnCartIcon()));
		menuPage.logout();

		loginPage.loginWithUserNamePassword("visual_user", "secret_sauce");
		productPage.waitForProductTitleDisplayed();
		assertEquals(0, productPage.getNumberOfProductShownOnCartIcon(), String.format("Cart for user visual_user contains %n items", productPage.getNumberOfProductShownOnCartIcon()));
	}

	@AfterEach
	public void clean() {
		productPage = new ProductPage(driver);
		menuPage = new MenuPage(driver);
		cartPage = new CartPage(driver);
		
		productPage.clickLinkToViewCart();
		cartPage.removeAllProducts();
		menuPage.logout();
	}
}

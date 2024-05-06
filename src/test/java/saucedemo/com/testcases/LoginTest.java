package saucedemo.com.testcases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import saucedemo.com.extension.CaptureScreenShotExtension;
import saucedemo.com.extension.LoggingExtension;
import saucedemo.com.pages.LoginPage;
import saucedemo.com.pages.ProductPage;
import saucedemo.com.pages.MenuPage;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(CaptureScreenShotExtension.class)
@ExtendWith(LoggingExtension.class)
public class LoginTest extends BaseTest {
	LoginPage loginPage;
	ProductPage productPage;
	MenuPage menu;

	@ParameterizedTest
	@CsvSource({ "standard_user, secret_sauce", "problem_user, secret_sauce", "performance_glitch_user, secret_sauce", "visual_user, secret_sauce"})
	@DisplayName("Login successful with valid credentials")
	public void test01LoginWithValidCredential(String userName, String password) {
		loginPage = new LoginPage(driver);
		productPage = new ProductPage(driver);
		menu = new MenuPage(driver);
		
		loginPage.loginWithUserNamePassword(userName, password);
		productPage.waitForProductTitleDisplayed();
		assertEquals(productPage.getTitle(), "Products", "Verify product page title");
		menu.logout();
	}

	@Test
	@DisplayName("Login unsuccessful with blank account and password")
	public void test02LoginWithBlank() {
		loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Username is required", "Verify error message");
	}

	@Test
	@DisplayName("Login unsuccessful with non existing account")
	public void test03LoginWithNonExistingAccount() {
		loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("non_existing_acc", "secret_sauce");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Username and password do not match any user in this service", "Verify error message");
	}

	@Test
	@DisplayName("Login unsuccessful with empty password")
	public void test04LoginWithEmptyPassword() {
		loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("standard_user", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Password is required", "Verify error message");
	}

	@Test
	@DisplayName("Login unsuccessful with invalid password")
	public void test05LoginWithIncorrectPassword() {
		loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("standard_user", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Password is required", "Verify error message");
	}

	@Test
	@DisplayName("Login unsuccessful with locked account")
	public void test06LoginWithLockedAccount() {
		loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("locked_out_user", "secret_sauce");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Sorry, this user has been locked out.", "Verify error message");
	}

	@Test
	@DisplayName("Verify that the close error message button functions correctly.")
	public void test07CloseErrorMessage() {
		loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("standard_user", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Password is required", "Verify error message");
		loginPage.closeLoginErrorMessage();
		assertFalse(loginPage.checkErrorMessageExisting(), "Verify error message after removing");
	}
	
	@AfterEach
	public void clean() {
		loginPage = new LoginPage(driver);
		loginPage.resetLoginTextField();
	}

}

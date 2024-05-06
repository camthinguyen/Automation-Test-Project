package saucedemo.com.testcases;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
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
import saucedemo.com.pages.MenuComponent;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(CaptureScreenShotExtension.class)
@ExtendWith(LoggingExtension.class)
public class LoginTest extends BaseTest {

	@ParameterizedTest
	@CsvSource({ "standard_user,secret_sauce", "problem_user,secret_sauce", "performance_glitch_user,secret_sauce" })
	public void test01LoginWithValidCredential(String userName, String password) {
		LoginPage loginPage = new LoginPage(driver);
		ProductPage productPage = new ProductPage(driver);
		MenuComponent menu = new MenuComponent(driver);
		loginPage.loginWithUserNamePassword(userName, password);
		assertEquals(productPage.getTitle(), "Swag Labs");
		menu.logout();
	}

	@Test
	public void test02LoginWithBlank() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Username is required");
	}

	@Test
	public void test03LoginWithNonExistingAccount() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("non_existing_acc", "secret_sauce");
		assertEquals(loginPage.getLoginErrorMessage(),
				"Epic sadface: Username and password do not match any user in this service");
	}

	@Test
	public void test04LoginWithEmptyPassword() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("standard_user", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Password is required");
	}

	@Test
	public void test05LoginWithIncorrectPassword() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("standard_user", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Password is required");
	}

	@Test
	public void test06LoginWithLockedAccount() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("locked_out_user", "secret_sauce");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Sorry, this user has been locked out.");
	}

	@Test
	public void test07CloseErrorMessage() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginWithUserNamePassword("standard_user", "");
		assertEquals(loginPage.getLoginErrorMessage(), "Epic sadface: Password is required");
		loginPage.closeLoginErrorMessage();
		assertFalse(loginPage.checkErrorMessageExisting());
	}
	
	@AfterEach
	public void clean() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.resetLoginTextField();
	}

}

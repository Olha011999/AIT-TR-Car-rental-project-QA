package car_rent.rent_page;

import car_rent.core.TestBase;
import car_rent.pages.AccountPage;
import car_rent.pages.HomePage;
import car_rent.pages.LoginPage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).clickLoginPage();
    }

@DisplayName("authorizationPositiveTest")
    @Test
    public void authorizationPositiveTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем видимость элемента "My Account"
        AccountPage accountPage = app.getAccountPage(); // получаем новую версию после логина
        assertTrue(accountPage.isMyAccountVisible(), "The 'My Account' element is visible");
        shouldRunTearDown = false;
    }

    @DisplayName("authorizationWithIncorrectLoginNegativeTest")
    @Test
    public void authorizationWithIncorrectLoginNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("incorrectEmail@gmail.com");
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with incorrect login");
        shouldRunTearDown = false;
    }

    @DisplayName("authorizationWithIncorrectPasswordNegativeTest")
    @Test
    public void authorizationWithIncorrectPasswordNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword("IncorrectPassword");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with incorrect password");
        shouldRunTearDown = false;
    }

    @DisplayName("authorizationWithInvalidLoginNegativeTest")
    @Test
    public void authorizationWithInvalidLoginNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("invalidEmail");
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with invalid login");
        shouldRunTearDown = false;
    }

    @DisplayName("authorizationWithInvalidPasswordNegativeTest")
    @Test
    public void authorizationWithInvalidPasswordNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword("123");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with invalid password");
        shouldRunTearDown = false;
    }

    @DisplayName("emailFieldIsNotFilledInNegativeTest")
    @Test
    public void emailFieldIsNotFilledInNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(""); // Оставляем поле пустым
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible when email is not filled");
        shouldRunTearDown = false;
    }

    @DisplayName("passwordFieldIsNotFilledInNegativeTest")
    @Test
    public void passwordFieldIsNotFilledInNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword(""); // Оставляем поле пустым
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible when password is not filled");
        shouldRunTearDown = false;
    }

    @AfterEach
    public void tearDown() {
        app.driver.close();
    }

}

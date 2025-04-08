package car_rent.rent_page;

import car_rent.core.TestBase;
import car_rent.pages.AccountPage;
import car_rent.pages.HomePage;
import car_rent.pages.LoginPage;
import car_rent.pages.RegistrationPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LoginTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
    }


    @Test
    public void authorizationPositiveTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем видимость элемента "My Account"
        AccountPage accountPage = app.getAccountPage(); // получаем новую версию после логина
        assertTrue("The 'My Account' element is visible", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @Test
    public void authorizationWithIncorrectLoginNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("incorrectEmail@gmail.com");
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse( "The 'My Account' element is visible with incorrect login", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @Test
    public void authorizationWithIncorrectPasswordNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword("IncorrectPassword");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse( "The 'My Account' element is visible with incorrect password", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @Test
    public void authorizationWithInvalidLoginNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("invalidEmail");
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse( "The 'My Account' element is visible with invalid login", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @Test
    public void authorizationWithInvalidPasswordNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword("123");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse("The 'My Account' element is visible with invalid password", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @Test
    public void emailFieldIsNotFilledInNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(""); // Оставляем поле пустым
        loginPage.enterPassword("Password@1");
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse( "The 'My Account' element is visible when email is not filled", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @Test
    public void passwordFieldIsNotFilledInNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43Test1@gmail.com");
        loginPage.enterPassword(""); // Оставляем поле пустым
        loginPage.clickLoginButton();
        // Проверяем, что элемент "My Account" не виден
        AccountPage accountPage = app.getAccountPage();
        assertFalse("The 'My Account' element is visible when password is not filled", accountPage.isMyAccountVisible());
        shouldRunTearDown = false;
    }

    @AfterEach
    public void tearDown() {
        app.driver.close();
    }

}

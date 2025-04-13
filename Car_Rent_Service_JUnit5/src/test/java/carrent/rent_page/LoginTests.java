package carrent.rent_page;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import carrent.core.TestBase;
import carrent.pages.AccountPage;
import carrent.pages.HomePage;
import carrent.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
    }


    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginData")
    public void authorizationPositiveTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertTrue(accountPage.isMyAccountVisible(), "The 'My Account' element is visible");
    }

    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginWithIncorrectLoginData")
    public void authorizationWithIncorrectLoginNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with incorrect login");
    }

    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginWithIncorrectPasswordData")
    public void authorizationWithIncorrectPasswordNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with incorrect password");
    }

    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginWithInvalidLoginData")
    public void authorizationWithInvalidLoginNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with invalid login");
    }


    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginWithInvalidPasswordData")
    public void authorizationWithInvalidPasswordNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible with invalid password");
    }


    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginEmailFieldIsNotFilledData")
    public void emailFieldIsNotFilledInNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible when email is not filled");
    }


    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#customerLoginPasswordFieldIsNotFilledData")
    public void passwordFieldIsNotFilledInNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AccountPage accountPage = app.getAccountPage();
        assertFalse(accountPage.isMyAccountVisible(), "The 'My Account' element is visible when password is not filled");
    }

    @AfterEach
    public void tearDown() {
        if (app.getAddCarPage() != null) {
            app.driver.quit();
        }
    }
}
package car_rent.rent_page;

import car_rent.core.TestBase;
import car_rent.pages.AdminPage;
import car_rent.pages.HomePage;
import car_rent.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AdminLoginTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
    }

    @Test
    public void adminAuthorizationPositiveTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("admin@gmail.com");
        loginPage.enterPassword("Yyyyyyy12345!");
        loginPage.clickLoginButton();
        // Проверяем видимость элемента "My Account"
        AdminPage adminPage = app.getAdminPage(); // получаем новую версию после логина
        assertTrue(adminPage.isMyAccountVisible(), "The 'My Account' element is visible");
        shouldRunTearDown = false;

    }

    @Test
    public void adminAuthorizationEmptyEmailNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("");
        loginPage.enterPassword("Yyyyyyy12345!");

        // Скроллим до кнопки логина (если надо)
        loginPage.scrollToLoginButton();

        // Проверяем, что кнопка логина неактивна
        assertFalse(loginPage.isLoginButtonEnabled(), "Login button should be disabled when email is empty");
    }

    @Test
    public void adminAuthorizationEmptyPasswordNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("admin@gmail.com");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        // Скроллим до кнопки логина (если надо)
        loginPage.scrollToLoginButton();

        // Проверяем, что кнопка логина неактивна
        assertFalse(loginPage.isLoginButtonEnabled(), "Login button should be disabled when email is empty");
    }

    @Test
    public void adminAuthorizationInvalidCredentialsNegativeTest() {
        // Переходим на страницу логина
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("admi@gmail.com");
        loginPage.enterPassword("Yyyyyyy12345!");
        loginPage.clickLoginButton();

        // Check error message
        String expectedMessage = "Password or email incorrect";
        String actualMessage = app.getLoginPage().getErrorMessage();
        assertEquals(expectedMessage, actualMessage, "Error message should match expected text for invalid login");
        shouldRunTearDown = false;
    }

    @AfterEach
    public void tearDown() {
        app.stop();
    }
}

package carrent.admin_tests;

import carrent.core.TestBase;
import carrent.admin_pages.AdminPage;
import carrent.pages.HomePage;
import carrent.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;


public class AdminLoginTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
    }

    @ParameterizedTest
    @Tag("smoky")
    @MethodSource("carrent.utils.DataProvider#adminLoginData")
    public void testAdminAuthorizationPositiveTest(String email, String password) {
        new LoginPage(app.driver, app.wait)
        .adminLogIn(email, password);
        AdminPage adminPage = app.getAdminPage();
        assertTrue(adminPage.isMyAccountVisible(), "The 'My Account' element is visible");
    }

    @ParameterizedTest
    @Tag("smoky")
    @MethodSource("carrent.utils.DataProvider#adminLoginEmptyEmailData")
    public void testAdminAuthorizationEmptyEmailNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AdminPage adminPage = app.getAdminPage();
        assertFalse(adminPage.isMyAccountVisible(), "The 'Admin' element is visible with incorrect email");
    }

    @ParameterizedTest
    @Tag("smoky")
    @MethodSource("carrent.utils.DataProvider#adminLoginEmptyPasswordData")
    public void testAdminAuthorizationEmptyPasswordNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AdminPage adminPage = app.getAdminPage();
        assertFalse(adminPage.isMyAccountVisible(), "The 'Admin' element is visible with incorrect password");
    }

    @ParameterizedTest
    @Tag("smoky")
    @MethodSource("carrent.utils.DataProvider#adminLoginInvalidCredentialsData")
    public void testAdminAuthorizationInvalidCredentialsNegativeTest(String email, String password) {
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        AdminPage adminPage = app.getAdminPage();
        assertFalse(adminPage.isMyAccountVisible(), "The 'My Account' element is visible with incorrect credentials");
        String expectedMessage = "Password or email incorrect";
        String actualMessage = app.getLoginPage().getErrorMessage();
        assertEquals(expectedMessage, actualMessage, "Error message should match expected text for invalid login");
        shouldRunTearDown = false;
    }

    @AfterEach
    public void tearDown() {
        if (app.getAddCarPage() != null) {
            app.driver.quit();
        }
    }
}

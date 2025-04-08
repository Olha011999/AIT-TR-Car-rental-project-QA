package car_rent;

import car_rent.core.ApplicationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Logger;

import static org.testng.AssertJUnit.*;

public class AuthorizationTests {
    private static final Logger logger = Logger.getLogger(AuthorizationTests.class.getName());

    private ApplicationManager app = new ApplicationManager();

    @BeforeMethod
    public void setUp() {
        logger.info("Application initialization");
        app.init();
    }

    @AfterMethod
    public void tearDown() {
        app.stop();
    }

    @Test
    public void testAdminAuthorizationPositiveTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        assertTrue(app.home().isAdminDashboardVisible());
    }

    @Test
    public void testAdminAuthorizationEmptyEmailNegativeTest() {
        app.login().loginAsAdmin("", "Yyyyyyy12345!");
        app.login().scrollToLoginButton();
        assertFalse(app.login().isLoginButtonEnabled());
    }

    @Test
    public void testAdminAuthorizationEmptyPasswordNegativeTest() {
        app.login().loginAsAdmin("admin@gmail.com", "");
        app.login().scrollToLoginButton();
        assertFalse(app.login().isLoginButtonEnabled());
    }
    @Test
    public void testAdminAuthorizationInvalidCredentialsNegativeTest() {
        app.login().loginWithEmailAndPassword("admi@gmail.com", "yyyyyy12345!");

        String expectedMessage = "Password or email incorrect";
        String actualMessage = app.login().getErrorMessage();

        assertEquals(actualMessage, expectedMessage);
    }

}


package carrent.rent_page;

import carrent.core.TestBase;
import carrent.pages.HomePage;
import carrent.pages.RegistrationPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
        new RegistrationPage(app.driver, app.wait).clickSignUp();
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationDataProvider")
    public void registrationPositiveTest(String firstName, String lastName, String email, String password) {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms()
                .clickCreateButton()
                .verifySuccessMessage("Registration successful! Please check your email to confirm your registration.")
                .clickOkButton();
    }


    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationNegativeAlreadyExistData")
    public void registrationNegativeAlreadyExistTest(String firstName, String lastName, String email, String password) {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms()
                .clickCreateButton()
                .verifyErrorMessage("Error")
                .clickCancelButton();
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationWithInvalidEmailNegativeData")
    public void registrationWithInvalidEmailNegativeTest(String firstName, String lastName, String email, String password) {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms()
                .clickCreateButton();

        RegistrationPage registrationPage = app.getRegistrationPage();
        assertTrue(registrationPage.isLogInVisible(), "The 'Log In' element is visible");
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationWithInvalidPasswordData")
    public void registrationWithInvalidPasswordNegativeTest(String firstName, String lastName, String email, String password) {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms()
                .clickCreateButton();

        RegistrationPage registrationPage = app.getRegistrationPage();
        assertTrue(registrationPage.isPasswordMessageVisible());
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationNegativeInvalidFirstNameData")
    public void registrationNegativeInvalidFirstNameTest(String firstName, String lastName, String email, String password) {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms();
        RegistrationPage registrationPage = app.getRegistrationPage();
        assertFalse(registrationPage.isCreateButtonEnabled(), "The 'Create' button should be inactive if the field is empty.");
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationInvalidLastNameData")
    public void registrationInvalidLastNameNegativeTest(String firstName, String lastName, String email, String password) {
        RegistrationPage registrationPage = new RegistrationPage(app.driver, app.wait);
        registrationPage
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms();
        assertFalse(registrationPage.isCreateButtonEnabled(), "The 'Create' button should be inactive if the field is empty.");
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationEmptyEmailData")
    public void registrationEmptyEmailNegativeTest(String firstName, String lastName, String email, String password) {
        RegistrationPage registrationPage = new RegistrationPage(app.driver, app.wait);
        registrationPage
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms();
        assertFalse(registrationPage.isCreateButtonEnabled(), "The 'Create' button should be inactive if the field is empty.");
    }

    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#registrationEmptyPasswordData")
    public void registrationEmptyPasswordNegativeTest(String firstName, String lastName, String email, String password) {
        RegistrationPage registrationPage = new RegistrationPage(app.driver, app.wait);
        registrationPage
                .enterPersonalData(firstName, lastName, email, password)
                .agreeToTerms();
        assertFalse(registrationPage.isCreateButtonEnabled(), "The 'Create' button should be inactive if the field is empty.");
    }

    @AfterEach
    public void tearDown() {
        if (app.getAddCarPage() != null) {
            app.driver.quit();
        }
    }
}
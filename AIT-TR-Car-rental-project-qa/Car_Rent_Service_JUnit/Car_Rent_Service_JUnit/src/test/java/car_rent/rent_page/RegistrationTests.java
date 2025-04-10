package car_rent.rent_page;

import car_rent.core.TestBase;
import car_rent.pages.HomePage;
import car_rent.pages.RegistrationPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.FixedWidth;

public class RegistrationTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).clickLoginPage();
        new RegistrationPage(app.driver, app.wait).clickSignUp();
    }

    @DisplayName("registrationPositiveTest")
    @Test
    public void registrationPositiveTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "Snow", "johnsnow55_test1@gmail.com", "Password1@")
                .agreeToTerms()
                .clickCreateButton()
                .verifySuccessMessage("Registration successful! Please check your email to confirm your registration.")
                .clickOkButton();
    }

    @DisplayName("registrationNegativeAlreadyExistTest")
    @Test
    public void registrationNegativeAlreadyExistTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "Snow", "test_1234@gmail.com", "Password1@")
                .agreeToTerms()
                .clickCreateButton()
                .verifyErrorMessage("Error")
                .clickCancelButton();
    }

    @DisplayName("registrationNegativeInvalidEmailTest")
    @Test
    public void registrationNegativeInvalidEmailTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "Snow", "test-gmail", "Password1@")
                .agreeToTerms()
                .clickCreateButton()
                .checkLogIn();
    }

    @DisplayName("registrationNegativeInvalidPasswordTest")
    @Test
    public void registrationNegativeInvalidPasswordTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "Snow", "test_12345@gmail.com", "Password@")
                .agreeToTerms()
                .clickCreateButton()
                .checkLogIn();
    }

    @DisplayName("registrationNegativeInvalidFirstNameTest")
    @Test
    public void registrationNegativeInvalidFirstNameTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("", "Snow", "test_123456@gmail.com", "Password@")
                .agreeToTerms()
                .clickCreateButton()
                .checkLogIn();
    }

    @DisplayName("registrationNegativeInvalidLastNameTest")
    @Test
    public void registrationNegativeInvalidLastNameTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "", "test_123457@gmail.com", "Password@")
                .agreeToTerms()
                .clickCreateButton()
                .checkLogIn();
    }

    @DisplayName("registrationNegativeEmptyEmailTest")
    @Test
    public void registrationNegativeEmptyEmailTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "Snow", "", "Password@")
                .agreeToTerms()
                .clickCreateButton()
                .checkLogIn();
    }

    @DisplayName("registrationNegativeEmptyPasswordTest")
    @Test
    public void registrationNegativeEmptyPasswordTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalData("John", "Snow", "test_123458@gmail.com", "")
                .agreeToTerms()
                .clickCreateButton()
                .checkLogIn();
    }

    @AfterEach
    public void tearDown() {
        app.driver.close();
    }
}
package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(css = "input[name='firstName']")
    WebElement userFirstName;
    @FindBy(css = "input[name='lastName']")
    WebElement userLastName;
    @FindBy(css = "input[name='email']")
    WebElement userEmail;
    @FindBy(css = "input[name='password']")
    WebElement userPassword;

    public RegistrationPage enterPersonalData(String firstName, String lastName, String email, String password) {
        type(userFirstName, firstName);
        type(userLastName, lastName);
        type(userEmail, email);
        type(userPassword, password);
        return this;
    }

    @FindBy(css = "input[type='checkbox']")
    WebElement termsCheckbox;

    public RegistrationPage agreeToTerms() {
        Actions actions = new Actions(driver);
        actions.moveToElement(termsCheckbox).click().perform();
        return this;
    }

    @FindBy(css = "button[type='submit']")
    WebElement createButton;

    public RegistrationPage clickCreateButton() {
        click(createButton);
        return this;
    }

    //* Sign Up
    @FindBy(xpath = "//a[normalize-space(text())='Sign up']")
    WebElement signUp;

    public void clickSignUp() {
        click(signUp);
        new RegistrationPage(driver, wait);
    }


    //* Check Successfully Registration
    @FindBy(xpath = "(//h3[text()='Success']/following-sibling::p)[2]")
    WebElement successMessage;

    public RegistrationPage verifySuccessMessage(String messageText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(successMessage));

        String actualText = successMessage.getText().trim();
        System.out.println("Actual success message: [" + actualText + "]");

        assert actualText.contains(messageText) : "Expected to find: [" + messageText + "] in actual text";

        return this;
    }


    //* Check Error Registration
    @FindBy(xpath = "//h3[normalize-space(text())='Error']")
    WebElement errorMessage;

    public RegistrationPage verifyErrorMessage(String error) {
        assert errorMessage.getText().contains(error);
        return this;
    }

    @FindBy(xpath = "(//button[@type='button'])[2]")
    WebElement okButton;

    public void clickOkButton() {
        click(okButton);
    }

    @FindBy(css = "button[type='button']")
    WebElement cancelButton;

    public void clickCancelButton() {
        click(cancelButton);
    }

    //* Check UnSuccessfully Registration
    @FindBy(xpath = "(//a[@data-discover='true'])[3]")
    WebElement logIn;

    public void checkLogIn() {
        wait.until(ExpectedConditions.visibilityOf(logIn)).isDisplayed();
    }
}

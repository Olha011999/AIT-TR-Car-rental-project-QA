package carrent.pages;

import carrent.core.BasePage;
import org.openqa.selenium.TakesScreenshot;
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

    @FindBy (css = "input[type='checkbox']")
    WebElement termsCheckbox;

    public RegistrationPage agreeToTerms() {
    Actions actions = new Actions(driver);
    actions.moveToElement(termsCheckbox).click().perform();
        return this;
    }

    @FindBy (css = "button[type='submit']")
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
    @FindBy (xpath = "(//h3[text()='Success']/following-sibling::p)[2]")
    WebElement successMessage;
    public RegistrationPage verifySuccessMessage(String messageText) {
        assert successMessage.getText().contains(messageText);
        return this;
    }

    //* Check Error Registration
    @FindBy (xpath = "//h3[normalize-space(text())='Error']")
    WebElement errorMessage;
    public RegistrationPage verifyErrorMessage(String error) {
        assert errorMessage.getText().contains(error);
        return this;
    }

    @FindBy (xpath = "(//button[@type='button'])[2]")
    WebElement okButton;

    public void clickOkButton() {
        click(okButton);
    }

    @FindBy (css = "button[type='button']")
    WebElement cancelButton;
    public void clickCancelButton() {
        click(cancelButton);
    }

    //* Check UnSuccessfully Registration
    @FindBy (xpath = "(//a[@data-discover='true'])[3]")
    WebElement logIn;

    public void checkLogIn() {
        wait.until(ExpectedConditions.visibilityOf(logIn)).isDisplayed();
    }

    @FindBy(xpath = "//a[normalize-space(text())='Log in']")
    private WebElement logInElement;

    public boolean isLogInVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(logInElement)); //ожидания видимости элемента
            return logInElement.isDisplayed();
        } catch (Exception e) {
            if (driver instanceof TakesScreenshot) {
                String screenshotPath = takeScreenshot(); // Метод из TestBase
                System.out.println("Error checking the item. Screenshot:" + screenshotPath);
            }
            return false;
        }
    }

    @FindBy(xpath = "//div[normalize-space(text())='Password must include an uppercase letter, a number, and a special character (# ? ! @ $ % ^ & * -)']")
    private WebElement incorrectPasswordMessageElement;

    public boolean isPasswordMessageVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(incorrectPasswordMessageElement));
            return incorrectPasswordMessageElement.isDisplayed();
        } catch (Exception e) {
            if (driver instanceof TakesScreenshot) {
                String screenshotPath = takeScreenshot();
                System.out.println("Error checking the item. Screenshot:" + screenshotPath);
            }
            return false;
        }
    }

    @FindBy(xpath = "//div[normalize-space(text())='Password must be at least 8 characters']")
    private WebElement incorrectLittlePasswordMessageElement;

    // Метод для ожидания сообщения о пароле менее 8 символов "Password must be at least 8 characters"
    public boolean isLittlePasswordMessageVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(incorrectLittlePasswordMessageElement)); //ожидания видимости элемента
            return incorrectLittlePasswordMessageElement.isDisplayed();
        } catch (Exception e) {
            if (driver instanceof TakesScreenshot) {
                String screenshotPath = takeScreenshot(); // Метод из TestBase
                System.out.println("Error checking the item. Screenshot:" + screenshotPath);
            }
            return false;
        }
    }

    public boolean isCreateButtonEnabled() {
        return createButton.isEnabled();
    }
}

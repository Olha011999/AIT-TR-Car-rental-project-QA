package carrent.pages;

import carrent.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[name='email']")
    WebElement userEmail;

    @FindBy(css = "input[name='password']")
    WebElement userPassword;

    @FindBy(css = "button[type='submit']")
    WebElement loginButton;

    public void enterEmail(String email) {
        userEmail.sendKeys(email);
    }

    public void enterPassword(String password) {
        userPassword.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void adminLogIn(String mail, String password) {
        userEmail.sendKeys(mail);
        userPassword.sendKeys(password);
        loginButton.click();
    }

    @FindBy(xpath = "//p[contains(text(),'Password or email incorrect')]")
    WebElement errorsMessage;

    public String getErrorMessage() {
        errorsMessage.getText();
        return errorsMessage.getText();
    }
}


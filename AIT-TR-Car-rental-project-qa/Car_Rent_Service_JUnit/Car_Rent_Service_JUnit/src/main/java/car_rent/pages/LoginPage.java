package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "email")
    WebElement email;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(xpath = "//button[contains(text(),'Login')]")
    WebElement loginButton;

    public void enterEmail(String email) {
        this.email.sendKeys(email);
    }

    public void enterPassword(String password) {
        this.password.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    @FindBy(xpath = "//p[contains(text(),'Password or email incorrect')]")
    WebElement errorsMessage;

    public String getErrorMessage() {
        errorsMessage.getText();
        return errorsMessage.getText();
    }

    public void loginAsAdmin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
    }

    // ✅ Новый метод: проверить активна ли кнопка
    public boolean isLoginButtonEnabled() {
        return loginButton.isEnabled();
    }

    // ✅ Новый метод: скролл до кнопки
    public void scrollToLoginButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
    }
}


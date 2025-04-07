package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private  By loginPageButton = By.xpath("//a[@href='#/login' and contains(text(), 'Log in')]");

    private By emailInput = By.name("email");
    private By passwordInput = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit' and contains(text(), 'Login')]");

    private By adminPageButton = By.xpath("//a[@href='#/admin' and contains(text(), 'Admin')]");



    public void loginAsAdmin(String email, String password) {
        //driver.findElement(loginPageButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(loginPageButton)).click();

        //driver.findElement(emailInput).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);

        //driver.findElement(passwordInput).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);

        //driver.findElement(loginButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        //driver.findElement(adminPageButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(adminPageButton)).click();
    }

    public boolean isLoginButtonEnabled() {
        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[1]/div/form/button"));
        return button.isEnabled();
    }

    public void scrollToLoginButton() {
        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[1]/div/form/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
    }


    public void scrollToAdminPageButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(adminPageButton));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);

        WebElement clickableButton = wait.until(ExpectedConditions.elementToBeClickable(adminPageButton));
        clickableButton.click();
    }

    public String getErrorMessage() {
        WebElement errorText = driver.findElement(By.xpath("//p[contains(text(),'Password or email incorrect')]"));
        return errorText.getText();
    }

    public void loginWithEmailAndPassword(String email, String password) {

        driver.findElement(loginPageButton).click();
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public void loginAsUser(String email, String password) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(loginPageButton)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);

        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

    }

}
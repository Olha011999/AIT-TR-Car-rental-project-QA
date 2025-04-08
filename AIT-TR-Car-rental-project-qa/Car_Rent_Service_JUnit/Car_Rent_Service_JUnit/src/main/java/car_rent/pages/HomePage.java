package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //* Login
    @FindBy(xpath = "//a[contains(text(),'Log in')]")
    WebElement loginPageButton;
    public void clickLoginPage() {
        click(loginPageButton);
        new HomePage(driver, wait);
    }

}

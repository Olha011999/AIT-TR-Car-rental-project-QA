package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //* Login
    @FindBy(xpath = "(//a[@data-discover='true'])[3]")
    WebElement login;
    public void selectLogin() {
        click(login);
        new HomePage(driver, wait);
    }
}

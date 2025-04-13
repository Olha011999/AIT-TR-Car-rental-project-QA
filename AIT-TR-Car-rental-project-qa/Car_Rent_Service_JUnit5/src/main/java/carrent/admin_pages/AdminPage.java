package carrent.admin_pages;

import carrent.core.BasePage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage extends BasePage {
    public AdminPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space(text())='Admin']")
    private WebElement adminElement;

    // Метод для ожидания элемента "My Account"
    public boolean isMyAccountVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(adminElement)); //ожидания видимости элемента
            return adminElement.isDisplayed();
        } catch (Exception e) {
            if (driver instanceof TakesScreenshot) {
                String screenshotPath = takeScreenshot(); // Метод из TestBase
                System.out.println("Error checking the item. Screenshot:" + screenshotPath);
            }
            return false;
        }
    }

    public AdminPage enterToTheAdminPanel() {
        wait.until(ExpectedConditions.elementToBeClickable(adminElement)).click();
        // Дождись, пока появится кнопка "Cars" — признак, что мы на админке
        wait.until(ExpectedConditions.visibilityOf(carsLink));
        return this;
    }

    @FindBy (xpath = "//button[normalize-space(text())='Cars']")
    WebElement carsLink;

    public CarsPage enterToTheCarsPage(){
        carsLink.click();
        return new CarsPage(driver,wait);
    }

    @FindBy (xpath = "//button[contains(text(),'Add car')]")
    WebElement carsButton;
    public AddCarPage clickAddCar() {
        carsButton.click();
        return new AddCarPage(driver, wait);
    }
}

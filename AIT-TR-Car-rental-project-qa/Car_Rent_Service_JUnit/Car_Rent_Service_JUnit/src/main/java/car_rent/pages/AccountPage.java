package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class AccountPage extends BasePage {

    public AccountPage(WebDriverWait wait, WebDriver driver) {
            super(driver, wait);
            PageFactory.initElements(driver, this);
        }

        @FindBy(xpath = "//a[normalize-space(text())='My Account']")
        private WebElement myAccountElement;

        // Метод для ожидания элемента "My Account"
        public boolean isMyAccountVisible() {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOf(myAccountElement)); //ожидания видимости элемента
                return myAccountElement.isDisplayed();
            } catch (Exception e) {
                if (driver instanceof TakesScreenshot) {
                    String screenshotPath = takeScreenshot(); // Метод из TestBase
                    System.out.println("Error checking the item. Screenshot:" + screenshotPath);
                }
                return false;
            }
        }
    }

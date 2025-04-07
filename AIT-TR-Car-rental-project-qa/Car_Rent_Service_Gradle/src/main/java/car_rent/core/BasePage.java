package car_rent.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Logger logger = Logger.getLogger(this.getClass().getName());

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public Logger getLogger() {
        return logger;
    }


    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void goToAdminPage() {
        driver.get("https://car-rental-cymg8.ondigitalocean.app/#/admin");
    }

    public void goToAllCars() {
        driver.get("https://car-rental-cymg8.ondigitalocean.app/#/admin/allCars");
    }

    public void click(WebElement element) {
        scrollToElement(element);
        element.click();
    }

    private void scrollToElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        waitForPageScrollToFinish();
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPageScrollToFinish() {
        wait.until(driver -> {
            double beforeScroll, afterScroll;
            do {
                beforeScroll = ((Number) js.executeScript("return window.scrollY;")).doubleValue();
                pause(50); // Ждём короткий промежуток времени
                afterScroll = ((Number) js.executeScript("return window.scrollY;")).doubleValue();
            } while (beforeScroll != afterScroll); // Если скролл ещё идёт, повторяем
            return true; // Выходим из ожидания, когда прокрутка остановилась
        });
    }

    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

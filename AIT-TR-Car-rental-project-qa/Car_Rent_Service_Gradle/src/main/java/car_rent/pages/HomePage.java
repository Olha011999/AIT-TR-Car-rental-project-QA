package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get("https://car-rental-cymg8.ondigitalocean.app/");
    }
    public boolean isAdminDashboardVisible() {
        return driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/div/div/nav/a[2]")).isDisplayed();
    }

}

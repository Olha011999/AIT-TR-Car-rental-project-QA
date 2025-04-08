package car_rent.pages;

import car_rent.core.BasePage;
import org.openqa.selenium.*;
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

    private By addCarButton = By.xpath("//*[@id=\"root\"]/div/main/div/div[1]/div/nav/button[1]");

    public void clickAddCar() {
        logger.info("Click the 'Add car' button");
        driver.findElement(addCarButton).click();
    }

    private By carsButton = By.xpath("//*[@id=\"root\"]/div/main/div/div[1]/div/nav/button[2]");
    public void clickCarsButton(){
        logger.info("Click the 'Cars' button");
        driver.findElement(carsButton).click();
    }

    private By bookingsButton = By.xpath("//*[@id=\"root\"]/div/main/div/div[1]/div/nav/button[3]");
    public void clickBooringsButton(){
        logger.info("Click the 'Boorings' button");
        driver.findElement(bookingsButton).click();
    }

    private By customersButton = By.xpath("//*[@id=\"root\"]/div/main/div/div[1]/div/nav/button[4]");
    public void clickCustomersButton(){
        logger.info("Click the 'Customers' button");
        driver.findElement(customersButton).click();
    }

    public boolean isAddCarButtonVisible() {
        try {
            WebElement addCarButton = driver.findElement(By.xpath("//*[@id='root']/div/main/div/div[1]/div/nav/button[1]"));
            return addCarButton.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAccessDeniedMessageDisplayed() {
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement accessDeniedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Access Denied')]")));

            return accessDeniedMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }


}

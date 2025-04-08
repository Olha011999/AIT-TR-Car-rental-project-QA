package car_rent.core;

import car_rent.pages.*;
import car_rent.pages.admin_pages.AddCarPage;
import car_rent.pages.admin_pages.CarsPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ApplicationManager {
    public WebDriver driver;
    public WebDriverWait wait;
    public BasePage basePage;
    public HomePage homePage;
    public LoginPage loginPage;
    public AddCarPage addCarPage;
    public CarsPage carsPage;
    public RegistrationPage registrationPage;

    public void init() {
        String browser = System.getProperty("browser", "chrome");

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
//            case "safari":
//                WebDriverManager.edgedriver().setup();
//                driver = new SafariDriver();
//                break;
            default: // Это резервный сценарий на случай, если значение browser не совпадает ни с одним из указанных случаев
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        //driver = new ChromeDriver();
        driver.get("https://car-rental-cymg8.ondigitalocean.app/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // неявное
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5)); // ожидание загрузки страницы
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        basePage = new BasePage(driver, wait);
        homePage = new HomePage(driver, wait);
        loginPage = new LoginPage(driver, wait);
        addCarPage = new AddCarPage(driver, wait);
        carsPage = new CarsPage(driver, wait);
        registrationPage = new RegistrationPage(driver, wait);
    }

    public BasePage getBasePage() {
        return basePage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public AccountPage getAccountPage() {
        return new AccountPage(wait, driver); // чтобы каждый раз был новый DOM
    }

    public AdminPage getAdminPage() {
        return new AdminPage(driver, wait);
    }

    public AddCarPage getAddCarPage() {
        return new AddCarPage(driver, wait);
    }

    public CarsPage getCarsPage() {
        return new CarsPage(driver, wait);
    }

    public RegistrationPage getRegistrationPage() {
        return new RegistrationPage(driver, wait);
    }

    public void stop() {
        driver.quit();
    }

}

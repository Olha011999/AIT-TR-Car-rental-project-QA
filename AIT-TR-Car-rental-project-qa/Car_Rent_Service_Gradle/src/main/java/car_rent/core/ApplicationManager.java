package car_rent.core;

import car_rent.pages.AddCarPage;
import car_rent.pages.AdminPage;
import car_rent.pages.HomePage;
import car_rent.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class ApplicationManager {
    private WebDriver driver;
    private WebDriverWait wait;
    private Logger logger = Logger.getLogger(ApplicationManager.class.getName());

    private LoginPage loginPage;
    private HomePage homePage;
    private AdminPage adminPage;
    private AddCarPage addCarPage;
    private BasePage basePage;

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
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        driver.manage().window().setPosition(new Point(2500, 0));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://car-rental-cymg8.ondigitalocean.app");
        basePage = new BasePage(driver, wait);
        homePage = new HomePage(driver, wait);
        loginPage = new LoginPage(driver, wait);
        addCarPage = new AddCarPage(driver, wait);
        adminPage = new AdminPage(driver, wait);
    }


    public WebDriver getDriver() {
        return driver;
    }

    public LoginPage login() {
        return loginPage;
    }

    public HomePage home() {
        return homePage;
    }

    public AdminPage admin() {
        return adminPage;
    }

    public AddCarPage car() {
        return addCarPage;
    }

    public BasePage getBasePage() {
        return basePage;
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

}


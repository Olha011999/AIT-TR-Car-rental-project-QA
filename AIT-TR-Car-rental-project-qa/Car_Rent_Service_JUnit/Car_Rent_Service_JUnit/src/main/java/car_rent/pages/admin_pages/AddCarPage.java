package car_rent.pages.admin_pages;

import car_rent.pages.AdminPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.util.logging.Logger;

public class AddCarPage extends AdminPage {

    public AddCarPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public By brand = By.name("brand");
    public By model = By.name("model");
    public By year = By.name("year");
    public By bodyType = By.name("bodyType");
    public By fuelType = By.name("fuelType");
    public By transmissionType = By.name("transmissionType");
    public By pricePerDay = By.name("dayRentalPrice");
    public By image = By.name("carImage");
    public By saveButton = By.xpath("//*[@id=\"root\"]/div/main/div/div[2]/div/form/div[2]/button");

    public void fillCarForm(String brandText, String modelText, String yearText,
                            String bodyTypeText, String fuelTypeText, String transmissionTypeText,
                            String pricePerDayText, String imagePath) {
        logger.info("Filling out the form for adding a car");

        driver.findElement(brand).sendKeys(brandText);
        driver.findElement(model).sendKeys(modelText);
        driver.findElement(year).sendKeys(yearText);
        driver.findElement(bodyType).sendKeys(bodyTypeText);
        driver.findElement(fuelType).sendKeys(fuelTypeText);
        driver.findElement(transmissionType).sendKeys(transmissionTypeText);
        driver.findElement(pricePerDay).sendKeys(pricePerDayText);

        WebElement upload = driver.findElement(image);
        upload.sendKeys(imagePath);

        logger.info("Form completed");
    }

    public void submitCarForm() {
        logger.info("Saving the car");
        driver.findElement(saveButton).click();
    }


    public boolean areRequiredFieldsVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(brand));

            By[] locators = {
                    brand, model, year, bodyType, fuelType, transmissionType,
                    pricePerDay, image, saveButton
            };

            for (By locator : locators) {
                WebElement el = driver.findElement(locator);
                js.executeScript("arguments[0].scrollIntoView(true);", el);
                if (!el.isDisplayed()) {
                    return false;
                }
            }

            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean isErrorMessageDisplayed(String carBrandIsRequired) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            boolean isBrandErrorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Car brand is required')]"))).isDisplayed();
            boolean isModelErrorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Car model is required')]"))).isDisplayed();
            boolean isYearErrorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Year when car was produced is required')]"))).isDisplayed();
            boolean isFuelTypeErrorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Car fuel type is required')]"))).isDisplayed();
            boolean isTransmissionErrorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Car transmission type is required')]"))).isDisplayed();
            boolean isPriceErrorVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Price per day is required')]"))).isDisplayed();

            return isBrandErrorVisible && isModelErrorVisible && isYearErrorVisible &&
                    isFuelTypeErrorVisible && isTransmissionErrorVisible && isPriceErrorVisible;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }


    public boolean isImageUploadAccepted(String imagePath) {

        String lowerCaseImagePath = imagePath.toLowerCase();
        if (!(lowerCaseImagePath.endsWith(".jpg") || lowerCaseImagePath.endsWith(".jpeg") || lowerCaseImagePath.endsWith(".png"))) {
            System.out.println("Unsupported file format. Only .jpg, .jpeg and .png are supported.");
            return false;
        }

        File file = new File(imagePath);
        if (!file.exists()) {
            System.out.println("Файл не найден: " + imagePath);
            return false;
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("carImage")));
            fileInput.sendKeys(imagePath);

            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPriceFieldInvalid() {
        WebElement priceInput = driver.findElement(pricePerDay);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (Boolean) js.executeScript("return arguments[0].validity.valid === false;", priceInput);
    }

    public boolean isSuccessAlertVisible(String theCarIsSaved) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'The car is saved')]")
            ));
            return toast.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
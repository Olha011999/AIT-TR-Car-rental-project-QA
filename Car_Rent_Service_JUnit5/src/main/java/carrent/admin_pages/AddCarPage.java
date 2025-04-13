package carrent.admin_pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class AddCarPage extends AdminPage {
    public AddCarPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private By brand = By.name("brand");
    private By model = By.name("model");
    private By year = By.name("year");
    private By bodyType = By.cssSelector("select[name='type']");
    private By fuelType = By.name("fuelType");
    private By transmissionType = By.name("transmissionType");
    private By pricePerDay = By.name("dayRentalPrice");
    private By image = By.name("carImage");
    public By saveButton = By.xpath("//*[@id=\"root\"]/div/main/div/div[2]/div/form/div[2]/button");

    public void fillCarForm(String brandText, String modelText, String yearText,
                            String bodyTypeText, String fuelTypeText, String transmissionTypeText,
                            String pricePerDayText, String imagePath) {

        driver.findElement(brand).sendKeys(brandText);
        driver.findElement(model).sendKeys(modelText);
        driver.findElement(year).sendKeys(yearText);
        driver.findElement(bodyType).sendKeys(bodyTypeText);
        driver.findElement(fuelType).sendKeys(fuelTypeText);
        driver.findElement(transmissionType).sendKeys(transmissionTypeText);
        driver.findElement(pricePerDay).sendKeys(pricePerDayText);

        WebElement upload = driver.findElement(image);
        upload.sendKeys(imagePath);

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

    public void submitCarForm() {
        driver.findElement(saveButton).click();
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

    public boolean isPriceValidationHintVisible(String expectedHintText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            // Ждем, пока появится <p> с нужным классом и текстом
            WebElement hint = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class, 'text-red-500') and contains(text(), '" + expectedHintText + "')]")
            ));
            System.out.println("Фактический текст: [" + hint.getText() + "]");
            return hint.getText().trim().contains(expectedHintText);
        } catch (TimeoutException | StaleElementReferenceException e) {
            return false;
        }
    }



    public boolean isValidationHintVisible (String expectedText) {
        List<WebElement> hints = driver.findElements(By.xpath("//*[contains(@class,'text-red-500') and (self::p or self::div)]"));
        return hints.stream().anyMatch(e -> e.getText().trim().equals(expectedText));
    }

}


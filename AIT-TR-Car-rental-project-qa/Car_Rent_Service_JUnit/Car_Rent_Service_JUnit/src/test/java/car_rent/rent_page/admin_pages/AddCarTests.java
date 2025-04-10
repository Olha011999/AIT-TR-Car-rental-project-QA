package car_rent.rent_page.admin_pages;

import car_rent.core.TestBase;
import car_rent.pages.AdminPage;
import car_rent.pages.HomePage;
import car_rent.pages.LoginPage;
import car_rent.pages.admin_pages.AddCarPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


public class AddCarTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(AddCarTests.class);

    @BeforeEach
    public void setUp() throws InterruptedException {
        logger.info("Initializing test...");

        // Переходим на страницу логина и авторизуемся
        new HomePage(app.driver, app.wait).clickLoginPage();
        new LoginPage(app.driver, app.wait).loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        new LoginPage(app.driver, app.wait).clickLoginButton();
        new AdminPage(app.driver, app.wait).goToAdmin();
        new AdminPage(app.driver, app.wait).clickAddCar();
        new AdminPage(app.driver, app.wait).scrollDownSlowly();
    }

    @DisplayName("allRequiredFieldsShouldBeVisiblePositiveTest")
    @Test
    public void allRequiredFieldsShouldBeVisiblePositiveTest() {
        logger.info("Starting test: allRequiredFieldsShouldBeVisible");

        AddCarPage addCarPage = app.getAddCarPage();

        boolean allVisible = addCarPage.areRequiredFieldsVisible();

        assertTrue(allVisible, "Not all required fields are visible on the Add Car page");
        logger.info("Test completed: allRequiredFieldsShouldBeVisible");
    }

    @DisplayName("shouldShowSuccessAlertAfterAddingCarPositiveTest")
    @Test
    public void shouldShowSuccessAlertAfterAddingCarPositiveTest() {
        logger.info("Starting test: shouldShowSuccessAlertAfterAddingCar");

        AddCarPage addCarPage = app.getAddCarPage();

        addCarPage.fillCarForm(
                "Toyota", "Corolla", "2020", "Sedan", "Petrol", "Automatic",
                "100", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg"
        );

        addCarPage.submitCarForm();

        assertTrue(addCarPage.isSuccessAlertVisible("The car is saved"),
                "Success alert should be visible after saving the car");

        logger.info("Test completed: shouldShowSuccessAlertAfterAddingCar");
    }

@DisplayName("showErrorMessagesIfFieldsEmptyPositiveTest")
    @Test
    public void showErrorMessagesIfFieldsEmptyPositiveTest() {
        logger.info("Starting test: showErrorMessagesIfFieldsEmpty");

        AddCarPage addCarPage = app.getAddCarPage();

        addCarPage.submitCarForm(); // отправляем пустую форму

        assertTrue(
                addCarPage.isErrorMessageDisplayed("Car brand is required"),
                "Error messages should be displayed for required fields"
        );
        logger.info("Test completed: showErrorMessagesIfFieldsEmpty");
    }

    @DisplayName("shouldRejectInvalidImageFormatNegativeTest")
    @Test
    public void shouldRejectInvalidImageFormatNegativeTest() {
        logger.info("Starting test: shouldRejectInvalidImageFormat");

        AddCarPage addCarPage = app.getAddCarPage();

        String wrongFormatPath = "src/test/resources/image.txt";

        assertFalse(
                addCarPage.isImageUploadAccepted(wrongFormatPath),
                "Only .jpg, .jpeg, and .png formats should be accepted"
        );
        logger.info("Test completed: shouldRejectInvalidImageFormat");
    }

    @DisplayName("shouldRejectNonexistentImagePathNegativeTest")
    @Test
    public void shouldRejectNonexistentImagePathNegativeTest() {
        logger.info("Starting test: shouldRejectNonexistentImagePath");

        AddCarPage addCarPage = app.getAddCarPage();

        String nonExistentPath = "src/test/resources/nonexistent.jpg";

        assertFalse(
                addCarPage.isImageUploadAccepted(nonExistentPath),
                "Non-existent image path should not be accepted"
        );
        logger.info("Test completed: shouldRejectNonexistentImagePath");
    }

    @DisplayName("shouldShowErrorForInvalidPriceNegativeTest")
    @Test
    public void shouldShowErrorForInvalidPriceNegativeTest() {
        logger.info("Starting test: shouldShowErrorForInvalidPrice");

        AddCarPage addCarPage = app.getAddCarPage();

        // Пытаемся ввести некорректную цену (буквы вместо цифр, например)
        addCarPage.fillCarForm(
                "Toyota", "Corolla", "2020", "Sedan", "Petrol", "Automatic",
                "abc", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg"
        );
        WebElement priceInput = app.driver.findElement(By.name("dayRentalPrice"));
        priceInput.sendKeys("abc");

// Проверяем, что поле осталось пустым
        String value = priceInput.getAttribute("value");
        assertEquals("", value, "Field should not accept non-numeric input");

        logger.info("Test completed: shouldShowErrorForInvalidPrice");
    }

    @DisplayName("shouldRejectInvalidRentalPriceNegativeTest")
    @Test
    public void shouldRejectInvalidRentalPriceNegativeTest() {
        logger.info("Starting test: shouldRejectInvalidRentalPriceNegativeTest");

        AddCarPage addCarPage = app.getAddCarPage();

        addCarPage.fillCarForm("Ford", "Focus", "2023", "Sedan", "Petrol", "Manual", "-50", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        addCarPage.submitCarForm();

        assertTrue(
                addCarPage.isPriceValidationHintVisible("Price must be more than 0"),
                "Hint 'Price must be more than 0' should appear below the input field"
        );

        logger.info("Test completed: shouldRejectNegativeRentalPriceNegativeTest");
    }

    @DisplayName("shouldShowErrorsForEmptyFormNegativeTest")
    @Test
    public void shouldShowErrorsForEmptyFormNegativeTest() {
        logger.info("Starting test: shouldShowErrorsForEmptyFormNegativeTest");

        AddCarPage addCarPage = app.getAddCarPage();
        addCarPage.submitCarForm();
        assertTrue(addCarPage.isErrorMessageDisplayed("Car brand is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Car model is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Year when car was produced is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Car fuel type is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Car transmission type is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Price per day is required"));

        logger.info("Test completed: shouldShowErrorsForEmptyFormNegativeTest");
    }
}
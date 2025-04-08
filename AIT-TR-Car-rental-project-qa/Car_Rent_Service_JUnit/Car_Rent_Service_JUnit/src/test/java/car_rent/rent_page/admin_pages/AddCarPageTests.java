package car_rent.rent_page.admin_pages;

import car_rent.core.TestBase;
import car_rent.pages.AdminPage;
import car_rent.pages.HomePage;
import car_rent.pages.LoginPage;
import car_rent.pages.admin_pages.AddCarPage;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCarPageTests extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(AddCarPageTests.class); // Используем SLF4J Logger

    @BeforeEach
    public void setUp() {
        logger.info("Initializing test...");

        // Переходим на страницу логина и авторизуемся
        new HomePage(app.driver, app.wait).selectLogin();
        new LoginPage(app.driver, app.wait).loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        new AdminPage(app.driver, app.wait).clickAddCar();
    }

    @Test
    public void allRequiredFieldsShouldBeVisible() {
        logger.info("Starting test: allRequiredFieldsShouldBeVisible");

        AddCarPage addCarPage = app.getAddCarPage();

        boolean allVisible = addCarPage.areRequiredFieldsVisible();

        assertTrue(allVisible, "Not all required fields are visible on the Add Car page");
        logger.info("Test completed: allRequiredFieldsShouldBeVisible");
    }

    @Test
    public void showErrorMessagesIfFieldsEmpty() {
        logger.info("Starting test: showErrorMessagesIfFieldsEmpty");

        AddCarPage addCarPage = app.getAddCarPage();

        addCarPage.submitCarForm(); // отправляем пустую форму

        assertTrue(
                addCarPage.isErrorMessageDisplayed("Car brand is required"),
                "Error messages should be displayed for required fields"
        );
        logger.info("Test completed: showErrorMessagesIfFieldsEmpty");
    }

    @Test
    public void shouldRejectInvalidImageFormat() {
        logger.info("Starting test: shouldRejectInvalidImageFormat");

        AddCarPage addCarPage = app.getAddCarPage();

        String wrongFormatPath = "src/test/resources/image.txt";

        assertFalse(
                addCarPage.isImageUploadAccepted(wrongFormatPath),
                "Only .jpg, .jpeg, and .png formats should be accepted"
        );
        logger.info("Test completed: shouldRejectInvalidImageFormat");
    }

    @Test
    public void shouldRejectNonexistentImagePath() {
        logger.info("Starting test: shouldRejectNonexistentImagePath");

        AddCarPage addCarPage = app.getAddCarPage();

        String nonExistentPath = "src/test/resources/nonexistent.jpg";

        assertFalse(
                addCarPage.isImageUploadAccepted(nonExistentPath),
                "Non-existent image path should not be accepted"
        );
        logger.info("Test completed: shouldRejectNonexistentImagePath");
    }

    @Test
    public void shouldShowErrorForInvalidPrice() {
        logger.info("Starting test: shouldShowErrorForInvalidPrice");

        AddCarPage addCarPage = app.getAddCarPage();

        // Пытаемся ввести некорректную цену (буквы вместо цифр, например)
        addCarPage.fillCarForm(
                "Toyota", "Corolla", "2020", "Sedan", "Petrol", "Automatic",
                "abc", "src/test/resources/car.jpg"
        );

        assertTrue(addCarPage.isPriceFieldInvalid(), "Invalid price input should trigger validation error");

        logger.info("Test completed: shouldShowErrorForInvalidPrice");
    }

    @Test
    public void shouldShowSuccessAlertAfterAddingCar() {
        logger.info("Starting test: shouldShowSuccessAlertAfterAddingCar");

        AddCarPage addCarPage = app.getAddCarPage();

        addCarPage.fillCarForm(
                "Toyota", "Corolla", "2020", "Sedan", "Petrol", "Automatic",
                "100", "src/test/resources/car.jpg"
        );

        addCarPage.submitCarForm();

        assertTrue(addCarPage.isSuccessAlertVisible("The car is saved"),
                "Success alert should be visible after saving the car");

        logger.info("Test completed: shouldShowSuccessAlertAfterAddingCar");
    }
}
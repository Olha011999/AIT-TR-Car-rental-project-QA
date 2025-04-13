package carrent.admin_tests;

import carrent.admin_pages.AddCarPage;
import carrent.admin_pages.AdminPage;
import carrent.core.TestBase;
import carrent.pages.HomePage;
import carrent.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class AddCarTests extends TestBase {
    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
        new LoginPage(app.driver, app.wait).adminLogIn("admin@gmail.com", "Yyyyyyy12345!");
        new LoginPage(app.driver, app.wait).clickLoginButton();
        new AdminPage(app.driver, app.wait).enterToTheAdminPanel();
        new AdminPage(app.driver, app.wait).clickAddCar();
    }

    @Test
    public void allRequiredFieldsShouldBeVisiblePositiveTest() {
        AddCarPage addCarPage = app.getAddCarPage();
        boolean allVisible = addCarPage.areRequiredFieldsVisible();
        assertTrue(allVisible, "Not all required fields are visible on the Add Car page");
    }

    @ParameterizedTest
    @CsvSource({
            "Toyota, Corolla, 2020, Sedan, Petrol, Automatic, 200, C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg",
            "Honda, Civic, 2019, Hatchback, Petrol, Manual, 180, C:\\Users\\admin\\Pictures\\Saved Pictures\\2019-civichatchback-honda-u.jpg"
    })
    public void shouldShowSuccessAlertAfterAddingCarPositiveTest(
            String brand,
            String model,
            String year,
            String type,
            String fuel,
            String transmission,
            String price,
            String imagePath
    ) {
        AddCarPage addCarPage = app.getAddCarPage();
        addCarPage.fillCarForm(
                brand, model, year, type, fuel, transmission, price, imagePath);
        addCarPage.submitCarForm();
        assertTrue(addCarPage.isSuccessAlertVisible("The car is saved"),
                "Success alert should be visible after saving the car");
    }

    @Test
    public void showErrorMessagesIfFieldsEmptyPositiveTest() {
        AddCarPage addCarPage = app.getAddCarPage();
        addCarPage.submitCarForm();
        assertTrue(
                addCarPage.isErrorMessageDisplayed("Car brand is required"),
                "Error messages should be displayed for required fields");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "C:\\Users\\admin\\Pictures\\Saved Pictures\\Документ Microsoft Word.txt",
            "C:\\Users\\admin\\Pictures\\Saved Pictures\\Документ Microsoft Word.pdf",
            "C:\\Users\\admin\\Pictures\\Saved Pictures\\Документ Microsoft Word.exe",
    })
    public void shouldRejectInvalidImageFormatNegativeTest(String wrongFormatPath) {
        AddCarPage addCarPage = app.getAddCarPage();
        assertFalse(
                addCarPage.isImageUploadAccepted(wrongFormatPath),
                "Only .jpg, .jpeg, and .png formats should be accepted"
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "C:\\Users\\admin\\Pictures\\Saved Pictures\\wp2374948.webp"
    })
    public void shouldRejectNonexistentImagePathNegativeTest(String nonExistentPath) {
        AddCarPage addCarPage = app.getAddCarPage();
        assertFalse(
                addCarPage.isImageUploadAccepted(nonExistentPath),
                "Non-existent image path should not be accepted"
        );
    }

    @ParameterizedTest
    @MethodSource("carrent.utils.DataProvider#shouldRejectInvalidPriceData")
    public void shouldRejectInvalidPriceNegativeTest(
            String brand,
            String model,
            String year,
            String type,
            String fuel,
            String transmission,
            String price,
            String imagePath,
            boolean expectedValid
    ) {
        AddCarPage addCarPage = app.getAddCarPage();
        addCarPage.fillCarForm(brand, model, year, type, fuel, transmission, price, imagePath);
        WebElement priceInput = app.driver.findElement(By.name("dayRentalPrice"));
        priceInput.sendKeys(price);
        String value = priceInput.getAttribute("value");

        if (!expectedValid) {
            assertEquals("", value, "The field must reject non-numeric values.: " + price);
        } else {
            assertNotEquals("", value, "The field should accept valid values.: " + price);
        }
    }


    @ParameterizedTest
    @MethodSource ("carrent.utils.DataProvider#invalidRentalPriceProvider")
    public void shouldRejectInvalidRentalPriceNegativeTest(String brand, String model, String year, String type, String fuel, String transmission, String price, String imagePath, String expectedHint) {
        AddCarPage addCarPage = app.getAddCarPage();
        addCarPage.fillCarForm(brand, model, year, type, fuel, transmission, price, imagePath);
        addCarPage.submitCarForm();
        assertTrue(
                addCarPage.isPriceValidationHintVisible(expectedHint),
                "Expected validation hint not shown for price: " + price
        );
    }

    @Test
    public void shouldShowErrorsForEmptyFormNegativeTest() {
        AddCarPage addCarPage = app.getAddCarPage();
        addCarPage.submitCarForm();
        assertTrue(addCarPage.isErrorMessageDisplayed("Car brand is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Car model is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Year when car was produced is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Car fuel type is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Car transmission type is required"));
        assertTrue(addCarPage.isErrorMessageDisplayed("Price per day is required"));
    }

    @AfterEach
    public void tearDown() {
        if (app.getAddCarPage() != null) {
            app.driver.quit();
        }
    }
}
package car_rent;

import car_rent.core.ApplicationManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.AssertJUnit.*;

public class AddCarTests {
    private ApplicationManager app = new ApplicationManager();

    @BeforeMethod
    public void setUp() {
        app.init();
    }

    @Test
    public void AddCarPositiveTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Toyota", "Camry", "2021", "Sedan", "Petrol", "Automatic", "90", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();

        WebDriverWait wait = new WebDriverWait(app.getDriver(), Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();

        assertEquals("The car is saved", alertText);
        alert.accept();
    }


    @Test
    public void AddNewCarButtonVisiblePositiveTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        assertTrue(app.admin().isAddCarButtonVisible());
    }

    @Test
    public void requiredFieldsPresentPositiveTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();

        assertTrue("Not all required fields are visible on Add Car page", app.car().areRequiredFieldsVisible());
    }



    @Test
    public void testEmptyFormShowsErrorPositiveTest() {

        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");

        app.home().goToAdminPage();

        app.admin().clickAddCar();

        app.car().submitCarForm();

        assertTrue(app.car().isErrorMessageDisplayed("Car brand is required"));
        assertTrue(app.car().isErrorMessageDisplayed("Car model is required"));
        assertTrue(app.car().isErrorMessageDisplayed("Year when car was produced is required"));
        assertTrue(app.car().isErrorMessageDisplayed("Car fuel type is required"));
        assertTrue(app.car().isErrorMessageDisplayed("Car transmission type is required"));
        assertTrue(app.car().isErrorMessageDisplayed("Price per day is required"));
    }

    @Test
    public void testValidFileFormatUploadPositiveTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();

        String imagePath = "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg";

        assertTrue(app.car().isImageUploadAccepted(imagePath));
    }


    @Test
    public void testValidationForNumericalFieldsNegativeTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();

        app.car().fillCarForm("Audi", "A4", "abcd", "Sedan", "Petrol", "Automatic", "e",
                "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");

        app.car().submitCarForm();

        assertTrue(app.car().isPriceFieldInvalid());
    }

    @Test
    public void testAddCarWithDifferentValidDataNegativeTest() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Tesla", "Model S", "2023", "Manual", "Electric", "Automatic", "200",
                "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();

        ((JavascriptExecutor) app.getDriver()).executeScript("window.scrollTo(0, 0);");

        assertTrue(app.car().isSuccessAlertVisible("The car is saved"));
    }


    @Test
    public void ErrorHandlingForMissingPrivilegesNegativeTest() {
        app.login().loginAsUser("user@gmail.com", "UserPass123!");
        app.home().goToAdminPage();
        assertTrue(app.admin().isAccessDeniedMessageDisplayed());
    }


    @Test
    public void testAddCarMissingFields() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Toyota", "", "", "", "", "", "", "");
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("All fields are required"));
    }

    @Test
    public void testUploadInvalidFileFormat() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        assertFalse(app.car().isImageUploadAccepted(".exe"));
    }

    @Test
    public void testInvalidYear() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Ford", "Focus", "1890", "Sedan", "Petrol", "Manual", "90", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("Invalid year"));
    }

    @Test
    public void testInvalidRentalPrice() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Ford", "Focus", "2023", "Sedan", "Petrol", "Manual", "-50", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("Invalid price"));
    }

    @Test
    public void testUnsupportedImageFormat() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        assertFalse(app.car().isImageUploadAccepted(".svg"));
    }

    @Test
    public void testAddCarWithoutPhoto() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Kia", "Rio", "2023", "Hatchback", "Petrol", "Automatic", "85", null);
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("Photo is required"));
    }

    @Test
    public void testSpecialCharactersInFields() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("<script>", "123$%^", "2022", "@@@", "Petrol", "Automatic", "100", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("Invalid characters in input"));
    }

    @Test
    public void testDuplicateCarEntry() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        app.car().fillCarForm("Toyota", "Camry", "2023", "Sedan", "Petrol", "Automatic", "100", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();
        // Повторно
        app.admin().clickAddCar();
        app.car().fillCarForm("Toyota", "Camry", "2023", "Sedan", "Petrol", "Automatic", "100", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("Car already exists"));
    }

    @Test
    public void testExcessivelyLongInput() {
        app.login().loginAsAdmin("admin@gmail.com", "Yyyyyyy12345!");
        app.home().goToAdminPage();
        app.admin().clickAddCar();
        String longText = "A".repeat(1000);
        app.car().fillCarForm(longText, longText, "2023", longText, "Petrol", "Automatic", "100", "C:\\Users\\admin\\Pictures\\Saved Pictures\\cam1712_6001_copy-1398082811.jpg");
        app.car().submitCarForm();
        assertTrue(app.car().isErrorMessageDisplayed("Text too long"));
    }

    @AfterMethod
    public void tearDown() {
        app.stop();
    }
}

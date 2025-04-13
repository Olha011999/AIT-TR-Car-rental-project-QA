package carrent.admin_tests;

import carrent.admin_pages.AdminPage;
import carrent.admin_pages.CarsPage;
import carrent.core.TestBase;
import carrent.pages.HomePage;
import carrent.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCarTests extends TestBase {

    @BeforeEach
    public void setUp() {
        new HomePage(app.driver, app.wait).selectLogin();
        new LoginPage(app.driver, app.wait).adminLogIn("admin@gmail.com", "Yyyyyyy12345!");
    }


    @ParameterizedTest
    @ValueSource(strings = {"Toyota Corolla", "Honda Civic"})
    public void deleteCarFromDataBasePositiveTest(String carName) {
        CarsPage carsPage = new AdminPage(app.driver, app.wait)
                .enterToTheAdminPanel()
                .enterToTheCarsPage();

        // Скроллим вниз, чтобы прогрузить все карточки
        carsPage.scrollToBottom();

        // Ожидаем, пока автомобили прогрузятся
        boolean carElement = carsPage.isCarPresent(carName);

        // Проверяем, что нужная машина есть на странице
        boolean isCarPresent = carsPage.isCarPresent(carName);
        assertTrue(isCarPresent, "❌ The car " + carName + " should be present but is not found.");

        // Теперь можно продолжать тесты для удаления автомобиля
        if (carsPage.isCarDeletable(carName)) {
            carsPage
                    .deleteCar(carName)
                    .verifyNotificationMessage("The car is deleted");

            assertFalse(carsPage.isCarPresent(carName), "❌ The car should be deleted but is still present.");
        }
    }



    @AfterEach
    public void tearDown() {
        if (app.getAddCarPage() != null) {
            app.driver.quit();
        }
    }
}

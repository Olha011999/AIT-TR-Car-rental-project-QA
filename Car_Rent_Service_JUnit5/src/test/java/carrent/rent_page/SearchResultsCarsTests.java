package carrent.rent_page;

import carrent.core.TestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import carrent.pages.HomePage;
import carrent.pages.LoginPage;
import carrent.pages.SearchResultsPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchResultsCarsTests extends TestBase {
    @BeforeEach
    public void preCondition() {
        HomePage homePage = app.getHomePage();
        homePage.selectLogin();
        LoginPage loginPage = app.getLoginPage();
        loginPage.enterEmail("test43@gmail.com");
        loginPage.enterPassword("Password1@");
        loginPage.clickLoginButton();
    }

    @ParameterizedTest
    @CsvSource({
            "'15-04-2025T10:00', '20-04-2025T10:00'",
            "'16-04-2025T10:30', '21-04-2025T13:00'"
    })
    public void searchForCarsPositiveTest(String startDate, String endDate) {
        HomePage homePage = app.getHomePage();
        homePage.enterRentalDates(startDate, endDate);
        SearchResultsPage searchResultsPage = homePage.submitSearch();
        assertTrue(searchResultsPage.isCarListDisplayed(), "Список доступных автомобилей не отображается.");
    }

    @ParameterizedTest
    @CsvSource({
            "'15-04-2025T10:00', '20-04-2025T10:00'",
            "'16-04-2025T10:30', '21-04-2025T13:00'"
    })
    public void searchForCarsNegativeTest(String startDate, String endDate) {
        HomePage homePage = app.getHomePage();
        homePage.enterRentalDates(startDate, endDate);
        SearchResultsPage searchResultsPage = homePage.submitSearch();
        assertTrue(searchResultsPage.isErrorMessageStart());
    }

    @AfterEach
    public void tearDown() {
        if (app.getAddCarPage() != null) {
            app.driver.quit();
        }
    }
}

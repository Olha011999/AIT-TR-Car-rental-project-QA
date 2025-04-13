package carrent.admin_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class CarsPage extends AdminPage {
    public CarsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "p.text-gray-600.mb-6")
    WebElement notificationMessage;

    public CarsPage deleteCar(String carName) {
        WebElement carCard = findCarCard(carName);
        WebElement deleteButton = carCard.findElement(By.xpath(".//button[contains(text(), 'Delete')]"));
        deleteButton.click();
        return this;
    }

    public CarsPage verifyNotificationMessage(String expectedMessage) {
        WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'text-gray-600')]")
        ));
        String actualText = notification.getText();
        if (!actualText.contains(expectedMessage)) {
            throw new AssertionError("❌ Expected message to contain: " + expectedMessage + ", but got: " + actualText);
        }
        return this;
    }


    public boolean isCarPresent(String carName) {
        return findCarCardByName(carName) != null;
    }


    private WebElement findCarCardByName(String carName) {
        List<WebElement> carTitles = driver.findElements(By.cssSelector("h3.text-xl.font-semibold.text-gray-800.truncate"));

        for (WebElement title : carTitles) {
            if (title.getText().trim().equalsIgnoreCase(carName)) {
                // Ищем ближайший родитель, содержащий всю карточку
                return title.findElement(By.xpath("./ancestor::div[contains(@class, 'flex')]"));
            }
        }
        return null;
    }


    public boolean isCarDeletable(String carName) {
        try {
            WebElement carCard = findCarCard(carName);

            // Ищем кнопку Delete внутри карточки машины
            WebElement deleteButton = carCard.findElement(By.xpath(".//button[contains(text(), 'Delete')]"));

            // Проверка, доступна ли кнопка для клика (например, по стилю или enabled-состоянию)
            return deleteButton.isEnabled();

        } catch (NoSuchElementException e) {
            // Нет кнопки Delete → либо машина уже удалена, либо её вообще нет
            return false;
        }
    }

    private WebElement findCarCard(String carName) {
        List<WebElement> titleElements = driver.findElements(
                By.xpath("//h3[contains(@class, 'text-xl') and contains(text(), '" + carName + "')]")
        );

        for (WebElement title : titleElements) {
            String fullName = title.getText().trim();
            if (fullName.equalsIgnoreCase(carName)) {
                return title.findElement(By.xpath("./ancestor::div[contains(@class, 'rounded-lg')]"));
            }
        }

        throw new NoSuchElementException("❌ Car not found in the list: " + carName);
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Ждем, пока страница полностью загрузится
        waitForPageLoad();

        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            // Скроллим вниз
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(1000); // Даем время на подгрузку новых элементов
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");

            // Если высота страницы не изменилась, значит, больше нет новых элементов
            if (newHeight == lastHeight) {
                break;
            }

            lastHeight = newHeight;
        }
    }

    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public boolean isCarPresent(String carBrand, String carModel) {
        scrollToBottom();
        List<WebElement> carElements = driver.findElements(By.xpath("//div[@class='car-card']")); // Заменить на правильный XPath для автомобилей
        for (WebElement carElement : carElements) {
            String carName = carElement.findElement(By.xpath(".//h3")).getText(); // Заменить XPath на правильный для названия автомобиля
            if (carName.contains(carBrand) && carName.contains(carModel)) {
                return true;
            }
        }
        return false;
    }


    public void waitForElementToLoad(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element)); // Ожидание видимости элемента
    }

}

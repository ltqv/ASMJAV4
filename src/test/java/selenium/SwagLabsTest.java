package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwagLabsTest {

    WebDriver driver;
    WebDriverWait wait;

    static final String BASE_URL = "https://www.saucedemo.com/";
    static final String USERNAME = "standard_user";
    static final String PASSWORD = "secret_sauce";

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // =========================
    // TEST CASES
    // =========================

    @Test
    @Order(1)
    void testOpenLoginPage() {
        driver.get(BASE_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));

        Assertions.assertTrue(
                driver.getTitle().toLowerCase().contains("swag"),
                "Không mở được trang Swag Labs"
        );
    }

    @Test
    @Order(2)
    void testLoginSuccess() {
        driver.get(BASE_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                .sendKeys(USERNAME);

        driver.findElement(By.id("password"))
                .sendKeys(PASSWORD);

        driver.findElement(By.id("login-button"))
                .click();

        // Đợi trang products xuất hiện
        wait.until(ExpectedConditions.urlContains("inventory"));

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("inventory"),
                "Login không thành công"
        );
    }

    @Test
    @Order(3)
    void testAddToCart() {
        testLoginSuccess();

        // Click Add to cart sản phẩm đầu tiên
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".inventory_item button")))
                .click();

        // Kiểm tra icon giỏ hàng có số 1
        WebElement cartBadge = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("shopping_cart_badge"))
        );

        Assertions.assertEquals("1", cartBadge.getText(),
                "Thêm vào giỏ hàng thất bại");
    }

    @Test
    @Order(4)
    void testLogout() {
        testLoginSuccess();

        // Mở menu
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("react-burger-menu-btn")))
                .click();

        // Click logout
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("logout_sidebar_link")))
                .click();

        // Quay lại trang login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));

        Assertions.assertTrue(
                driver.getCurrentUrl().equals(BASE_URL),
                "Logout không thành công"
        );
    }
}

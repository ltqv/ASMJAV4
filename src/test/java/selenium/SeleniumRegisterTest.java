package selenium;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class SeleniumRegisterTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    @Order(1)
    void testAutoRegister() {
        driver.get("http://localhost:8080/ASM_Java4/register");

        // Tạo user random để không bị trùng DB
        String username = "test" + UUID.randomUUID().toString().substring(0, 5);
        String password = "123456";
        String email = username + "@gmail.com";

        // Locate input fields
        WebElement txtUser = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        WebElement txtPass = driver.findElement(By.name("password"));
        WebElement txtEmail = driver.findElement(By.name("email"));

        // Fill form
        txtUser.sendKeys(username);
        txtPass.sendKeys(password);
        txtEmail.sendKeys(email);

        // Submit
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Check success
        boolean success = wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("login"),
                ExpectedConditions.presenceOfElementLocated(By.className("alert-success"))
        )) != null;

        assertTrue(success);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

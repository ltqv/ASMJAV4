package selenium;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    void testLoginSuccess() throws InterruptedException {
        driver.get("http://localhost:8080/ASM_Java4/login");

        driver.findElement(By.name("id")).sendKeys("u01");
        driver.findElement(By.name("password")).sendKeys("123");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        Thread.sleep(2000);

        String url = driver.getCurrentUrl();
        System.out.println("FINAL URL = " + url);

        Assertions.assertFalse(url.contains("login"),
                "Login thành công thì không được ở lại trang login");
    }


    @Test
    void testLoginFail() {
        driver.get("http://localhost:8080/ASM_Java4/login");

        driver.findElement(By.name("id")).sendKeys("saiuser");
        driver.findElement(By.name("password")).sendKeys("saimatkhau");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Kiểm tra message lỗi hiển thị
        WebElement alert = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("alert-danger"))
        );

        Assertions.assertTrue(alert.isDisplayed(), "Phải hiển thị thông báo lỗi khi login sai");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

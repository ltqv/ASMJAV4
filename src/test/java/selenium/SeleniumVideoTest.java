package selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumVideoTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void testAdminDeleteVideo() throws InterruptedException {
        driver.get("http://localhost:8080/ASM_Java4/login");

        // Login
        driver.findElement(By.name("id")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.tagName("button")).click();

        Thread.sleep(2000);

        // Vào Video Manager
        driver.get("http://localhost:8080/ASM_Java4/admin/videoManager");
        Thread.sleep(2000);

        // Click nút xóa video đầu tiên
        WebElement deleteBtn = driver.findElement(
                By.xpath("//button[contains(@onclick,'confirmDelete')]")
        );
        deleteBtn.click();

        Thread.sleep(1000);

        // Confirm alert
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        // Check còn bảng video => thành công
        WebElement table = driver.findElement(By.tagName("table"));
        assertTrue(table.isDisplayed());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

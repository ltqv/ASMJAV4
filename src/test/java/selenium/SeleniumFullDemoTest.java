package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumFullDemoTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    static final String BASE_URL = "http://localhost:8080/ASM_Java4";
    static String autoUser;
    static final String PASSWORD = "123";

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // =========================
    // HÀM DÙNG CHUNG
    // =========================

    void safeClick(WebElement element) throws InterruptedException {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", element);
    }

    void register(String user, String pass, String fullname, String email) throws InterruptedException {
        driver.get(BASE_URL + "/register");

        wait.until(ExpectedConditions.elementToBeClickable(By.name("id")))
                .sendKeys(user);

        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.name("fullname")).sendKeys(fullname);
        driver.findElement(By.name("email")).sendKeys(email);

        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        safeClick(submitBtn);
    }

    void login(String user, String pass) throws InterruptedException {
        driver.get(BASE_URL + "/login");

        wait.until(ExpectedConditions.elementToBeClickable(By.name("id")))
                .sendKeys(user);

        driver.findElement(By.name("password")).sendKeys(pass);

        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
        safeClick(submitBtn);
    }

    void fillVideoForm(String id, String title) {
        wait.until(ExpectedConditions.elementToBeClickable(By.name("id")))
                .clear();
        driver.findElement(By.name("id")).sendKeys(id);

        driver.findElement(By.name("title")).clear();
        driver.findElement(By.name("title")).sendKeys(title);

        driver.findElement(By.name("link")).clear();
        driver.findElement(By.name("link"))
                .sendKeys("https://www.youtube.com/embed/test123");

        driver.findElement(By.name("poster")).clear();
        driver.findElement(By.name("poster"))
                .sendKeys("https://img.youtube.com/vi/test123/maxresdefault.jpg");

        driver.findElement(By.name("description")).clear();
        driver.findElement(By.name("description"))
                .sendKeys("Video được tạo bằng Selenium Automation Test");
    }

    // =========================
    // TEST CASES
    // =========================

    @Test
    @Order(1)
    void testRegister() throws InterruptedException {
        autoUser = "auto" + System.currentTimeMillis();

        String autoEmail = "auto" + System.currentTimeMillis() + "@gmail.com";
        register(autoUser, PASSWORD, "Selenium User", autoEmail);


        wait.until(ExpectedConditions.urlContains("/login"));

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Đăng ký xong phải chuyển về trang login"
        );
    }

    @Test
    @Order(2)
    void testLogin() throws InterruptedException {
        login(autoUser, PASSWORD);

        wait.until(ExpectedConditions.urlContains("/home"));

        Assertions.assertFalse(
                driver.getCurrentUrl().contains("/login"),
                "Login thành công thì không được ở lại trang login"
        );
    }

    @Test
    @Order(3)
    void testAdminVideoCRUD() throws InterruptedException {
        // Login admin
        login("admin", "123");

        // Vào Video Manager
        driver.get(BASE_URL + "/admin/videoManager");

        WebElement addBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Thêm Video Mới"))
        );
        safeClick(addBtn);

        String videoId = "VD" + System.currentTimeMillis();

        fillVideoForm(videoId, "Selenium ASM Video");

        WebElement saveBtn = driver.findElement(By.cssSelector("button.btn-success"));
        safeClick(saveBtn);

        // Quay lại danh sách
        driver.get(BASE_URL + "/admin/video/list");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        Assertions.assertTrue(
                driver.getPageSource().contains(videoId),
                "Video vừa thêm phải xuất hiện trong danh sách"
        );
    }

    @Test
    @Order(4)
    void testLogout() throws InterruptedException {
        driver.get(BASE_URL + "/home");

        WebElement homeBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='home']"))
        );
        safeClick(homeBtn);

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("/home"),
                "Logout xong phải về trang home"
        );
    }
}

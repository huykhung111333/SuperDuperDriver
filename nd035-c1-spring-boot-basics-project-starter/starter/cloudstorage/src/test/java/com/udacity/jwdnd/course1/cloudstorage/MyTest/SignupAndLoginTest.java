package com.udacity.jwdnd.course1.cloudstorage.MyTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupAndLoginTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.webDriverWait = new WebDriverWait(driver, 2);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void signUpAndLoginTest() {

        // Signup
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill data
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys("huy");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys("huy");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("huy");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("123");

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

        // Redirect login page
        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        Assertions.assertEquals("Login", driver.getTitle());
        // Fill username and passwork
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("huy");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("123");

        WebElement loginBtn = driver.findElement(By.id("login-button"));
        Assertions.assertEquals("Login", loginBtn.getText());
        loginBtn.click();

        // Access home page
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        // Log out
        WebElement logoutBtn = driver.findElement(By.id("logout-button"));
        Assertions.assertEquals("Logout", logoutBtn.getText());
        logoutBtn.click();

        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
        // End test
    }
}
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTest {
    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 2);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    private void signUp(){
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
    }

    private void login(){
        driver.get("http://localhost:" + this.port + "/login");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys("huy");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys("123");

        WebElement loginBtn = driver.findElement(By.id("login-button"));
        Assertions.assertEquals("Login", loginBtn.getText());

        loginBtn.click();
    }

    @Test
    @Order(1)
    public void createTest(){
        signUp();
        login();
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        Assertions.assertEquals("Credentials", credentialTab.getText());
        credentialTab.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='nav-credentials']/button")));
        WebElement addCredentialBtn = driver.findElement(By.xpath("//div[@id='nav-credentials']/button"));
        addCredentialBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.sendKeys("http://web-sample.com");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.sendKeys("username");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.sendKeys("password");

        WebElement credentialForm = driver.findElement(By.id("credentialForm"));
        credentialForm.submit();

        returnHomePage();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        Assertions.assertEquals("Credentials", credentialTab.getText());
        credentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='http://web-sample.com']")));
        credentialUrl = driver.findElement(By.xpath("//th[text()='http://web-sample.com']"));
        Assertions.assertEquals("http://web-sample.com", credentialUrl.getText());
        credentialUsername = driver.findElement(By.xpath("//td[text()='username']"));
        Assertions.assertEquals("username", credentialUsername.getText());
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='password']")));

    }

    @Test
    @Order(2)
    public void editCredentialTest(){
        Assertions.assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//th[text()='http://web-sample.com']"));
            driver.findElement(By.xpath("//td[text()='username']"));
            webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='password']")));
        });
        WebElement editBtn = driver.findElement(By.id("editCredentialBtn"));
        Assertions.assertEquals("Edit", editBtn.getText());
        editBtn.click();

        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialUrl));
        credentialUrl.clear();
        credentialUrl.sendKeys("http://web-sample.com/hello");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.clear();
        credentialUsername.sendKeys("usernameEdited");

        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        Assertions.assertEquals("password", credentialPassword.getAttribute("value"));
        credentialPassword.clear();
        credentialPassword.sendKeys("passwordEdited");

        WebElement credentialForm = driver.findElement(By.id("credentialForm"));
        credentialForm.submit();

        returnHomePage();

        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        Assertions.assertEquals("Credentials", credentialTab.getText());
        credentialTab.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='http://web-sample.com/hello']")));
        credentialUrl= driver.findElement(By.xpath("//th[text()='http://web-sample.com/hello']"));
        Assertions.assertEquals("http://web-sample.com/hello", credentialUrl.getText());
        credentialUsername = driver.findElement(By.xpath("//td[text()='usernameEdited']"));
        Assertions.assertEquals("usernameEdited", credentialUsername.getText());
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='passwordEdited']")));
    }

    @Test
    @Order(3)
    public void deleteCredentialTest(){
        /* DELETE CREDENTIAL: START */
        Assertions.assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//th[text()='http://web-sample.com/hello']"));
            driver.findElement(By.xpath("//td[text()='usernameEdited']"));
            webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='passwordEdited']")));
        });

        WebElement deleteBtn = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/a"));
        Assertions.assertEquals("Delete", deleteBtn.getText());
        deleteBtn.click();

        returnHomePage();

        WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
        Assertions.assertEquals("Credentials", credentialTab.getText());
        credentialTab.click();

        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//th[text()='http://web-sample.com/hello']")));
    }

    private void returnHomePage(){
        webDriverWait.until(ExpectedConditions.titleContains("Result"));
        Assertions.assertEquals("Result", driver.getTitle());

        WebElement success = driver.findElement(By.id("success"));
        Assertions.assertEquals("Success", success.getText());

        WebElement backHomeFromResult = driver.findElement(By.id("return"));
        Assertions.assertEquals("here", backHomeFromResult.getText());
        backHomeFromResult.click();
    }

}
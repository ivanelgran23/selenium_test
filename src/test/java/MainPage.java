import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;


class MainPage extends PageBase {

    private By signInBy = By.linkText("Sign In");
    private By cookiesAgree = By.xpath("//button[@onclick='acceptCompliance()']");
    private By login = By.name("user");
    private By password = By.name("password");
    private By submtBtn = By.className("formButton");
    private By pageTitle = By.tagName("title");
    
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://shadertoy.com/");
    }    

    public LoginPage signIn() throws Exception {
        this.waitAndReturnElement(cookiesAgree).click();
        Thread.sleep(1000);
        this.waitAndReturnElement(signInBy).click();
        this.waitAndReturnElement(login).sendKeys("selenium_test");
        this.waitAndReturnElement(password).sendKeys("Selenium123");
        this.waitAndReturnElement(submtBtn).click();
        return new LoginPage(this.driver);
    };

    public String getTitleText() {
        return this.driver.findElement(By.tagName("title")).getAttribute("innerText");
    }
    
}

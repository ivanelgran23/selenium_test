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


class LoginPage extends PageBase {

    private By login = By.id("user_login");
    private By password = By.id("user_password");
    private By submtBtn = By.name("commit");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }
    
    public String signIn() {
        this.waitAndReturnElement(login).sendKeys("selenium_test");
        this.waitAndReturnElement(password).sendKeys("Selenium123");
        this.waitAndReturnElement(submtBtn).click();
        return "done";
    };
}

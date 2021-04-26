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
import java.nio.file.Paths;
import java.nio.file.Path;

class ProfilePage extends PageBase {

    private By config = By.linkText("Config");
    private By aboutBox = By.className("aboutBox");
    private By configBtn = By.name("submitConfig1");
    private By publicProfile = By.linkText("Public Profile");
    private By profileImage = By.id("file");
    private By fileTable = By.id("divShadersTable");

    public ProfilePage(WebDriver driver) {
        super(driver);
        this.driver.get("https://shadertoy.com/profile");
    }

    public PublicProfilePage setProfileDescription() {
        this.waitAndReturnElement(config).click();
        ((JavascriptExecutor)this.driver).executeScript("document.getElementById('file').style.display = 'inline'");
        String path = Paths.get("selenium.png").toAbsolutePath().toString();
        this.waitAndReturnElement(profileImage).sendKeys(path);
        this.waitAndReturnElement(aboutBox).clear();
        this.waitAndReturnElement(aboutBox).sendKeys("Hi! I am an automated program for testing websites!");
        this.waitAndReturnElement(configBtn).click();
        this.waitAndReturnElement(publicProfile).click();
        return new PublicProfilePage(this.driver);
    }

    public WebElement getFilesTable() throws Exception {
        Thread.sleep(2000);
        return this.waitAndReturnElement(fileTable);
    }
           
}

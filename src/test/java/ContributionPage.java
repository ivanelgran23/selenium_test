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


class ContributionPage extends PageBase {

    private By pauseBtn = By.id("myPauseButton");
    private By shaderTitle = By.id("shaderTitle");

    public ContributionPage(WebDriver driver, String link) {
        super(driver);
        this.driver.get(link);
    }

    public String showShader() throws Exception {
        Thread.sleep(4000);
        this.waitAndReturnElement(pauseBtn).click();
        return this.waitAndReturnElement(shaderTitle).getText();
    }
           
}

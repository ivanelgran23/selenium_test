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


class PublicProfilePage extends PageBase {

    private By logoutBtn = By.linkText("Logout");


    public PublicProfilePage(WebDriver driver) {
        super(driver);
    }

    public String getProfileBodyText() {
        WebElement profileBodyElement = this.waitAndReturnElement(By.tagName("body"));
        return profileBodyElement.getText();
    }

    public PublicProfilePage logoutUser() {
        this.waitAndReturnElement(logoutBtn).click();
        return new PublicProfilePage(this.driver);
    }
           
}

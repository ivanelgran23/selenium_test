import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Cookie;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  
import java.util.concurrent.TimeUnit;
import java.io.File;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.*;

public class FirstSeleniumTest {
    public WebDriver driver;
    
    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        String downloadPath = System.getProperty("user.dir") + File.separator + "downloads";
        prefs.put("download.default_directory",downloadPath);
        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("chrome.switches","--disable-extensions"); 
        options.addArguments("--disable-save-password");
        options.addArguments("disable-infobars");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
    
    @Test
    public void authTest() {
        MainPage mainPage = new MainPage(this.driver);
        String shadertoyTitle = mainPage.getTitleText();
        Assert.assertEquals(shadertoyTitle, "Shadertoy BETA");
        LoginPage loginPage = mainPage.signIn();
        String bodyText = loginPage.getBodyText();
        Assert.assertTrue(bodyText.contains("selenium_test"));
        ProfilePage profilePage = new ProfilePage(this.driver);
        PublicProfilePage publicProfilePage = profilePage.setProfileDescription();
        String profileBodyText = publicProfilePage.getProfileBodyText();
        Assert.assertTrue(profileBodyText.contains("Hi! I am an automated program for testing websites!"));
        PublicProfilePage logoutPage = publicProfilePage.logoutUser();
        String logoutPageBodyText = logoutPage.getProfileBodyText();
        Assert.assertTrue(logoutPageBodyText.contains("Sign In"));
    }
    
    @Test
    public void staticTest() {
        int contrCounter = 0;
        Map<String,String> contrs = new HashMap<String,String>();
        MainPage mainPage = new MainPage(this.driver);
        WebElement contributors = this.driver.findElement(By.className("contributors"));
        List<WebElement> contributorsList = contributors.findElements(By.className("regular"));
        for ( WebElement contributor : contributorsList ) {
            if ( contrCounter % 2 != 0 ) {
                String link = contributor.getAttribute("href");
                String text = contributor.getText();
                text = text.substring(1, text.length() - 1);
                contrs.put(link, text);
            }
            contrCounter += 1;
        }
        for (String link : contrs.keySet()) {
            ContributionPage contributionPage = new ContributionPage(this.driver, link);
            String shaderTitle = contributionPage.showShader();
            Assert.assertTrue(shaderTitle.contains(contrs.get(link)));
        }    
    }
    
    @Test
    public void cookieTest() {
        MainPage mainPage = new MainPage(this.driver);
        Cookie lpCookie = driver.manage().getCookieNamed("_gat");
        System.out.println(lpCookie.getValue());
        assertThat(lpCookie.getValue(), containsString("1"));
        Cookie newCookie = new Cookie("foo", "bar");
        driver.manage().addCookie(newCookie);
        driver.navigate().refresh();
        Cookie driverCookie = driver.manage().getCookieNamed("foo");
        assertThat(driverCookie.getValue(), equalTo("bar"));
        driver.manage().deleteCookie(driverCookie);
        Cookie deletedCookie = driver.manage().getCookieNamed("foo");
        assertThat(deletedCookie, is(nullValue()));
    }

    @Test
    public void downloadTest() throws Exception{
        int fileCounter = 0;
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.signIn();
        ProfilePage profilePage = new ProfilePage(this.driver);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String downloadPath = System.getProperty("user.dir") + File.separator + "downloads";
        WebElement fileTable = profilePage.getFilesTable();
        List<WebElement> rows = fileTable.findElements(By.className("formButtonSmall"));
        System.out.println("Number of elements: " + rows.size());
        for ( WebElement row : rows ) {
            if ( row.getText().contains("Export")) {
                System.out.println(row.getText());
                row.click();
                Thread.sleep(2000);
                fileCounter += 1;
            }
        }
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();
        Assert.assertEquals(dirContents.length, fileCounter);
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class Ribblecycles1 {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lenovo\\IdeaProjects\\RibblecyclesAutomationTest\\src\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://www.ribblecycles.co.uk/");
    }

    @After
    public void cleanUp() {
        driver.close();
        if (driver != null)
            driver.quit();
    }

    @Test
    public void CheckURL() {
        assertEquals("https://www.ribblecycles.co.uk/", driver.getCurrentUrl());
        assertEquals("Ribble Cycles | Leading British Cycle Manufacturer | Bike Shop | Since 1897 | Ribble Cycles", driver.getTitle());
    }


// Login as an existing customer
// Search for a product
// Check that the search result page has only those products which has been typed on the search

    @Test
    public void LoginCheck() {
        driver.findElement(By.cssSelector("div[data-testid='accountButton']")).click();

        WebElement password_field = driver.findElement(By.xpath("//input[@type='password']"));
        password_field.sendKeys("Xp&LLR7@1091");

        String inputText_email = "test123@gmail.com";
        WebElement email_field = driver.findElement(By.xpath("//input[@placeholder='E-mail address *']"));
        email_field.sendKeys(inputText_email);
        email_field.sendKeys(Keys.UP);

        driver.findElement(By.cssSelector("button[class='action mb20 loginSubmit']")).click();

        driver.findElement(By.cssSelector("button[data-testid='openSearchPanel']")).sendKeys(Keys.SPACE);
        String inputSearch = "Ribble";
        WebElement search = driver.findElement(By.xpath("//input[@class='search-panel-input']"));
        search.sendKeys(inputSearch);

        String searchItem = "//div[@class='product-details']//h2[@class='mt10 product-details__name']//span[@class='name']";
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchItem)));
        List<WebElement> searchList = driver.findElements(By.xpath(searchItem));
        int count = searchList.size();
        for (int i = 0; i < count; i++) {
            String item = searchList.get(i).getText();
            assertThat(item, containsString(inputSearch));
        }
    }
}
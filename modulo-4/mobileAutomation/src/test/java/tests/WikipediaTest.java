package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.MutableCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.WikipediaPage;

import java.net.MalformedURLException;
import java.net.URL;

public class WikipediaTest extends BaseTest {
    private WikipediaPage wikipediaPage;

    @BeforeMethod
    public void beforeTest() throws MalformedURLException {
        MutableCapabilities capabilities = new UiAutomator2Options();
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wikipediaPage = new WikipediaPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

    @Test(dataProvider = "searchQueries")
    public void testWikipediaSearch(String searchQuery) {
        var results = wikipediaPage.search(searchQuery);
        Assert.assertFalse(results.isEmpty());
    }

    @DataProvider
    public Object[][] searchQueries() {
        return new Object[][]{
                {"WebdriverIO"},
                {"BrowserStack"}
        };
    }
}

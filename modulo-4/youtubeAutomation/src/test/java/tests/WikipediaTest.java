package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.WikipediaPage;
import pages.YouTubePage;

import java.net.MalformedURLException;

public class WikipediaTest extends BaseTest {
    private WikipediaPage wikipediaPage;

    @BeforeMethod
    public void beforeTest() throws MalformedURLException {
        configureAndroidDriver(false);
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
        wikipediaPage.search(searchQuery);
    }

    @DataProvider
    public Object[][] searchQueries() {
        return new Object[][]{
                {"WebdriverIO"},
                {"BrowserStack"}
        };
    }
}

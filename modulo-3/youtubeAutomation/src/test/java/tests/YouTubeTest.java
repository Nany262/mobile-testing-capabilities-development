package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.YouTubePage;

import java.net.MalformedURLException;

public class YouTubeTest extends BaseTest {
    private YouTubePage youtubePage;

    @BeforeMethod
    public void beforeTest() throws MalformedURLException {
        configureAndroidDriver();
        youtubePage = new YouTubePage(driver);
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
    public void testSearchAndPlayVideo(String searchQuery) {
        youtubePage.searchVideo(searchQuery);
    }

    @DataProvider
    public Object[][] searchQueries() {
        return new Object[][]{
                {"WebdriverIO tutorial"},
                {"Selenium Tutorial"},
                {"Python Tutorial"}
        };
    }
}

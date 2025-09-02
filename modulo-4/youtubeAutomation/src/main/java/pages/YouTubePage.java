package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class YouTubePage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    @FindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
    public WebElement allowButton;

    @FindBy(xpath = "//*[contains(@content-desc,'Buscar') or contains(@content-desc,'Search')]")
    public WebElement searchBar;

    @FindBy(id = "com.google.android.youtube:id/search_edit_text")
    public WebElement searchBarToWrite;

    @FindBy(xpath = "//*[@resource-id=\"com.google.android.youtube:id/results\"]/android.view.ViewGroup")
    public WebElement homeResults;

    @FindBy(id = "com.google.android.youtube:id/watch_while_time_bar_view")
    public WebElement videoPlayer;


    public YouTubePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        PageFactory.initElements(driver, this);
    }

    public void searchVideo(String query) {
        allowButton.click();
        wait.until(ExpectedConditions.visibilityOf(homeResults));
        wait.until(ExpectedConditions.visibilityOf(searchBar));
        searchBar.click();
        searchBarToWrite.sendKeys(query);
        WebElement suggestedSearchResult = waitForSuggestedSearchResult(query);
        suggestedSearchResult.click();
        var videoResults = waitForResults(query);
        checkVideoPlayer(videoResults);
    }

    private WebElement waitForSuggestedSearchResult(String query) {
        String[] words = query.split(" ");
        By locator = By.xpath("//android.widget.TextView[@resource-id='com.google.android.youtube:id/text' and matches(@text, '(?i).*" + words[0] + ".*')]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    private List<WebElement> waitForResults(String query) {
        String[] words = query.split(" ");
        By locator = By.xpath("//android.view.ViewGroup[matches(@content-desc, '(?i).*" + words[0] + ".*')]");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    private void checkVideoPlayer(List<WebElement> searchResults) {
        for (WebElement video : searchResults) {
            // SI video[i] ES visible EN pantalla
            if (video.isDisplayed()) {
                // CLICK video[i]
                video.click();
                // SI botonReproducir ESTÁ disponible
                playAllIfPresent();
                // ESPERAR reproductorDeVideo
                wait.until(ExpectedConditions.visibilityOf(videoPlayer));
                // El video se reprodujo exitosamente
                return;
            }
        }
    }

    private void playAllIfPresent() {
        var smallWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        var locator = By.xpath("//android.view.ViewGroup[@content-desc='Play all']");
        if (!driver.findElements(locator).isEmpty()) {
            // CLICK botonReproducir
            driver.findElement(locator).click();
        }
    }
}
package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WikipediaPage {
    private final WebDriverWait wait;

    @FindBy(id = "org.wikipedia.alpha:id/fragment_onboarding_skip_button")
    public WebElement skipButton;

    @FindBy(id = "org.wikipedia.alpha:id/search_src_text")
    public WebElement insertTextElement;

    public WikipediaPage(AppiumDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> search(String query) {
        skipButton.click();
        var searchElement = wait.until(
                ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search Wikipedia")));
        searchElement.click();
        wait.until(ExpectedConditions.elementToBeClickable(insertTextElement));
        insertTextElement.sendKeys(query);
        var resultsLocator = By.className("android.widget.TextView");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultsLocator));
    }
}

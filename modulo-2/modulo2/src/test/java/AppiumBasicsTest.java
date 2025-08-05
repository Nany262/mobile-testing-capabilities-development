import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppiumBasicsTest extends BaseTest {

    public void searchVideoOnYoutube(AppiumDriver driver, Map<String, By> locators, String toSearch) {
        // ACEPTAR permisos de notificaciones
        driver.findElement(locators.get("allow button")).click();
        //ENCONTRAR barraBusqueda
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.get("search bar")));
        //CLICK barraBusqueda
        driver.findElement(locators.get("search bar")).click();
        //ESCRIBIR "WebdriverIO tutorial"
        driver.findElement(locators.get("search bar to write")).sendKeys(toSearch);
        //ESPERAR sugerenciaBusqueda
        wait.until(ExpectedConditions.visibilityOfElementLocated(locators.get("suggested search")));
        // CLICK sugerenciaElegida
        driver.findElement(locators.get("suggested search")).click();
        List<WebElement> searchResults = driver.findElements(locators.get("results"));
        //PARA i = 1 HASTA nVideos EN resultadosDeBusqueda
        for (WebElement video : searchResults) {
            // SI video[i] ES visible EN pantalla
            if (video.isDisplayed()) {
                // CLICK video[i]
                video.click();
                // SI botonReproducir ESTÁ disponible
                if (!driver.findElements(locators.get("play all")).isEmpty()) {
                    // CLICK botonReproducir
                    driver.findElement(locators.get("play all")).click();
                }
                // ESPERAR reproductorDeVideo
                WebElement videoPlayer = wait.until(
                        ExpectedConditions.presenceOfElementLocated(locators.get("video player"))
                );
                // VALIDAR reproduccion video
                Assert.assertNotNull(videoPlayer, "The video player should be present on screen");
                break;
            }
        }
    }

    @Test
    public void searchVideoOnYouTubeAndroid() throws MalformedURLException {
        configureAndroidDriver();
        String toSearch = "WebdriverIO tutorial";
        String[] words = toSearch.split(" ");
        Map<String, By> androidYoutubeLocators = new HashMap<>();
        androidYoutubeLocators.put("allow button", By.id("com.android.permissioncontroller:id/permission_allow_button"));
        //XPATH dinamico: https://www.browserstack.com/guide/dynamic-xpath-in-selenium
        androidYoutubeLocators.put("search bar",
                By.xpath("//*[@content-desc='Buscar en YouTube' or @content-desc='Search YouTube']"));
        androidYoutubeLocators.put("search bar to write",
                By.id("com.google.android.youtube:id/search_edit_text"));
        androidYoutubeLocators.put("suggested search",
                By.xpath("//android.widget.TextView[@resource-id='com.google.android.youtube:id/text' and contains(@text,'" + words[0].toLowerCase() + "')]"));
        androidYoutubeLocators.put("results",
                By.xpath("//android.view.ViewGroup[contains(translate(@content-desc, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + words[0].toLowerCase() + "')]"));
        androidYoutubeLocators.put("play all", By.xpath("//android.view.ViewGroup[@content-desc='Play all']"));
        androidYoutubeLocators.put("video player", By.id("com.google.android.youtube:id/watch_while_time_bar_view"));
        searchVideoOnYoutube(androidDriver, androidYoutubeLocators, toSearch);
    }

    @AfterSuite
    public void cleanConfig() {
        tearDown();
    }
}

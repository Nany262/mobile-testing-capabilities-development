import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    public AndroidDriver androidDriver;
    public IOSDriver iosDriver;
    public AppiumDriverLocalService service;
    public WebDriverWait wait;

    public void appiumServerStart() {
        service = new AppiumServiceBuilder().withAppiumJS(
                        new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js")) // Cambiar por la ruta donde se encuentra tu appium
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();
    }

    public void configureAndroidDriver() throws MalformedURLException {
        //appiumServerStart();
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554") // Cambia por el nombre del emulador
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                // ABRIR aplicación YouTube
                // ESPERAR hasta que cargue la pantalla principal
                .setAppPackage("com.google.android.youtube")
                .setAppActivity("com.google.android.youtube.HomeActivity")
                .setNewCommandTimeout(Duration.ZERO);


        androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

        // ESPERA IMPLÍCITA: Configura un tiempo máximo para que todos los findElement esperen
        androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ESPERA EXPLÍCITA: Espera específicamente a que aparezca un resultado, se usa especificamente en el elemento
        wait = new WebDriverWait(androidDriver, Duration.ofSeconds(60));
    }

    public void tearDown() {
        androidDriver.quit();
        //service.stop();
    }
}

package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected AppiumDriver driver;
    protected AppiumDriverLocalService service;
    static ExtentReports extend;

    protected void configureAndroidDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("emulator-5554")
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAppPackage("com.google.android.youtube")
                .setAppActivity("com.google.android.youtube.HomeActivity")
                .amend("hideKeyboard", true)
                .setNewCommandTimeout(Duration.ofSeconds(60));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static ExtentReports configureReports() {
        String path = System.getProperty("user.dir") + "/reports/index.html";
        System.out.println(path);
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Youtube Automation");
        reporter.config().setDocumentTitle("Youtube Test Results");

        extend = new ExtentReports();
        extend.attachReporter(reporter);
        return extend;
    }
}
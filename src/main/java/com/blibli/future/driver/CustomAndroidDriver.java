package com.blibli.future.driver;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class CustomAndroidDriver implements DriverSource {

    @Override
    public WebDriver newDriver() {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("platformName", "Android");
        dc.setCapability("platformVersion", "11");
        dc.setCapability("automationName", "UiAutomator2");
        dc.setCapability("unicodeKeyboard", "true");
        dc.setCapability("resetKeyboard", "true");

        String apkPath = System.getProperty("user.dir") + "/src/test/resources/apps/PokedexApp.apk";
        dc.setCapability("app", apkPath);
        URL url = null;

        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url != null) {
            return new io.appium.java_client.android.AndroidDriver<>(url, dc);
        } else
            return null;
    }

    @Override
    public boolean takesScreenshots() {
        return false;
    }
}
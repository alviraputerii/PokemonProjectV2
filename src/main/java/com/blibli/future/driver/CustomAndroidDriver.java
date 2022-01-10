package com.blibli.future.driver;

import io.appium.java_client.android.AndroidDriver;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

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

        try {
            return new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public boolean takesScreenshots() {
        return false;
    }
}
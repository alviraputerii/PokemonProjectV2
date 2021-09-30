package com.blibli.future.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;

public class CustomDriver implements DriverSource {

    @Override
    public WebDriver newDriver() {
//        if (System.getProperty("driver").equalsIgnoreCase("chrome")) {
            ChromeOptions opt = new ChromeOptions();
            opt.addExtensions(new File("extension.crx"));
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(opt);
//        } else {
//            FirefoxOptions opt = new FirefoxOptions();
//            opt.addArguments("--private");
//            WebDriverManager.firefoxdriver().setup();
//            return new FirefoxDriver(opt);
//        }
    }

    @Override
    public boolean takesScreenshots() {
        return false;
    }
}
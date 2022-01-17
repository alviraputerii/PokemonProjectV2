package com.blibli.future.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomDriver implements DriverSource {

    @Override
    public WebDriver newDriver() {
//        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
//        String driver = EnvironmentSpecificConfiguration.from(variables).getProperty("webdriver.getBrowser");
//        System.out.println(driver);
//        if (driver.equalsIgnoreCase("chrome")) {
//            ChromeOptions opt = new ChromeOptions();
//            opt.addExtensions(new File("extension.crx"));
//            WebDriverManager.chromedriver().setup();
//            return new ChromeDriver(opt);
//        } else {
//            FirefoxOptions opt = new FirefoxOptions();
//            opt.addArguments("--private");
//            WebDriverManager.firefoxdriver().setup();
//            return new FirefoxDriver(opt);
//        }
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String driver = EnvironmentSpecificConfiguration.from(variables).getProperty("webdriver.getBrowser");
        System.out.println(driver);
        if (driver.equalsIgnoreCase("chrome")) {
            DesiredCapabilities dr = DesiredCapabilities.chrome();
            dr.setBrowserName("chrome");
            dr.setPlatform(Platform.ANY);
            ChromeOptions opt = new ChromeOptions();
            opt.addExtensions(new File("extension.crx"));
            opt.addArguments("--disable-dev-shm-usage");
            opt.merge(dr);
            try {
                return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), opt);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            DesiredCapabilities dr = DesiredCapabilities.firefox();
            dr.setBrowserName("firefox");
            dr.setPlatform(Platform.ANY);
            FirefoxOptions opt = new FirefoxOptions();
            opt.addArguments("--private");
            opt.addArguments("--disable-dev-shm-usage");
            opt.merge(dr);
            try {
                return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), opt);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean takesScreenshots() {
        return false;
    }
}
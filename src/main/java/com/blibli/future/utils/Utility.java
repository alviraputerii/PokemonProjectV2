package com.blibli.future.utils;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Slf4j
public class Utility extends PageObject {
    private final Properties properties;
    public static AndroidDriver ANDROID_DRIVER;

    public Utility() {
        BufferedReader reader;
        String propertyFilePath = "Configuration.properties";
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getWebsiteUrl(String web) {
        String url = properties.getProperty(web);
        if (url != null) return url;
        else throw new RuntimeException(web + "'s url not specified in the Configuration.properties file.");
    }

    public String getPokemon() {
        String pokemon = properties.getProperty("pokemon");
        if (pokemon != null) return pokemon;
        else throw new RuntimeException("Pokemon not specified in the Configuration.properties file.");
    }

    public static void clickByWebElement(WebElement element, WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].click();", element);
    }

    protected void clickByXpath(By xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(xpath));
        try {
            webElement.click();
        } catch (Exception e) {
            System.out.println("click normal failed");
            clickByWebElement(webElement, getDriver());
        }
    }

    protected Boolean verifyUrlIsOpened(String url) {
        System.out.println("currentUrl: " + getDriver().getCurrentUrl());
        System.out.println("expected: " + url);
        return getDriver().getCurrentUrl().contains(url);
    }

    protected String getTextByXpath(By xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(xpath));
        return webElement.getText();
    }

    protected List<WebElement> getALlWebElementByXpath(By xpath) {
        return waitForCondition().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(xpath));
    }

    protected Boolean isElementVisibleByXpath(By xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.elementToBeClickable(xpath));
        return webElement!=null;
    }

    protected void typeValueByXpath(By xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(xpath));
        webElement.sendKeys(value);
        webElement.sendKeys(Keys.ENTER);
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) ((WebDriverFacade) ThucydidesWebDriverSupport.getDriver()).getProxiedDriver();
    }

    public static void swipe(int startX, int startY, int endX, int endY, int duration) {
        log.info("Swipe (" + startX + "," + startY + "," + endX + "," + endY + "," + duration + ")");
        new TouchAction<>((PerformsTouchActions) ANDROID_DRIVER)
                .press(new PointOption().withCoordinates(startX, startY))
                .waitAction(new WaitOptions().withDuration(Duration.ofSeconds(duration)))
                .moveTo(new PointOption().withCoordinates(endX, endY))
                .release()
                .perform();
    }
}

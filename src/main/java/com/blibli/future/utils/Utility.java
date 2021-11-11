package com.blibli.future.utils;

import com.blibli.future.data.ParentJsonData;
import com.blibli.future.data.PokemonData;
import com.blibli.future.data.PokemonJsonData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Utility extends PageObject {
    private final Properties properties;

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

    protected void clickByString(String xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
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

    protected String getTextByString(String string) {
        By xpath = By.xpath(string);
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(xpath));
        return webElement.getText();
    }

    protected List<WebElement> getALlWebElementByString(String xpath) {
        return waitForCondition().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    protected Boolean isElementVisibleByString(String xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return webElement != null;
    }

    protected void typeValueByString(String xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElement.sendKeys(value);
        webElement.sendKeys(Keys.ENTER);
    }

    protected void typeValueWithoutEnterByString(String xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElement.sendKeys(value);
    }

    protected void sendKeyEnterWebsite(String xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElement.sendKeys(Keys.ENTER);
    }

    protected void sendKeyEnterMobile() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) ((WebDriverFacade) ThucydidesWebDriverSupport.getDriver()).getProxiedDriver();
    }

    public void setPokemonData(Map<String, Object> map) {
        ParentJsonData.putParentData(map);
    }

    public static String objToJsonString(Object obj) {
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = "{}";
        try {
            jsonStr = Obj.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public void writeJsonFile(String fileName) {
        String path = System.getProperty("user.dir") + "\\target\\jsonData\\" + fileName + ".json";
        String data = objToJsonString(ParentJsonData.getParentData());
        try {
            File file = new File(path);
            FileUtils.writeStringToFile(file, data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            PokemonJsonData.clearPokemonData();
            ParentJsonData.clearParentData();
        }
    }

    public List<Map<String, Object>> readJsonFile(String fileName) {
        String path = System.getProperty("user.dir") + "\\target\\jsonData\\" + fileName;
        List<Map<String, Object>> jsonMap;
        try {
            InputStream getJsonFile = new FileInputStream(path);
            jsonMap = new ObjectMapper().readValue(getJsonFile, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (IOException e) {
            jsonMap = new ArrayList<>();
        }
        return jsonMap;
    }

    public File[] getJsonFile() {
        File folder = new File("target/jsonData");
        return folder.listFiles();
    }

    public PokemonData convertClass(Object data) {
        return new ObjectMapper().convertValue(data, PokemonData.class);
    }
}

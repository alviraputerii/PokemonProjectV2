package com.blibli.future.utils;

import com.blibli.future.data.PokemonData;
import com.fasterxml.jackson.core.JsonParser;
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
import java.util.*;

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

    protected void clickByString(String string) {
        By xpath = By.xpath(string);
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

    protected String getTextByString(String string) {
        By xpath = By.xpath(string);
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
        return webElement != null;
    }

    protected void typeValueByXpath(By xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(xpath));
        webElement.sendKeys(value);
        webElement.sendKeys(Keys.ENTER);
    }

    protected void typeValueWithoutEnterByXpath(By xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(xpath));
        webElement.sendKeys(value);
    }

    protected void sendKeyEnterMobile() {
        getAndroidDriver().pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) ((WebDriverFacade) ThucydidesWebDriverSupport.getDriver()).getProxiedDriver();
    }

    public static String objToJsonString(Object obj) {
        ObjectMapper Obj = new ObjectMapper();
        String jsonStr = "{}";
        try {
            jsonStr = Obj.writeValueAsString(obj);
        } catch (IOException e) {
            System.out.println("parseObjectToJson:" + e);
        }
        return jsonStr;
    }

    public void writeJsonFile(String fileName, Map<String, Object> map){
        String path = System.getProperty("user.dir") + "\\target\\jsonData\\" + fileName + ".json";
        List<Map<String, Object>> currData = readJsonFile(fileName + ".json");
        Map<String, Object> mapData = new HashMap<>();
        mapData.putAll(map);
        currData.add(mapData);
        String data = objToJsonString(currData);
        System.out.println(data);
        try {
            File file = new File(path);
            FileUtils.writeStringToFile(file, data, "UTF-8");
//            FileWriter writer = new FileWriter(path);
//            writer.write(data);
//            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String,Object>> readJsonFile(String fileName) {
        String path = System.getProperty("user.dir") + "\\target\\jsonData\\" + fileName;
        List<Map<String, Object>> jsonMap;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            InputStream getJsonFile = new FileInputStream(path);
            System.out.println("file read");
            jsonMap = objectMapper.readValue(getJsonFile, new TypeReference<List<Map<String, Object>>>() {
            });
            System.out.println("file read2");
        } catch (IOException e) {
            jsonMap = new ArrayList<>();
            e.printStackTrace();
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

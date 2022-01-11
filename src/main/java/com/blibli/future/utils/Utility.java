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
import org.apache.commons.net.util.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static void clickByWebElement(WebElement element, WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].click();", element);
    }

    public void clickByString(String xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        try {
            webElement.click();
        } catch (Exception e) {
            System.out.println("click normal failed");
            clickByWebElement(webElement, getDriver());
        }
    }

    public String getTextByString(String string) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(string)));
        return webElement.getText();
    }

    public List<WebElement> getALlWebElementByString(String xpath) {
        return waitForCondition().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    public Boolean isElementVisibleByString(String xpath) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return webElement != null;
    }

    public void typeValueByString(String xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElement.sendKeys(value);
        webElement.sendKeys(Keys.ENTER);
    }

    public void typeValueWithoutEnterByString(String xpath, String value) {
        WebElement webElement = waitForCondition().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webElement.sendKeys(value);
    }

    public void switchTabs() {
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(0));
    }

    public void startRecord(String platform, String fileName) throws Exception {
        if (platform.equalsIgnoreCase("website")) VideoRecorder_utlity.startRecord(platform + "-" + fileName);
        else if (platform.equalsIgnoreCase("mobile")) getAndroidDriver().startRecordingScreen();
    }

    public void stopRecord(String platform, String pokemon) throws Exception {
        if (platform.equalsIgnoreCase("website")) VideoRecorder_utlity.stopRecord();
        else if (platform.equalsIgnoreCase("mobile")) {
            String base64String = getAndroidDriver().stopRecordingScreen();
            byte[] data = Base64.decodeBase64(base64String);
            String destinationPath = "./target/automationrecordings/" + platform + "-" + pokemon + ".mp4";
            Path path = Paths.get(destinationPath);
            Files.write(path, data);
        }
    }

    public void sendKeyEnterMobile() {
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

package com.blibli.future.common;

import com.blibli.future.utils.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class CommonAction extends Utility {

    public void openPage(String url) {
        openAt(url);
    }

    public Boolean verifyPageIsOpened(String url) {
        new WebDriverWait(getDriver(), 10).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        return verifyUrlIsOpened(url);
    }
    public void switchTabs(){
        ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(0));
    }
}
package com.blibli.future.pages;

import com.blibli.future.common.CommonAction;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;

public class BulbapediaHomePage extends Utility {
    CommonAction commonAction;

    private final By searchInput = By.xpath("//*[@id='searchInput']");

    public void openBulbapediaHomePage() {
        commonAction.openPage(getWebsiteUrl("urlBulbapedia"));
    }

    public void inputKeyword(String keyword) {
        typeValueByXpath(searchInput, keyword);
    }
}
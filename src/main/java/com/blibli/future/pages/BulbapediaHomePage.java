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

    public void searchPokemon(String keyword) {
        String currentUrl = getDriver().getCurrentUrl();
        typeValueByXpath(searchInput, keyword);

        if (getDriver().getCurrentUrl().equals(currentUrl)) {
            System.out.println("------------------------------------------");
            System.out.println(currentUrl);
            System.out.println(getDriver().getCurrentUrl());
            clickByXpath(searchInput);
            sendKeyEnterWebsite(searchInput);
        }
    }
}
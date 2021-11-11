package com.blibli.future.pages;

import com.blibli.future.common.CommonAction;
import com.blibli.future.utils.Utility;

public class BulbapediaHomePage extends Utility {
    CommonAction commonAction;

    private final String searchInput = "//*[@id='searchInput']";

    public void openBulbapediaHomePage() {
        commonAction.openPage(getWebsiteUrl("urlBulbapedia"));
    }

    public void searchPokemon(String keyword) {
        String currentUrl = getDriver().getCurrentUrl();
        typeValueByString(searchInput, keyword);

//        if (getDriver().getCurrentUrl().equals(currentUrl)) {
        System.out.println("------------------------------------------");
        System.out.println(currentUrl);
        System.out.println(getDriver().getCurrentUrl());
//            clickByString(searchInput);
//            sendKeyEnterWebsite(searchInput);
//            System.out.println("reenter");
//        }
    }
}
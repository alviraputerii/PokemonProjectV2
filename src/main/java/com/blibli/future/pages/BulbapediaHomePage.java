package com.blibli.future.pages;

import com.blibli.future.utils.Utility;

public class BulbapediaHomePage extends Utility {
    private final String searchInput = "//*[@id='searchInput']";
    private final String noResultText = "//*[@id='mw-content-text']//p[2]";

    public void openBulbapediaHomePage() {
        openAt(getWebsiteUrl("urlBulbapedia"));
    }

    public void searchPokemon(String keyword) {
        typeValueByString(searchInput, keyword);
    }

    public String getSearchResultText() {
        return getTextByString(noResultText);
    }
}
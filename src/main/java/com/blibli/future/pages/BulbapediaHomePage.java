package com.blibli.future.pages;

import com.blibli.future.utils.Utility;

public class BulbapediaHomePage extends Utility {
    private final String searchInput = "//*[@id='searchInput']";

    public void openBulbapediaHomePage() {
        openAt(getWebsiteUrl("urlBulbapedia"));
    }

    public void searchPokemon(String keyword) {
        typeValueByString(searchInput, keyword);
    }
}
package com.blibli.future.pages;

import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;

public class PokedexHomePage extends Utility {

    private final By searchInput = By.xpath("//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/searchView']");
    private String searchResult = "//android.widget.TextView[@text='%s']";

    public void searchPokemon(String keyword) {
        clickByXpath(searchInput);
        typeValueWithoutEnterByXpath(searchInput, keyword);
        sendKeyEnterMobile();
        clickByString(String.format(searchResult, keyword));
    }
}
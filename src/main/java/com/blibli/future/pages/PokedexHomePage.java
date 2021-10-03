package com.blibli.future.pages;

import com.blibli.future.common.CommonAction;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;

public class PokedexHomePage extends Utility {
    CommonAction commonAction;

    private final By searchInput = By.xpath("//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/searchView']");

    public void inputKeyword(String keyword) {
        clickByXpath(searchInput);
        typeValueByXpath(searchInput, keyword);
    }
}
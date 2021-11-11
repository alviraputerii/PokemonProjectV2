package com.blibli.future.pages;

import com.blibli.future.utils.Utility;

public class PokedexHomePage extends Utility {

    private final String searchInput = "//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/searchView']";
    private final String btnConfirm = "//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/confirm_button']";
    private final String searchResult = "//android.widget.TextView[@text='%s']";

    public void searchPokemon(String keyword) {
        try {
            if (isElementVisibleByString(btnConfirm)) clickByString(btnConfirm);
        } catch (Exception ex) {
            System.out.println("no such element");
        }
        clickByString(searchInput);
        typeValueWithoutEnterByString(searchInput, keyword);
        sendKeyEnterMobile();
        clickByString(String.format(searchResult, keyword));
    }
}
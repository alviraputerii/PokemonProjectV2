package com.blibli.future.pages;

import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PokemonDbHomePage extends Utility {
    private final String privacyControlOk = "//*[@id='gdpr-confirm']//button";
    private final String searchInput = "//*[@id='sitesearch']";
    private final String pokemonResult = "//div[@class='gsc-webResult gsc-result'][1]//div[@class='gs-title']/a";
    private final String noResultText = "//div[@class='gs-snippet']";

    public void openPokemonDbHomePage() {
        openAt(getWebsiteUrl("urlPokemonDb"));
    }

    public void searchPokemon(String keyword) {
        try {
            if (isElementVisibleByString(privacyControlOk)) clickByString(privacyControlOk);
        } catch (Exception ex) {
            System.out.println("no such element");
        }

        clickByString(searchInput);
        typeValueByString(searchInput, keyword);
    }

    public void clickPokemonPokedex() {
        try {
            WebElement dataClick = getDriver().findElement(By.xpath(pokemonResult));
            System.out.println(dataClick.getAttribute("data-cturl"));
            while(!dataClick.getAttribute("data-cturl").contains("pokedex")){getDriver().navigate().refresh();}
            clickByWebElement(dataClick, getDriver());
        }catch (Exception e){
            clickByString(pokemonResult);
        }
    }

    public String getSearchResultText() {
        return getTextByString(noResultText);
    }
}
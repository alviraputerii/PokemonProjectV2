package com.blibli.future.pages;

import com.blibli.future.common.CommonAction;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PokemonDbHomePage extends Utility {
    CommonAction commonAction;

    private final By privacyControlOk = By.xpath("//*[@id='gdpr-confirm']//button");
    private final By searchInput = By.xpath("//*[@id='sitesearch']");
    private final By pokemonResult = By.xpath("//div[@class='gsc-webResult gsc-result'][1]//div[@class='gs-title']/a");

    public void openPokemonDbHomePage() {
        commonAction.openPage(getWebsiteUrl("urlPokemonDb"));
    }

    public void searchPokemon(String keyword) {
        try {
            if (isElementVisibleByXpath(privacyControlOk)) clickByXpath(privacyControlOk);
        } catch (Exception ex) {
            System.out.println("no such element");
        }

        clickByXpath(searchInput);
        typeValueByXpath(searchInput, keyword);
    }

    public void clickPokemonPokedex() {
        WebElement dataClick = getDriver().findElement(pokemonResult);
        clickByWebElement(dataClick, getDriver());
    }
}
package com.blibli.future.pages;

import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class BulbapediaPokemonPage extends Utility {

    private final By pokemonName = By.xpath("//h1[@id='firstHeading']");
    private final By pokemonNumber = By.xpath("//th//a[contains(@title,'National Pokédex number')]");
    private final By pokemonType = By.xpath("//a[contains(@title,'Type')]/ancestor::td//td[1]/table//a[contains(@title,'(type)')]//b[not(contains(text(),'Unknown'))]");
    private final By pokemonBaseStat = By.xpath("//*[@id='Base_stats']/parent::h4/following-sibling::table[1]//div[2]");
    private final By pikachuBaseStat = By.xpath("//*[@id='Generation_VI_onward']/parent::h5/following-sibling::table[1]//div[2]");

    public String getPokemonName() {
        return getTextByXpath(pokemonName).split(" ")[0].toLowerCase();
    }

    public Integer getPokemonNumber() {
        return Integer.valueOf(getTextByXpath(pokemonNumber).substring(1));
    }

    public List<String> getPokemonTypes() {
        return getALlWebElementByXpath(pokemonType).stream().map(type -> type.getAttribute("textContent").toLowerCase()).collect(Collectors.toList());
    }

    public List<Integer> getPokemonStats(String poke) {
        By pokemonStat = poke.equalsIgnoreCase("pikachu") ? pikachuBaseStat : pokemonBaseStat;
        List<Integer> pokemonStats = getALlWebElementByXpath(pokemonStat).stream().map(stat -> Integer.valueOf(stat.getAttribute("textContent"))).collect(Collectors.toList());
        pokemonStats.remove(pokemonStats.size() - 1);
        return pokemonStats;
    }
}
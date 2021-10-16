package com.blibli.future.pages;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String, Integer> getPokemonStats(String poke) {
        By pokemonStat = poke.equalsIgnoreCase("pikachu") ? pikachuBaseStat : pokemonBaseStat;
        List<WebElement> stats = getALlWebElementByXpath(pokemonStat);
        Map<String, Integer> pokemonStats = new HashMap<>();
        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
            pokemonStats.put(ParamConstant.baseStatsName.get(i), Integer.valueOf(stats.get(i).getAttribute("textContent")));
        }
        return pokemonStats;
    }
}
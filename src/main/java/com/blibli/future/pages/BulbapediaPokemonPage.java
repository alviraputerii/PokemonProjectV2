package com.blibli.future.pages;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BulbapediaPokemonPage extends Utility {

    private final String pokemonName = "//h1[@id='firstHeading']";
    private final String pokemonNumber = "//th//a[contains(@title,'National Pokédex number')]";
    private final String pokemonType = "//a[contains(@title,'Type')]/ancestor::td//td[1]/table//a[contains(@title,'(type)')]//b[not(contains(text(),'Unknown'))]";
    private final String pokemonBaseStat = "//*[@id='Base_stats']/parent::h4/following-sibling::table[1]//div[2]";
    private final String otherBaseStat = "//span[contains(@id,'onward')]/parent::h5/following-sibling::table[1]//div[2]";
    private final String pokemonBaseExperience = "//a[contains(@title,'Experience')]/parent::b/following-sibling::table//td[3]";
    private final String pokemonSpecies = "//a[contains(@title,'Pokémon category')]/span";
    private final String pokemonGrowthRate = "//span[contains(text(),'Leveling rate')]/ancestor::b/following-sibling::table//td";

    public String getPokemonName() {
        return getTextByString(pokemonName).split(" ")[0].toLowerCase();
    }

    public Integer getPokemonNumber() {
        return Integer.valueOf(getTextByString(pokemonNumber).substring(1));
    }

    public List<String> getPokemonTypes() {
        return getALlWebElementByString(pokemonType).stream().map(type -> type.getAttribute("textContent").toLowerCase()).collect(Collectors.toList());
    }

    public Map<String, Integer> getPokemonStats() {
        String pokemonStat = "";
        try{
            if (isElementVisibleByString(otherBaseStat)) pokemonStat = otherBaseStat;
        }catch (Exception e){
            pokemonStat = pokemonBaseStat;
        }
        List<WebElement> stats = getALlWebElementByString(pokemonStat);
        Map<String, Integer> pokemonStats = new HashMap<>();
        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
            pokemonStats.put(ParamConstant.baseStatsName.get(i), Integer.valueOf(stats.get(i).getAttribute("textContent")));
        }
        return pokemonStats;
    }
    public Integer getPokemonBaseExperience() {
        String experience = getTextByString(pokemonBaseExperience);
        return Integer.valueOf(experience.substring(0,experience.length()-3));
    }

    public String getPokemonSpecies() {
        return getTextByString(pokemonSpecies);
    }

    public String getPokemonGrowthRate() {
        return getTextByString(pokemonGrowthRate);
    }
}
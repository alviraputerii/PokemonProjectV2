package com.blibli.future.pages;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokemonDbPokemonPage extends Utility {
    private final String pokemonName = "//main[@id='main']//em";
    private final String pokemonNumber = "//div/table//th[text()='National №']/parent::tr/td/strong";
    private final String pokemonTypes = "//div[contains(@class,'active')]//table//th[text()='Type']/parent::tr/td/a";
    private final String pokemonStats = "//div[contains(@class,'active')]//h2[contains(text(),'Base stats')]/parent::div//tr/td[1]";
    private final String pokemonBaseExperience = "//div[contains(@class,'active')]//table//th[text()='Base Exp.']/following-sibling::td";
    private final String pokemonSpecies = "//div[contains(@class,'active')]//table//th[text()='Species']/following-sibling::td";
    private final String pokemonGrowthRate = "//div[contains(@class,'active')]//table//th[text()='Growth Rate']/following-sibling::td";

    public String getPokemonName() {
        return getTextByString(pokemonName).toLowerCase();
    }

    public Integer getPokemonNumber() {
        return Integer.valueOf(getTextByString(pokemonNumber));
    }

    public List<String> getPokemonTypes() {
        return getALlWebElementByString(pokemonTypes).stream().map(type -> type.getAttribute("textContent").toLowerCase()).collect(Collectors.toList());
    }

    public Map<String, Integer> getPokemonStats() {
        List<WebElement> stats = getALlWebElementByString(pokemonStats);
        Map<String, Integer> pokemonStats = new HashMap<>();
        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
            pokemonStats.put(ParamConstant.baseStatsName.get(i), Integer.valueOf(stats.get(i).getAttribute("textContent")));
        }
        return pokemonStats;
    }

    public Integer getPokemonBaseExperience() {
        return Integer.valueOf(getTextByString(pokemonBaseExperience));
    }

    public String getPokemonSpecies() {
        return getTextByString(pokemonSpecies);
    }

    public String getPokemonGrowthRate() {
        return getTextByString(pokemonGrowthRate);
    }
}

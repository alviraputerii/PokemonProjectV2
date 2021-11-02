package com.blibli.future.pages;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokemonDbPokemonPage extends Utility {
    private final By pokemonName = By.xpath("//main[@id='main']//em");
    private final By pokemonNumber = By.xpath("//div/table//th[text()='National №']/parent::tr/td/strong");
    private final By pokemonTypes = By.xpath("//div[contains(@class,'active')]//table//th[text()='Type']/parent::tr/td/a");
    private final By pokemonStats = By.xpath("//div[contains(@class,'active')]//h2[contains(text(),'Base stats')]/parent::div//tr/td[1]");

    public String getPokemonName() {
        return getTextByXpath(pokemonName).toLowerCase();
    }

    public Integer getPokemonNumber() {
        return Integer.valueOf(getTextByXpath(pokemonNumber));
    }

    public List<String> getPokemonTypes() {
        return getALlWebElementByXpath(pokemonTypes).stream().map(type -> type.getAttribute("textContent").toLowerCase()).collect(Collectors.toList());
    }

    public Map<String, Integer> getPokemonStats() {
        List<WebElement> stats = getALlWebElementByXpath(pokemonStats);
        Map<String, Integer> pokemonStats = new HashMap<>();
        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
            pokemonStats.put(ParamConstant.baseStatsName.get(i), Integer.valueOf(stats.get(i).getAttribute("textContent")));
        }
        return pokemonStats;
    }
}

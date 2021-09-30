package com.blibli.future.pages;

import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class PokemonDbPokemonPage extends Utility {
    private final By pokemonName = By.xpath("//*[@id='main']/h1");
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

    public List<Integer> getPokemonStats() {
        List<Integer> stats = getALlWebElementByXpath(pokemonStats).stream().map(stat -> Integer.valueOf(stat.getText())).collect(Collectors.toList());
        stats.remove(stats.size() - 1);
        return stats;
    }
}

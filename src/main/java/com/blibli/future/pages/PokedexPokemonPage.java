package com.blibli.future.pages;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.utils.Utility;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class PokedexPokemonPage extends Utility {

    private final By pokemonName = By.xpath("//android.widget.TextView");
    private final By pokemonNumber = By.xpath("//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/pokemon_number']");
    private String baseStat = "//android.widget.FrameLayout[%d]/android.view.ViewGroup/android.widget.TextView[1]";

    public String getPokemonName() {
        return getTextByXpath(pokemonName).toLowerCase();
    }

    public Integer getPokemonNumber() {
        return Integer.valueOf(getTextByXpath(pokemonNumber));
    }

    public  Map<String, Integer> getPokemonStats() {
        Map<String, Integer> pokemonStats = new HashMap<>();
        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
            pokemonStats.put(ParamConstant.baseStatsName.get(i), Integer.valueOf(getTextByString(String.format(baseStat, i+1))));
        }
        return pokemonStats;
    }
}

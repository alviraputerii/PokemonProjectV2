package com.blibli.future.pages;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.utils.Utility;

import java.util.HashMap;
import java.util.Map;

public class PokedexPokemonPage extends Utility {

    private final String pokemonName = "//android.widget.TextView";
    private final String pokemonNumber = "//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/pokemon_number']";
    private final String baseStat = "//android.widget.FrameLayout[%d]/android.view.ViewGroup/android.widget.TextView[1]";
    private final String pokemonHeight = "//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/pokemon_item_height']";
    private final String pokemonWeight = "//*[@resource-id='dev.ronnie.pokeapiandroidtask:id/pokemon_item_weight']";

    public String getPokemonName() {
        return getTextByString(pokemonName).toLowerCase();
    }

    public Integer getPokemonNumber() {
        return Integer.valueOf(getTextByString(pokemonNumber));
    }

    public Map<String, Integer> getPokemonStats() {
        Map<String, Integer> pokemonStats = new HashMap<>();
        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
            pokemonStats.put(ParamConstant.baseStatsName.get(i), Integer.valueOf(getTextByString(String.format(baseStat, i + 1))));
        }
        return pokemonStats;
    }

    public Double getPokemonHeight() {
        return Double.valueOf(getTextByString(pokemonHeight).split(" ")[0]);
    }

    public Double getPokemonWeight() {
        return Double.valueOf(getTextByString(pokemonWeight).split(" ")[0]);
    }

}

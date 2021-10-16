package com.blibli.future.data;

import java.util.HashMap;
import java.util.Map;

public class PokemonJsonData {
    private static Map<String, Object> pokemonData = new HashMap<>();
    private static PokemonData data = new PokemonData();

    public static void putPokemonData(String key, Object value){
        pokemonData.put(key, value);
    }

    public static Map<String, Object> getPokemonData(){
        return pokemonData;
    }

    public static void clearPokemonData(){
        pokemonData.clear();
    }
}

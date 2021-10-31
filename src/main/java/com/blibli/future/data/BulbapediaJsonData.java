package com.blibli.future.data;

import com.blibli.future.constant.ParamConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BulbapediaJsonData {
    private static Map<String, Object> pokemonData = new HashMap<>();

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

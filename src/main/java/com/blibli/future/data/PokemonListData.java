package com.blibli.future.data;

import com.blibli.future.constant.ParamConstant;

import java.util.ArrayList;
import java.util.List;

public class PokemonListData {
    private static List<PokemonData> listBulbapediaData = new ArrayList<>();
    private static List<PokemonData> listPokemonDbData = new ArrayList<>();
    private static List<PokemonData> listPokeApiData = new ArrayList<>();
    private static List<PokemonData> listPokedexData = new ArrayList<>();

    public static void putParentListData(PokemonData pokemonData, String source) {
        switch (source) {
            case ParamConstant.bulbapediaData:
                listBulbapediaData.add(pokemonData);
                break;
            case ParamConstant.pokemonDbData:
                listPokemonDbData.add(pokemonData);
                break;
            case ParamConstant.pokeApiData:
                listPokeApiData.add(pokemonData);
                break;
            default:
                listPokedexData.add(pokemonData);
        }
    }

    public static List<PokemonData> getParentListData(String source) {
        List<PokemonData> currData;
        switch (source) {
            case ParamConstant.bulbapediaData:
                currData = listBulbapediaData;
                break;
            case ParamConstant.pokemonDbData:
                currData =  listPokemonDbData;
                break;
            case ParamConstant.pokeApiData:
                currData =  listPokeApiData;
                break;
            default:
                currData =  listPokedexData;
        }
        return currData;
    }

}

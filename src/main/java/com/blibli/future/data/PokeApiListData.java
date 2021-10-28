package com.blibli.future.data;

import com.blibli.future.constant.ParamConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PokeApiListData {
    private static List<PokemonData> listPokeApiData = new ArrayList<>();

    public static void createParentListData(Long threadId) {
        PokemonData newData = new PokemonData();
        newData.setThreadId(threadId);
        listPokeApiData.add(newData);
    }

    public static void putParentListData(String category, Object pokemonData, Long threadId) {
        for (PokemonData pokemon : listPokeApiData) {
            if (threadId == pokemon.getThreadId())
                switch (category) {
                    case ParamConstant.name:
                        pokemon.setName(pokemonData.toString());
                        break;
                    case ParamConstant.number:
                        pokemon.setNumber(Integer.parseInt(pokemonData.toString()));
                        break;
                    case ParamConstant.type:
                        pokemon.setType((List<String>) pokemonData);
                        break;
                    default:
                        pokemon.setBaseStats((Map<String, Integer>) pokemonData);
                }
        }
    }

    public static List<PokemonData> getParentListData() {
        return listPokeApiData;
    }
}
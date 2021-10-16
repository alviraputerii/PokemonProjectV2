package com.blibli.future.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParentJsonData {
    private static List<Map<String, Object>> listData = new ArrayList<>();
    private static PokemonJsonData pokemonJsonData = new PokemonJsonData();

    public static void putParentData(String key, Map<String, Object> value) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(key, value);
        listData.add(mapData);
    }

    public static List<Map<String, Object>> getParentData() {
        return listData;
    }

    public static void clearParentData() {
        listData.clear();
    }
}

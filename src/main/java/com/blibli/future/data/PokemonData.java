package com.blibli.future.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PokemonData {
    private String name;
    private int number;
    private List<String> type;
    private Map<String, Integer> baseStats;
    private String species;
    private String growthRate;
    private double height;
    private double weight;
}

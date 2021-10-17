package com.blibli.future.data;

import lombok.Data;

import java.util.List;

@Data
public class PokemonData {
    private String name;
    private int number;
    private List<String> type;
    private List<Integer> speciesStats;
}

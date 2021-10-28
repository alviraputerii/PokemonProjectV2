package com.blibli.future.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PokemonData {
    private String name;
    private long threadId;
    private int number;
    private List<String> type;
    private Map<String, Integer> baseStats;
}

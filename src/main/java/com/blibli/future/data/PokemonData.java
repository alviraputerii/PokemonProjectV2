package com.blibli.future.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class PokemonData {
    private String name;
    private int number;
    private List<String> type;
    private Map<String, Integer> baseStats;
}

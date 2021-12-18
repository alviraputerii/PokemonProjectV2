package com.blibli.future.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GetPokemonApiResponse {
    private Long id;
    private String name;
    private List<Stat> stats;
    private List<Type> types;
    private Long base_experience;
}

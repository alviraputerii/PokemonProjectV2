package com.blibli.future.response;

import lombok.Data;

@Data
public class Stat {
    private Long base_stat;
    private Long effort;
    private String name;
    private Stat stat;
    private String url;
}

package com.blibli.future.response;

import lombok.Data;

@Data
public class Type {
    private String name;
    private Long slot;
    private Type type;
    private String url;
}

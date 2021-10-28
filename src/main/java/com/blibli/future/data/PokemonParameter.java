package com.blibli.future.data;

public class PokemonParameter {
    private static String parameter = "";

    public static void setParameter(String key) {
        parameter = key;
    }

    public static String getParameter() {
        return parameter;
    }

    public static void clearParameter() {
        parameter = "";
    }
}

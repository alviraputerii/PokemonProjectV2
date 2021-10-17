package com.blibli.future.service;

//import io.qameta.allure.restassured.AllureRestAssured;
import com.blibli.future.utils.Utility;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PokeApiController extends Utility {
    public Response getPokemon(String url, String pokemonName){
    return RestAssured.given().
//            filter(new AllureRestAssured()).
            header("Content-Type", "application/json").
            header("Accept", "application/json").
            when().
            get(url + pokemonName);
    }
}

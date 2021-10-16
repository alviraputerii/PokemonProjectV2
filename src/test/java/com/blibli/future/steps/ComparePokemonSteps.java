package com.blibli.future.steps;

import com.blibli.future.common.CommonAction;
import com.blibli.future.constant.ParamConstant;
import com.blibli.future.data.PokemonData;
import com.blibli.future.pages.*;
import com.blibli.future.response.GetPokemonApiResponse;
import com.blibli.future.service.PokeApiController;
import com.blibli.future.utils.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComparePokemonSteps extends Utility {

    CommonAction commonAction;
    BulbapediaHomePage bulbapediaHomePage;
    BulbapediaPokemonPage bulbapediaPokemonPage;
    PokemonDbHomePage pokemonDbHomePage;
    PokemonDbPokemonPage pokemonDbPokemonPage;
    PokedexHomePage pokedexHomePage;
    PokedexPokemonPage pokedexPokemonPage;
    GetPokemonApiResponse getPokemonApiResponse;
    Response response;
    boolean isBulbapediaDataExist, isPokemonDbDataExist, isPokeApiDataExist;
    PokemonData bulbapediaPokemon = new PokemonData();
    PokemonData pokemonDbPokemon = new PokemonData();
    PokemonData pokeapiPokemon = new PokemonData();
    PokemonData pokedexPokemon = new PokemonData();
    SoftAssert softAssert = new SoftAssert();

    //---------------------------- Open URL
    @Given("open bulbapedia home page")
    public void openBulbapediaHomePage() {
        commonAction.switchTabs();
        bulbapediaHomePage.openBulbapediaHomePage();
    }

    @Given("open pokemondb home page")
    public void openPokemondbHomePage() {
        pokemonDbHomePage.openPokemonDbHomePage();
    }

    //---------------------------- Verify Page is Opened
    @Given("bulbapedia home page should be opened")
    public void bulbapediaHomePageShouldBeOpened() {
        String url = getWebsiteUrl("urlBulbapedia");
        softAssert.assertTrue(commonAction.verifyPageIsOpened(url), "page not opened");
    }

    @Given("pokemondb home page should be opened")
    public void pokemondbHomePageShouldBeOpened() {
        String url = getWebsiteUrl("urlPokemonDb");
        softAssert.assertTrue(commonAction.verifyPageIsOpened(url), "page not opened");
    }

    //---------------------------- Search for Pokemon
    @When("at bulbapedia home page search for {string}")
    public void atBulbapediaHomePageSearchForPokemonPokemon(String pokemon) {
        bulbapediaHomePage.searchPokemon(pokemon);
    }

    @When("at pokemondb home page search for {string}")
    public void atPokemondbHomePageSearchForPokemonPokemon(String pokemon) {
        try {
            pokemonDbHomePage.searchPokemon(pokemon);
            pokemonDbHomePage.clickPokemonPokedex();
            isPokemonDbDataExist = true;
        } catch (Exception e) {
            isPokemonDbDataExist = false;
        }
    }

    @When("send api request for {string}")
    public void sendApiRequestForPokemonPokemon(String pokemon) {
        PokeApiController pokeApiController = new PokeApiController();
        response = pokeApiController.getPokemon(getWebsiteUrl("urlPokeApi"), pokemon.toLowerCase());
    }

    //---------------------------- Check Response Code
    @Then("api response code should be {int}")
    public void apiResponseCodeShouldBe(int code) {
        softAssert.assertTrue(response.getStatusCode() == code, "Wrong response code");
    }

    //---------------------------- Get Data
    @Then("at bulbapedia pokemon page get following {string} data")
    public void atBulbapediaPokemonPageGetFollowingData(String pokemon, List<String> data) {
        try {
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        bulbapediaPokemon.setName(bulbapediaPokemonPage.getPokemonName());
                        System.out.println(bulbapediaPokemon.getName());
                        break;
                    case "number":
                        bulbapediaPokemon.setNumber(bulbapediaPokemonPage.getPokemonNumber());
                        System.out.println(bulbapediaPokemon.getNumber());
                        break;
                    case "types":
                        bulbapediaPokemon.setType(bulbapediaPokemonPage.getPokemonTypes());
                        System.out.println(bulbapediaPokemon.getType());
                        break;
                    default:
                        bulbapediaPokemon.setSpeciesStats(bulbapediaPokemonPage.getPokemonStats(pokemon));
                        System.out.println(bulbapediaPokemon.getSpeciesStats());
                }
            }
            isBulbapediaDataExist = true;
        } catch (Exception e) {
            isBulbapediaDataExist = false;
        } finally {
            Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        }
    }

    @Then("at pokemondb pokemon page get following data")
    public void atPokemondbPokemonPageGetFollowingData(List<String> data) {
        Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        if (isPokemonDbDataExist) {
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        pokemonDbPokemon.setName(pokemonDbPokemonPage.getPokemonName());
                        System.out.println(pokemonDbPokemon.getName());
                        break;
                    case "number":
                        pokemonDbPokemon.setNumber(pokemonDbPokemonPage.getPokemonNumber());
                        System.out.println(pokemonDbPokemon.getNumber());
                        break;
                    case "types":
                        pokemonDbPokemon.setType(pokemonDbPokemonPage.getPokemonTypes());
                        System.out.println(pokemonDbPokemon.getType());
                        break;
                    default:
                        pokemonDbPokemon.setSpeciesStats(pokemonDbPokemonPage.getPokemonStats());
                        System.out.println(pokemonDbPokemon.getSpeciesStats());
                }
            }
        }
    }

    @Then("get following data from response")
    public void getFollowingDataFromResponse(List<String> data) {
        try {
            getPokemonApiResponse = response.getBody().as(GetPokemonApiResponse.class);
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        pokeapiPokemon.setName(getPokemonApiResponse.getName());
                        System.out.println(pokeapiPokemon.getName());
                        break;
                    case "number":
                        pokeapiPokemon.setNumber(getPokemonApiResponse.getId().intValue());
                        System.out.println(pokeapiPokemon.getNumber());
                        break;
                    case "types":
                        List<String> pokeApiType = getPokemonApiResponse.getTypes().stream().map(ty -> ty.getType().getName()).collect(Collectors.toList());
                        pokeapiPokemon.setType(pokeApiType);
                        System.out.println(pokeapiPokemon.getType());
                        break;
                    default:
                        List<Integer> pokeApiStats = getPokemonApiResponse.getStats().stream().map(st -> st.getBase_stat().intValue()).collect(Collectors.toList());
                        Map<String, Integer> pokemonStats = new HashMap<>();
                        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
                            pokemonStats.put(ParamConstant.baseStatsName.get(i), pokeApiStats.get(i));
                        }
                        pokeapiPokemon.setSpeciesStats(pokemonStats);
                        System.out.println(pokeapiPokemon.getSpeciesStats());
                }
            }
            isPokeApiDataExist = true;
        } catch (Exception e) {
            isPokeApiDataExist = false;
        }
    }

    //---------------------------- Compare Data
    @Then("compare following pokemon data from bulbapedia, pokemondb and pokeapi")
    public void compareFollowingPokemonDataFromBulbapediaPokemondbAndPokeapi(List<String> data) {
        if (isBulbapediaDataExist && isPokemonDbDataExist && isPokeApiDataExist) {
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        boolean checkName = bulbapediaPokemon.getName().equalsIgnoreCase(pokemonDbPokemon.getName()) &&
                                bulbapediaPokemon.getName().equalsIgnoreCase(pokeapiPokemon.getName());
                        softAssert.assertTrue(checkName, "Pokemon name doesn't match");
                        break;
                    case "number":
                        boolean checkNumber = bulbapediaPokemon.getNumber() == pokemonDbPokemon.getNumber() &&
                                bulbapediaPokemon.getNumber() == pokeapiPokemon.getNumber();
                        softAssert.assertTrue(checkNumber, "Pokemon number doesn't match");
                        break;
                    case "types":
                        boolean checkType = bulbapediaPokemon.getType().equals(pokemonDbPokemon.getType()) &&
                                bulbapediaPokemon.getType().equals(pokeapiPokemon.getType());
                        softAssert.assertTrue(checkType, "Pokemon type doesn't match");
                        break;
                    default:
//                        boolean checkStats = bulbapediaPokemon.getSpeciesStats().equals(pokemonDbPokemon.getSpeciesStats()) &&
//                                bulbapediaPokemon.getSpeciesStats().equals(pokeapiPokemon.getSpeciesStats());
                        boolean checkStats = bulbapediaPokemon.getSpeciesStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokemonDbPokemon.getSpeciesStats().get(e.getKey()))) &&
                                bulbapediaPokemon.getSpeciesStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokeapiPokemon.getSpeciesStats().get(e.getKey())));
                        softAssert.assertTrue(checkStats, "Pokemon base stat doesn't match");
                }
            }
        } else {
            softAssert.assertTrue(isBulbapediaDataExist, "Comparison failed because pokemon doesn't exist in Bulbapedia");
            softAssert.assertTrue(isPokemonDbDataExist, "Comparison failed because pokemon doesn't exist in PokemonDb");
            softAssert.assertTrue(isPokeApiDataExist, "Comparison failed because pokemon doesn't exist in PokeApi");
        }
            softAssert.assertAll();
    }

    //---------------------------- Mobile Step
    @When("at pokedex app home page search for {string}")
    public void atAppHomePageSearchForPokemon(String value) {
        pokedexHomePage.searchPokemon(value);
    }

    @Then("at pokedex app pokemon page get following data")
    public void atPokedexAppPokemonPageGetFollowingData(List<String> data) {
        for (String dt : data) {
            switch (dt) {
                case "name":
                    pokedexPokemon.setName(pokedexPokemonPage.getPokemonName());
                    System.out.println(pokedexPokemon.getName());
                    break;
                case "number":
                    pokedexPokemon.setNumber(pokedexPokemonPage.getPokemonNumber());
                    System.out.println(pokedexPokemon.getNumber());
                    break;
                default:
                    pokedexPokemon.setSpeciesStats(pokedexPokemonPage.getPokemonStats());
                    System.out.println(pokedexPokemon.getSpeciesStats());
            }
        }
    }

}

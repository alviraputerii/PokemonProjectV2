package com.blibli.future.steps;

import com.blibli.future.common.CommonAction;
import com.blibli.future.constant.ParamConstant;
import com.blibli.future.data.*;
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
import java.io.File;
import java.util.ArrayList;
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
    boolean isPokemonDbDataExist;
    PokemonData bulbapediaPokemon, pokemonDbPokemon, pokeapiPokemon, pokedexPokemon;
    SoftAssert softAssert = new SoftAssert();
    List<String> pokemons = new ArrayList<>();

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

    @When("at pokedex app home page search for {string}")
    public void atAppHomePageSearchForPokemon(String value) {
        pokedexHomePage.searchPokemon(value);
    }

    //---------------------------- Check Response Code
    @Then("api response code should be {int}")
    public void apiResponseCodeShouldBe(int code) {
        softAssert.assertTrue(response.getStatusCode() == code, "Wrong response code");
    }

    //---------------------------- Get Data
    @Then("at bulbapedia pokemon page get following {string} data")
    public void atBulbapediaPokemonPageGetFollowingData(String pokemon, List<String> data) {
        bulbapediaPokemon = new PokemonData();
        try {
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        bulbapediaPokemon.setName(bulbapediaPokemonPage.getPokemonName());
                        break;
                    case "number":
                        bulbapediaPokemon.setNumber(bulbapediaPokemonPage.getPokemonNumber());
                        break;
                    case "types":
                        bulbapediaPokemon.setType(bulbapediaPokemonPage.getPokemonTypes());
                        break;
                    default:
                        bulbapediaPokemon.setBaseStats(bulbapediaPokemonPage.getPokemonStats(pokemon));
                }
            }
            PokemonListData.putParentListData(bulbapediaPokemon, ParamConstant.bulbapediaData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        }
    }

    @Then("at pokemondb pokemon page get following data")
    public void atPokemondbPokemonPageGetFollowingData(List<String> data) {
        Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        if (isPokemonDbDataExist) {
            pokemonDbPokemon = new PokemonData();
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        pokemonDbPokemon.setName(pokemonDbPokemonPage.getPokemonName());
                        break;
                    case "number":
                        pokemonDbPokemon.setNumber(pokemonDbPokemonPage.getPokemonNumber());
                        break;
                    case "types":
                        pokemonDbPokemon.setType(pokemonDbPokemonPage.getPokemonTypes());
                        break;
                    default:
                        pokemonDbPokemon.setBaseStats(pokemonDbPokemonPage.getPokemonStats());
                }
            }
            PokemonListData.putParentListData(pokemonDbPokemon, ParamConstant.pokemonDbData);
        }
    }

    @Then("get following data from response")
    public void getFollowingDataFromResponse(List<String> data) {
        try {
            getPokemonApiResponse = response.getBody().as(GetPokemonApiResponse.class);
            pokeapiPokemon = new PokemonData();
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        pokeapiPokemon.setName(getPokemonApiResponse.getName());
                        break;
                    case "number":
                        pokeapiPokemon.setNumber(getPokemonApiResponse.getId().intValue());
                        break;
                    case "types":
                        List<String> pokeApiType = getPokemonApiResponse.getTypes().stream().map(ty -> ty.getType().getName()).collect(Collectors.toList());
                        pokeapiPokemon.setType(pokeApiType);
                        break;
                    default:
                        List<Integer> pokeApiStats = getPokemonApiResponse.getStats().stream().map(st -> st.getBase_stat().intValue()).collect(Collectors.toList());
                        Map<String, Integer> pokemonStats = new HashMap<>();
                        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
                            pokemonStats.put(ParamConstant.baseStatsName.get(i), pokeApiStats.get(i));
                        }
                        pokeapiPokemon.setBaseStats(pokemonStats);
                }
            }
            PokemonListData.putParentListData(pokeapiPokemon, ParamConstant.pokeApiData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("at pokedex app pokemon page get following data")
    public void atPokedexAppPokemonPageGetFollowingData(List<String> data) {
        pokedexPokemon = new PokemonData();
        for (String dt : data) {
            switch (dt) {
                case "name":
                    pokedexPokemon.setName(pokedexPokemonPage.getPokemonName());
                    break;
                case "number":
                    pokedexPokemon.setNumber(pokedexPokemonPage.getPokemonNumber());
                    break;
                default:
                    pokedexPokemon.setBaseStats(pokedexPokemonPage.getPokemonStats());
            }
        }
        PokemonListData.putParentListData(pokedexPokemon, ParamConstant.pokedexAppData);
        Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        getAndroidDriver().resetApp();
    }

    //---------------------------- Compare Data
    @Given("prepare pokemon parameter for following pokemon")
    public void preparePokemonParameterForFollowingPokemon(List<String> data) {
        pokemons.addAll(data);
    }

    @Then("compare all pokemon data")
    public void compareAllPokemonData() {
        File[] listOfFiles = getJsonFile();
        List<Map<String, Object>> pokemonData;

        for (String pokemon : pokemons) {
            for (File listOfFile : listOfFiles) {
                String fileName = listOfFile.getName();
                pokemonData = readJsonFile(fileName);
                for (Map<String, Object> pokemonDatum : pokemonData) {
                    try {
                        if (pokemonDatum.containsValue(pokemon)) {
                            if (fileName.contains(ParamConstant.bulbapediaData))
                                bulbapediaPokemon = convertClass(pokemonDatum);
                            else if (fileName.contains(ParamConstant.pokemonDbData))
                                pokemonDbPokemon = convertClass(pokemonDatum);
                            else if (fileName.contains(ParamConstant.pokeApiData))
                                pokeapiPokemon = convertClass(pokemonDatum);
                            else
                                pokedexPokemon = convertClass(pokemonDatum);
                        }
                    } catch (Exception e) {
                        if (fileName.contains(ParamConstant.bulbapediaData))
                            bulbapediaPokemon = null;
                        else if (fileName.contains(ParamConstant.pokemonDbData))
                            pokemonDbPokemon = null;
                        else if (fileName.contains(ParamConstant.pokeApiData))
                            pokeapiPokemon = null;
                        else
                            pokedexPokemon = null;
                    }
                }
            }

            boolean checkName = bulbapediaPokemon.getName().equalsIgnoreCase(pokemonDbPokemon.getName()) &&
                    bulbapediaPokemon.getName().equalsIgnoreCase(pokeapiPokemon.getName()) &&
                    bulbapediaPokemon.getName().equalsIgnoreCase(pokedexPokemon.getName());
            softAssert.assertTrue(checkName, "Pokemon name doesn't match");

            boolean checkNumber = bulbapediaPokemon.getNumber() == pokemonDbPokemon.getNumber() &&
                    bulbapediaPokemon.getNumber() == pokeapiPokemon.getNumber() &&
                    bulbapediaPokemon.getNumber() == pokedexPokemon.getNumber();
            softAssert.assertTrue(checkNumber, "Pokemon number doesn't match");

            boolean checkType = bulbapediaPokemon.getType().equals(pokemonDbPokemon.getType()) &&
                    bulbapediaPokemon.getType().equals(pokeapiPokemon.getType());
            softAssert.assertTrue(checkType, "Pokemon type doesn't match");

            boolean checkStats = bulbapediaPokemon.getBaseStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokemonDbPokemon.getBaseStats().get(e.getKey()))) &&
                    bulbapediaPokemon.getBaseStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokeapiPokemon.getBaseStats().get(e.getKey()))) &&
                    bulbapediaPokemon.getBaseStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokedexPokemon.getBaseStats().get(e.getKey())));
            softAssert.assertTrue(checkStats, "Pokemon base stat doesn't match");
        }
        softAssert.assertAll();
    }
}

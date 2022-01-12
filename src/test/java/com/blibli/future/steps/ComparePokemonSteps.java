package com.blibli.future.steps;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.data.PokemonData;
import com.blibli.future.data.PokemonListData;
import com.blibli.future.pages.*;
import com.blibli.future.response.GetPokemonApiResponse;
import com.blibli.future.service.PokeApiController;
import com.blibli.future.utils.Utility;
import com.blibli.future.utils.VideoRecorder_utlity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComparePokemonSteps extends Utility {
    BulbapediaHomePage bulbapediaHomePage;
    BulbapediaPokemonPage bulbapediaPokemonPage;
    PokemonDbHomePage pokemonDbHomePage;
    PokemonDbPokemonPage pokemonDbPokemonPage;
    PokedexHomePage pokedexHomePage;
    PokedexPokemonPage pokedexPokemonPage;
    GetPokemonApiResponse getPokemonApiResponse;
    Response response;
    boolean isBulbapediaDataExist, isPokemonDbDataExist, isPokeApiDataExist, isPokedexDataExist;
    PokemonData bulbapediaPokemon, pokemonDbPokemon, pokeapiPokemon, pokedexPokemon;
    SoftAssert softAssert = new SoftAssert();
    List<String> pokemons = new ArrayList<>();

    //---------------------------- Open URL
    @Given("open bulbapedia home page")
    public void openBulbapediaHomePage() {
        switchTabs();
        bulbapediaHomePage.openBulbapediaHomePage();
    }

    @Given("open pokemondb home page")
    public void openPokemondbHomePage() {
        pokemonDbHomePage.openPokemonDbHomePage();
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
            e.printStackTrace();
        }
    }

    @When("send api request for {string}")
    public void sendApiRequestForPokemonPokemon(String pokemon) {
        PokeApiController pokeApiController = new PokeApiController();
        response = pokeApiController.getPokemon(getWebsiteUrl("urlPokeApi"), pokemon.toLowerCase());
    }

    @When("at pokedex app home page search for {string} exist is {string}")
    public void atAppHomePageSearchForPokemon(String pokemon,String value) {
        try {
            pokedexHomePage.searchPokemon(pokemon);
            isPokedexDataExist = true;
        } catch (Exception e) {
            isPokedexDataExist = false;
            e.printStackTrace();
            Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        }
        softAssert.assertEquals(isPokedexDataExist, Boolean.parseBoolean(value),"Pokemon " + pokemon + " exist in Pokedex App is not " + value);
    }

    //---------------------------- Check Response Code
    @Then("api response code should be {int}")
    public void apiResponseCodeShouldBe(int code) {
        softAssert.assertTrue(response.getStatusCode() == code, "wrong response status code");
    }

    //---------------------------- Get Data
    @Then("at bulbapedia pokemon page get following data")
    public void atBulbapediaPokemonPageGetFollowingData(List<String> data) {
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
                    case "baseStats":
                        bulbapediaPokemon.setBaseStats(bulbapediaPokemonPage.getPokemonStats());
                        break;
                    case "baseExperience":
                        bulbapediaPokemon.setBaseExperience(bulbapediaPokemonPage.getPokemonBaseExperience());
                        break;
                    case "species":
                        bulbapediaPokemon.setSpecies(bulbapediaPokemonPage.getPokemonSpecies());
                        break;
                    default:
                        bulbapediaPokemon.setGrowthRate(bulbapediaPokemonPage.getPokemonGrowthRate());
                }
            }
            PokemonListData.putParentListData(bulbapediaPokemon, ParamConstant.bulbapediaData);
            isBulbapediaDataExist = true;
        } catch (Exception e) {
            isBulbapediaDataExist = false;
            e.printStackTrace();
        } finally {
            Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
        }
        softAssert.assertTrue(isBulbapediaDataExist, "Pokemon doesn't exist in Bulbapedia");
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
                    case "baseStats":
                        pokemonDbPokemon.setBaseStats(pokemonDbPokemonPage.getPokemonStats());
                        break;
                    case "baseExperience":
                        pokemonDbPokemon.setBaseExperience(pokemonDbPokemonPage.getPokemonBaseExperience());
                        break;
                    case "species":
                        pokemonDbPokemon.setSpecies(pokemonDbPokemonPage.getPokemonSpecies());
                        break;
                    default:
                        pokemonDbPokemon.setGrowthRate(pokemonDbPokemonPage.getPokemonGrowthRate());
                }
            }
            PokemonListData.putParentListData(pokemonDbPokemon, ParamConstant.pokemonDbData);
        }else{
            softAssert.assertTrue(isPokemonDbDataExist, "Pokemon doesn't exist in PokemonDb");
        }
        softAssert.assertAll();
    }

    @Then("get following data from response")
    public void getFollowingDataFromResponse(List<String> data) {
        try {
            getPokemonApiResponse = response.getBody().as(GetPokemonApiResponse.class);
            pokeapiPokemon = new PokemonData();
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        pokeapiPokemon.setName(getPokemonApiResponse.getName().toLowerCase());
                        break;
                    case "number":
                        pokeapiPokemon.setNumber(getPokemonApiResponse.getId().intValue());
                        break;
                    case "types":
                        List<String> pokeApiType = getPokemonApiResponse.getTypes().stream().map(ty -> ty.getType().getName()).collect(Collectors.toList());
                        pokeapiPokemon.setType(pokeApiType);
                        break;
                    case "baseStats":
                        List<Integer> pokeApiStats = getPokemonApiResponse.getStats().stream().map(st -> st.getBase_stat().intValue()).collect(Collectors.toList());
                        Map<String, Integer> pokemonStats = new HashMap<>();
                        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
                            pokemonStats.put(ParamConstant.baseStatsName.get(i), pokeApiStats.get(i));
                        }
                        pokeapiPokemon.setBaseStats(pokemonStats);
                    default:
                        pokeapiPokemon.setBaseExperience(getPokemonApiResponse.getBase_experience().intValue());
                }
            }
            PokemonListData.putParentListData(pokeapiPokemon, ParamConstant.pokeApiData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        softAssert.assertAll();
    }

    @Then("at pokedex app pokemon page get following data")
    public void atPokedexAppPokemonPageGetFollowingData(List<String> data){
        if (isPokedexDataExist) {
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
        softAssert.assertAll();
    }

    @Then("at bulbapedia home page pokemon should not be found")
    public void atBulbapediaHomePagePokemonShouldNotBeFound() {
        softAssert.assertEquals(bulbapediaHomePage.getSearchResultText(),"There were no results matching the query.","Pokemon found");
        Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
    }

    @Then("at pokemondb home page pokemon should not be found")
    public void atPokemondbHomePagePokemonShouldNotBeFound() {
        softAssert.assertEquals(pokemonDbHomePage.getSearchResultText(),"No Results","Pokemon found");
        Allure.addAttachment("Page Screenshot", new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
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
                for (Map<String, Object> data : pokemonData) {
                    if (data.containsValue(pokemon)) {
                        if (fileName.contains(ParamConstant.bulbapediaData)) {
                            bulbapediaPokemon = convertClass(data);
                            isBulbapediaDataExist = true;
                        } else if (fileName.contains(ParamConstant.pokemonDbData)) {
                            pokemonDbPokemon = convertClass(data);
                            isPokemonDbDataExist = true;
                        } else if (fileName.contains(ParamConstant.pokeApiData)) {
                            pokeapiPokemon = convertClass(data);
                            isPokeApiDataExist = true;
                        } else {
                            pokedexPokemon = convertClass(data);
                            isPokedexDataExist = true;
                        }
                        break;
                    } else {
                        if (fileName.contains(ParamConstant.bulbapediaData))
                            isBulbapediaDataExist = false;
                        else if (fileName.contains(ParamConstant.pokemonDbData))
                            isPokemonDbDataExist = false;
                        else if (fileName.contains(ParamConstant.pokeApiData))
                            isPokeApiDataExist = false;
                        else
                            isPokedexDataExist = false;
                    }
                }
            }
            if (isBulbapediaDataExist && isPokemonDbDataExist && isPokeApiDataExist && isPokedexDataExist) {
                boolean checkName = bulbapediaPokemon.getName().equalsIgnoreCase(pokemonDbPokemon.getName()) &&
                        bulbapediaPokemon.getName().equalsIgnoreCase(pokeapiPokemon.getName()) &&
                        bulbapediaPokemon.getName().equalsIgnoreCase(pokedexPokemon.getName());
                softAssert.assertTrue(checkName, "Pokemon name for pokemon " + pokemon + " doesn't match");

                boolean checkNumber = bulbapediaPokemon.getNumber() == pokemonDbPokemon.getNumber() &&
                        bulbapediaPokemon.getNumber() == pokeapiPokemon.getNumber() &&
                        bulbapediaPokemon.getNumber() == pokedexPokemon.getNumber();
                softAssert.assertTrue(checkNumber, "Pokemon number for pokemon " + pokemon + " doesn't match");

                boolean checkType = bulbapediaPokemon.getType().equals(pokemonDbPokemon.getType()) &&
                        bulbapediaPokemon.getType().equals(pokeapiPokemon.getType());
                softAssert.assertTrue(checkType, "Pokemon type for pokemon " + pokemon + " doesn't match");

                boolean checkStats = bulbapediaPokemon.getBaseStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokemonDbPokemon.getBaseStats().get(e.getKey()))) &&
                        bulbapediaPokemon.getBaseStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokeapiPokemon.getBaseStats().get(e.getKey()))) &&
                        bulbapediaPokemon.getBaseStats().entrySet().stream().allMatch(e -> e.getValue().equals(pokedexPokemon.getBaseStats().get(e.getKey())));
                softAssert.assertTrue(checkStats, "Pokemon base stat for pokemon " + pokemon + " doesn't match");

                boolean checkExperience = bulbapediaPokemon.getBaseExperience() == pokemonDbPokemon.getBaseExperience() &&
                        bulbapediaPokemon.getBaseExperience() == pokeapiPokemon.getBaseExperience();
                softAssert.assertTrue(checkExperience, "Pokemon base experience for pokemon " + pokemon + " doesn't match");

                boolean checkSpecies = bulbapediaPokemon.getSpecies().equals(pokemonDbPokemon.getSpecies());
                softAssert.assertTrue(checkSpecies, "Pokemon species for pokemon " + pokemon + " doesn't match");

                boolean checkGrowthRate = bulbapediaPokemon.getGrowthRate().equals(pokemonDbPokemon.getGrowthRate());
                softAssert.assertTrue(checkGrowthRate, "Pokemon growth rate for pokemon " + pokemon + " doesn't match");
            } else {
                softAssert.assertTrue(isBulbapediaDataExist, "Comparison failed because pokemon " + pokemon + " doesn't exist in Bulbapedia");
                softAssert.assertTrue(isPokemonDbDataExist, "Comparison failed because pokemon " + pokemon + " doesn't exist in PokemonDb");
                softAssert.assertTrue(isPokeApiDataExist, "Comparison failed because pokemon " + pokemon + " doesn't exist in PokeApi");
                softAssert.assertTrue(isPokedexDataExist, "Comparison failed because pokemon " + pokemon + " doesn't exist in Pokedex");
            }
        }
        softAssert.assertAll();
    }

    //---------------------------- Recording Steps
    @Given("prepare start recording for pokemon {string}")
    public void prepareStartRecording(String pokemon) throws Exception {
        startRecord(pokemon);
    }

    @Then("stop and save recording for pokemon {string}")
    public void stopAndSaveRecording(String pokemon) throws Exception {
        stopRecord();
        VideoRecorder_utlity.convertVideo(pokemon);
        byte[] byteArr = IOUtils.toByteArray(new FileInputStream("./target/automationrecordings/" + pokemon + ".mp4"));
        Allure.addAttachment("video", "video/mp4", new ByteArrayInputStream(byteArr), "mp4");
    }
}

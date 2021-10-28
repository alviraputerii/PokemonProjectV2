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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    PokemonData bulbapediaPokemon = new PokemonData();
    PokemonData pokemonDbPokemon = new PokemonData();
    PokemonData pokeapiPokemon = new PokemonData();
    PokemonData pokedexPokemon = new PokemonData();
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
//        bulbapediaHomePage.searchPokemon(PokemonParameter.getParameter());
    }

    @When("at pokemondb home page search for {string}")
    public void atPokemondbHomePageSearchForPokemonPokemon(String pokemon) {
        try {
            pokemonDbHomePage.searchPokemon(pokemon);
//            pokemonDbHomePage.searchPokemon(PokemonParameter.getParameter());
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
//        response = pokeApiController.getPokemon(getWebsiteUrl("urlPokeApi"), PokemonParameter.getParameter().toLowerCase());
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
        try {
            BulbapediaListData.createParentListData(Thread.currentThread().getId());
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        BulbapediaListData.putParentListData(ParamConstant.name, bulbapediaPokemonPage.getPokemonName(), Thread.currentThread().getId());
                        break;
                    case "number":
                        BulbapediaListData.putParentListData(ParamConstant.number, bulbapediaPokemonPage.getPokemonNumber(), Thread.currentThread().getId());
                        break;
                    case "types":
                        BulbapediaListData.putParentListData(ParamConstant.type, bulbapediaPokemonPage.getPokemonTypes(), Thread.currentThread().getId());
                        break;
                    default:
                        BulbapediaListData.putParentListData(ParamConstant.baseStats, bulbapediaPokemonPage.getPokemonStats(pokemon), Thread.currentThread().getId());
//            BulbapediaListData.putParentListData(ParamConstant.baseStats, bulbapediaPokemonPage.getPokemonStats(PokemonParameter.getParameter()), Thread.currentThread().getId());
                }
            }
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
            PokemonDbListData.createParentListData(Thread.currentThread().getId());
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        PokemonDbListData.putParentListData(ParamConstant.name, pokemonDbPokemonPage.getPokemonName(), Thread.currentThread().getId());
                        break;
                    case "number":
                        PokemonDbListData.putParentListData(ParamConstant.number, pokemonDbPokemonPage.getPokemonNumber(), Thread.currentThread().getId());
                        break;
                    case "types":
                        PokemonDbListData.putParentListData(ParamConstant.type, pokemonDbPokemonPage.getPokemonTypes(), Thread.currentThread().getId());
                        break;
                    default:
                        PokemonDbListData.putParentListData(ParamConstant.baseStats, pokemonDbPokemonPage.getPokemonStats(), Thread.currentThread().getId());
                }
            }
        }
    }

    @Then("get following data from response")
    public void getFollowingDataFromResponse(List<String> data) {
        try {
            getPokemonApiResponse = response.getBody().as(GetPokemonApiResponse.class);
            PokeApiListData.createParentListData(Thread.currentThread().getId());
            for (String dt : data) {
                switch (dt) {
                    case "name":
                        PokeApiListData.putParentListData(ParamConstant.name, getPokemonApiResponse.getName(), Thread.currentThread().getId());
                        break;
                    case "number":
                        PokeApiListData.putParentListData(ParamConstant.number, getPokemonApiResponse.getId(), Thread.currentThread().getId());
                        break;
                    case "types":
                        List<String> pokeApiType = getPokemonApiResponse.getTypes().stream().map(ty -> ty.getType().getName()).collect(Collectors.toList());
                        PokeApiListData.putParentListData(ParamConstant.type, pokeApiType, Thread.currentThread().getId());
                        break;
                    default:
                        List<Integer> pokeApiStats = getPokemonApiResponse.getStats().stream().map(st -> st.getBase_stat().intValue()).collect(Collectors.toList());
                        Map<String, Integer> pokemonStats = new HashMap<>();
                        for (int i = 0; i < ParamConstant.baseStatsName.size(); i++) {
                            pokemonStats.put(ParamConstant.baseStatsName.get(i), pokeApiStats.get(i));
                        }
                        PokeApiListData.putParentListData(ParamConstant.baseStats, pokemonStats, Thread.currentThread().getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("at pokedex app pokemon page get following data")
    public void atPokedexAppPokemonPageGetFollowingData(List<String> data) {
        PokedexListData.createParentListData(Thread.currentThread().getId());
        for (String dt : data) {
            switch (dt) {
                case "name":
                    PokedexListData.putParentListData(ParamConstant.name, pokedexPokemonPage.getPokemonName(), Thread.currentThread().getId());
                    break;
                case "number":
                    PokedexListData.putParentListData(ParamConstant.number, pokedexPokemonPage.getPokemonNumber(), Thread.currentThread().getId());
                    break;
                default:
                    PokedexListData.putParentListData(ParamConstant.baseStats, pokedexPokemonPage.getPokemonStats(), Thread.currentThread().getId());
            }
        }
    }

    //---------------------------- Compare Data
    @Given("prepare pokemon parameter for following pokemon")
    public void preparePokemonParameterForFollowingPokemon(List<String> data) {
        pokemons.addAll(data);
    }

    @When("save pokemon data to json")
    public void saveData() throws InterruptedException {
        boolean complete = false;
        while (!complete) {
            Thread.sleep(2000);
            System.out.println("test :(");
            if (PokemonDbListData.getParentListData().size() == pokemons.size())
                complete = true;
        }
        System.out.println("test :)");

//        String[] sources = {ParamConstant.bulbapediaData, ParamConstant.pokemonDbData, ParamConstant.pokeApiData, ParamConstant.pokedexAppData};
//        String[] sources = {ParamConstant.pokedexAppData};
        String[] sources = {ParamConstant.bulbapediaData, ParamConstant.pokemonDbData, ParamConstant.pokeApiData};
        List<PokemonData> currData;
        for (String source : sources) {
            if (source.equals(ParamConstant.bulbapediaData))
                currData = BulbapediaListData.getParentListData();
            else if (source.equals(ParamConstant.pokemonDbData))
                currData = PokemonDbListData.getParentListData();
            else if (source.equals(ParamConstant.pokeApiData))
                currData = PokeApiListData.getParentListData();
            else
                currData = PokedexListData.getParentListData();
            for (int i = 0; i < currData.size(); i++) {
                PokemonJsonData.putPokemonData(ParamConstant.name, currData.get(i).getName());
                PokemonJsonData.putPokemonData(ParamConstant.number, currData.get(i).getNumber());
                PokemonJsonData.putPokemonData(ParamConstant.type, currData.get(i).getType());
                PokemonJsonData.putPokemonData(ParamConstant.baseStats, currData.get(i).getBaseStats());
                setPokemonData(PokemonJsonData.getPokemonData());
            }
            writeJsonFile(source);
        }
    }

    @Then("compare all pokemon data")
    public void compareAllPokemonData() {
        File[] listOfFiles = getJsonFile();
        List<Map<String, Object>> pokemonData;

        for (String dt : pokemons) {
            for (int i = 0; i < listOfFiles.length; i++) {
                String fileName = listOfFiles[i].getName();
                pokemonData = readJsonFile(fileName);
                for (int x = 0; x < pokemonData.size(); x++) {
                    try {
                        if (pokemonData.get(x).containsValue(dt)) {
                            if (fileName.contains(ParamConstant.bulbapediaData))
                                bulbapediaPokemon = convertClass(pokemonData.get(x));
                            else if (fileName.contains(ParamConstant.pokemonDbData))
                                pokemonDbPokemon = convertClass(pokemonData.get(x));
                            else if (fileName.contains(ParamConstant.pokeApiData))
                                pokeapiPokemon = convertClass(pokemonData.get(x));
                            else
                                pokedexPokemon = convertClass(pokemonData.get(x));
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

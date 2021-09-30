package com.blibli.future.steps;

import com.blibli.future.common.CommonAction;
import com.blibli.future.data.PokemonData;
import com.blibli.future.pages.BulbapediaHomePage;
import com.blibli.future.pages.BulbapediaPokemonPage;
import com.blibli.future.pages.PokemonDbHomePage;
import com.blibli.future.pages.PokemonDbPokemonPage;
import com.blibli.future.response.GetPokemonApiResponse;
import com.blibli.future.service.PokeApiController;
import com.blibli.future.utils.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ComparePokemonSteps extends Utility {

    CommonAction commonAction;
    BulbapediaHomePage bulbapediaHomePage;
    BulbapediaPokemonPage bulbapediaPokemonPage;
    PokemonDbHomePage pokemonDbHomePage;
    PokemonDbPokemonPage pokemonDbPokemonPage;
    GetPokemonApiResponse getPokemonApiResponse;
    Response response;
    PokemonData bulbapediaPokemon = new PokemonData();
    PokemonData pokemonDbPokemon = new PokemonData();
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
        bulbapediaHomePage.inputKeyword(pokemon);
    }

    @When("at pokemondb home page search for {string}")
    public void atPokemondbHomePageSearchForPokemonPokemon(String pokemon) {
        pokemonDbHomePage.inputKeyword(pokemon);
    }

    @When("send api request for {string}")
    public void sendApiRequestForPokemonPokemon(String pokemon) {
        PokeApiController pokeApiController = new PokeApiController();
        response = pokeApiController.getPokemon(getWebsiteUrl("urlPokeApi"), pokemon.toLowerCase());
    }

    //---------------------------- Check Response Code
    @Then("api response code should be {int}")
    public void apiResponseCodeShouldBe(int code) {
        softAssert.assertEquals(response.getStatusCode(), equalTo(code),"Wrong response code");
    }

    //---------------------------- Get Data
    @Then("at bulbapedia pokemon page get following {string} data")
    public void atBulbapediaPokemonPageGetFollowingData(String pokemon, List<String> data) {
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
    }

    @Then("at pokemondb pokemon page get following data")
    public void atPokemondbPokemonPageGetFollowingData(List<String> data) {
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

    @Then("convert the response into model class")
    public void convertTheResponseIntoModelClass() {
        getPokemonApiResponse = response.getBody().as(GetPokemonApiResponse.class);
        System.out.println("Pokemon Name : " + getPokemonApiResponse.getName());
        System.out.println("Pokemon Number : " + getPokemonApiResponse.getId().intValue());
    }

    //---------------------------- Compare Data
    @Then("compare pokemon name from three websites")
    public void comparePokemonNameFromThreeWebsites() {
        boolean checkName = bulbapediaPokemon.getName().equalsIgnoreCase(pokemonDbPokemon.getName()) &&
                bulbapediaPokemon.getName().equalsIgnoreCase(getPokemonApiResponse.getName());
        softAssert.assertTrue(checkName, "Pokemon name doesn't match");
    }

    @Then("compare pokemon number from three websites")
    public void comparePokemonNumberFromThreeWebsites() {
        boolean checkNumber = bulbapediaPokemon.getNumber() == pokemonDbPokemon.getNumber() &&
                bulbapediaPokemon.getNumber() == getPokemonApiResponse.getId().intValue();
        softAssert.assertTrue(checkNumber, "Pokemon number doesn't match");
    }

    @Then("compare pokemon type from three websites")
    public void comparePokemonTypeFromThreeWebsites() {
        List<String> pokeApiType = getPokemonApiResponse.getTypes().stream().map(ty -> ty.getType().getName()).collect(Collectors.toList());
        boolean checkType = bulbapediaPokemon.getType().equals(pokemonDbPokemon.getType()) &&
                bulbapediaPokemon.getType().equals(pokeApiType);
        softAssert.assertTrue(checkType, "Pokemon type doesn't match");
    }

    @Then("compare pokemon baseStats from three websites")
    public void comparePokemonBaseStatsFromThreeWebsites() {
        List<Integer> pokeApiStats = getPokemonApiResponse.getStats().stream().map(st -> st.getBase_stat().intValue()).collect(Collectors.toList());
        boolean checkStats = bulbapediaPokemon.getSpeciesStats().equals(pokemonDbPokemon.getSpeciesStats()) &&
                bulbapediaPokemon.getSpeciesStats().equals(pokeApiStats);
        softAssert.assertTrue(checkStats, "Pokemon base stat doesn't match");
    }

    //---------------------------- Additional Steps
    @When("at pokemondb result page click pokemon pokedex")
    public void atPokemondbResultPageClickPokemonPokedex() {
        pokemonDbHomePage.clickPokemonPokedex();
    }

}

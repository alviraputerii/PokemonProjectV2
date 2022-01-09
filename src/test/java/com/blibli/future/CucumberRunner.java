package com.blibli.future;

import com.blibli.future.constant.ParamConstant;
import com.blibli.future.data.PokemonData;
import com.blibli.future.data.PokemonJsonData;
import com.blibli.future.data.PokemonListData;
import com.blibli.future.utils.Utility;
import com.blibli.future.utils.VideoRecorder_utlity;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.List;

@CucumberOptions(features = "src/test/resources/features/",
        plugin = {"pretty", "html:target/cucuber-html-reports",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "json:target/destination/cucumber.json"},
        glue = {"com.blibli.future.steps"},
        tags = "")

public class CucumberRunner extends AbstractTestNGCucumberTests{
    Utility utility = new Utility();

    @BeforeClass
    public void beforeClass() throws Exception {
        VideoRecorder_utlity.startRecord("WebsiteTestRecording");
    }

    @AfterClass
    public void afterClass() throws Exception {
        String[] sources = {ParamConstant.bulbapediaData, ParamConstant.pokemonDbData, ParamConstant.pokeApiData, ParamConstant.pokedexAppData};
        List<PokemonData> currData;
        for (String source : sources) {
            switch (source) {
                case ParamConstant.bulbapediaData:
                    currData = PokemonListData.getParentListData(ParamConstant.bulbapediaData);
                    break;
                case ParamConstant.pokemonDbData:
                    currData = PokemonListData.getParentListData(ParamConstant.pokemonDbData);
                    break;
                case ParamConstant.pokeApiData:
                    currData = PokemonListData.getParentListData(ParamConstant.pokeApiData);
                    break;
                default:
                    currData = PokemonListData.getParentListData(ParamConstant.pokedexAppData);
                    break;
            }
            if(currData.size()!=0) {
                for (PokemonData dt : currData) {
                    PokemonJsonData.putPokemonData(ParamConstant.name, dt.getName());
                    PokemonJsonData.putPokemonData(ParamConstant.number, dt.getNumber());
                    PokemonJsonData.putPokemonData(ParamConstant.type, dt.getType());
                    PokemonJsonData.putPokemonData(ParamConstant.baseStats, dt.getBaseStats());
                    PokemonJsonData.putPokemonData(ParamConstant.baseExperience, dt.getBaseExperience());
                    PokemonJsonData.putPokemonData(ParamConstant.species, dt.getSpecies());
                    PokemonJsonData.putPokemonData(ParamConstant.growthRate, dt.getGrowthRate());
                    utility.setPokemonData(PokemonJsonData.getPokemonData());
                }
                utility.writeJsonFile(source);
            }
            currData.clear();
            VideoRecorder_utlity.stopRecord();
        }
    }
}
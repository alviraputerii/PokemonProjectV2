package com.blibli.future;

import com.blibli.future.constant.ParamConstant;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;

@CucumberOptions(features = "src/test/resources/features/",
        plugin = {"pretty", "html:target/cucumber-html-reports",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "json:target/destination/cucumber.json"},
        glue = {"com.blibli.future.steps"},
        tags = "")

public class CucumberRunner extends AbstractTestNGCucumberTests {
//    public Utility utility = new Utility();

//    @DataProvider(name = "data-provider")
//    public Object[][] data() {
//        String[] data = utility.getPokemon().split(",");
//        Object[][] pokemon = new Object[data.length][];
//        int i = 0;
//        for (String row : data)
//            pokemon[i++] = row.split(",");
//        return pokemon;
//    }

//    @BeforeMethod
//    @Factory(dataProvider="data-provider")
//    public void createInstances() {
//        PokemonParameter.setParameter("Pikachu");
//    }

//    @BeforeClass
//    public void createJsonFile() throws IOException {
//        String[] files = {ParamConstant.bulbapediaData, ParamConstant.pokemonDbData, ParamConstant.pokeApiData, ParamConstant.pokedexAppData};
//        for (String fileName : files) {
//            String path = System.getProperty("user.dir") + "\\target\\jsonData\\" + fileName + ".json";
//            File file = new File(path);
//            FileUtils.writeStringToFile(file, "", "UTF-8");
//        }
//    }
}
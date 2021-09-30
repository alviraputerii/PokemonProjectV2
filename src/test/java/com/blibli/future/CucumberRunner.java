package com.blibli.future;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;
import org.testng.annotations.DataProvider;

//@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/",
        plugin = {"json:target/destination/cucumber.json"},
        glue = {"com.blibli.future.steps"},
//        stepNotifications = true,
        tags = "@PokemonFeature")

public class CucumberRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
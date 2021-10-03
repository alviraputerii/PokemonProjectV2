package com.blibli.future;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

//@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/",
        plugin = {"pretty", "html:target/cucumber-html-reports",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "json:target/destination/cucumber.json"},
        glue = {"com.blibli.future.steps"},
        tags = "@Mobile")

public class CucumberRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
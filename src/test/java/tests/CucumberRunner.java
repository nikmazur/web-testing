package tests;

import helpers.Methods;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@CucumberOptions(
        features = "src/test/resources/TheInternet.feature",
        glue = { "helpers", "pages.TheInternet" },
        plugin = { "html:bin/cucumberReport.html" })

public class CucumberRunner {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpCucumber() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "Cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void feature(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws IOException {
        testNGCucumberRunner.finish();
        // Automatically opens the generated Cucumber report in default browser
        Desktop.getDesktop().browse(URI.create("file://" +
                Methods.PROJ_PATH + Methods.S + "bin" + Methods.S + "cucumberReport.html"));
    }
}
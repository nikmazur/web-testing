package helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

import java.net.MalformedURLException;

public class Hooks {

    @Before
    public void beforeScenario() throws MalformedURLException {
        GeneralBrowser.setup();
    }

    @After
    public void afterScenario(Scenario scenario) {
        scenario.attach(Selenide.screenshot(OutputType.BYTES), "image/png", scenario.getName());
        WebDriverRunner.driver().close();
    }
}

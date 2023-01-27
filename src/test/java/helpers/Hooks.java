package helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

import java.net.MalformedURLException;
import java.util.Base64;

public class Hooks {

    @Before
    public void beforeScenario() throws MalformedURLException {
        GeneralBrowser.setup();
    }

    @After
    public void afterScenario(Scenario scenario) {
        byte[] screenshot = Base64.getDecoder().decode(Selenide.screenshot(OutputType.BASE64));
        scenario.attach(screenshot, "image/png", scenario.getName());

        WebDriverRunner.driver().close();
    }
}

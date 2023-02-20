package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class KeyPresses {

    private static final String content = "//div[@id='content']";
    private static final String header = content + "//h3";
    private static final String input = content + "//input[@id='target']";
    private static final String result = content + "//*[@id='result']";

    public KeyPresses() {
        $x(header).shouldHave(Condition.exactText("Key Presses"));
        $x(input).shouldBe(Condition.visible);
    }

    @Step("Press {keyCode} key")
    @When("I press {word} on the keyboard")
    public KeyPresses pressKey(String keyCode) {
        $x(input).sendKeys(Keys.valueOf(keyCode));
        return this;
    }

    @Step("Check {res} is displayed on the page")
    @Then("Page displays \"You entered: {word}\"")
    public KeyPresses checkResult(String res) {
        $x(result).shouldHave(Condition.exactText("You entered: " + res));
        return this;
    }
}

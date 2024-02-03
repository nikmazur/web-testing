package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class KeyPresses {

    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h3";
    final String INPUT = CONTENT + "//input[@id='target']";
    final String RESULT = CONTENT + "//*[@id='result']";

    public KeyPresses() {
        $x(HEADER).shouldHave(Condition.exactText("Key Presses"));
        $x(INPUT).shouldBe(Condition.visible);
    }

    @Step("Press {keyCode} key")
    @When("I press {word} on the keyboard")
    public KeyPresses pressKey(String keyCode) {
        $x(INPUT).sendKeys(Keys.valueOf(keyCode));
        return this;
    }

    @Step("Check {res} is displayed on the page")
    @Then("Page displays \"You entered: {word}\"")
    public KeyPresses checkResult(String res) {
        $x(RESULT).shouldHave(Condition.exactText("You entered: " + res));
        return this;
    }
}

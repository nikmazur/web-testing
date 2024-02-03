package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Dropdown {

    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h3";
    final String LIST = CONTENT + "//select[@id='dropdown']";
    final String SELECTED_OPTION = LIST + "//option[@selected='selected']";

    public Dropdown() {
        $x(HEADER).shouldHave(Condition.exactText("Dropdown List"));
        $x(LIST).shouldBe(Condition.visible);
    }

    @Step("Select option {0} from list")
    @When("I select Option {int}")
    public Dropdown selectOption(int index) {
        $x(LIST + "//option[@value='" + index + "']").click();
        return this;
    }

    @Step("Check dropdown text: \"{text}\"")
    @Then("Dropdown text is {string}")
    public Dropdown checkValue(String text) {
        $x(SELECTED_OPTION).shouldHave(Condition.exactText(text));
        return this;
    }

}

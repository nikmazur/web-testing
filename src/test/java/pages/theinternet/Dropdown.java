package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Dropdown {

    private static final String content = "//div[@id='content']";
    private static final String header = content + "//h3";
    private static final String list = content + "//select[@id='dropdown']";
    private static final String selectedOption = list + "//option[@selected='selected']";

    public Dropdown() {
        $x(header).shouldHave(Condition.exactText("Dropdown List"));
        $x(list).shouldBe(Condition.visible);
    }

    @Step("Select option {0} from list")
    @When("I select Option {int}")
    public Dropdown selectOption(int index) {
        $x(list + "//option[@value='" + index + "']").click();
        return this;
    }

    @Step("Check dropdown text: \"{text}\"")
    @Then("Dropdown text is {string}")
    public Dropdown checkValue(String text) {
        $x(selectedOption).shouldHave(Condition.exactText(text));
        return this;
    }

}

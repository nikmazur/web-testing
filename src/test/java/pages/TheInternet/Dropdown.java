package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Dropdown {

    private final String CONTENT = "//div[@id='content']";
    private final String HEADER = CONTENT + "//h3";
    private final String DROPDOWN = CONTENT + "//select[@id='dropdown']";
    private final String SELECTEDOPTION = DROPDOWN + "//option[@selected='selected']";

    public Dropdown() {
        $x(HEADER).shouldHave(Condition.exactText("Dropdown List"));
        $x(DROPDOWN).shouldBe(Condition.visible);
    }

    @Step("Select option {0} from list")
    @When("I select Option {int}")
    public Dropdown selectOption(int index) {
        $x(DROPDOWN + "//option[@value='" + index + "']").click();
        return this;
    }

    @Step("Check dropdown text: \"{text}\"")
    @Then("Dropdown text is {string}")
    public Dropdown checkValue(String text) {
        $x(SELECTEDOPTION).shouldHave(Condition.exactText(text));
        return this;
    }

}

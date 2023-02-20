package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Checkboxes {

    private static final String content = "//div[@id='content']";
    private static final String header = content + "//h3";
    private static final String form = content + "//form[@id='checkboxes']";
    private static final String input = form + "/input[";

    public Checkboxes() {
        $x(header).shouldHave(Condition.exactText("Checkboxes"));
        $x(form).shouldBe(Condition.visible);
    }

    @Step("Click on checkbox {index}")
    @When("I click on Checkbox {int}")
    public Checkboxes clickCheckbox(int index) {
        $x(input + index + "]").click();
        return this;
    }

    @Step("Check status of checkbox {index}")
    @Then("Checkbox {int} is {booleanValue}")
    public Checkboxes verifyCheckbox(int index, boolean checked) {
        if(checked)
            $x(input + index + "]").shouldBe(Condition.value("on"));
        else
            $x(input + index + "]").shouldNotBe(Condition.value("off"));
        return this;
    }

    @ParameterType(value = "checked|unchecked")
    public Boolean booleanValue(String value) {
        return value.equals("checked");
    }
}

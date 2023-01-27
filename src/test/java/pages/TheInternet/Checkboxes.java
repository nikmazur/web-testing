package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Checkboxes {

    private final String CONTENT = "//div[@id='content']";
    private final String HEADER = CONTENT + "//h3";
    private final String FORM = CONTENT + "//form[@id='checkboxes']";

    public Checkboxes() {
        $x(HEADER).shouldHave(Condition.exactText("Checkboxes"));
        $x(FORM).shouldBe(Condition.visible);
    }

    @Step("Click on checkbox {index}")
    @When("I click on Checkbox {int}")
    public Checkboxes clickCheckbox(int index) {
        $x(FORM + "/input[" + index + "]").click();
        return this;
    }

    @Step("Check status of checkbox {index}")
    @Then("Checkbox {int} is {booleanValue}")
    public Checkboxes verifyCheckbox(int index, boolean checked) {
        if(checked)
            $x(FORM + "/input[" + index + "]").shouldBe(Condition.value("on"));
        else
            $x(FORM + "/input[" + index + "]").shouldNotBe(Condition.value("off"));
        return this;
    }

    @ParameterType(value = "checked|unchecked")
    public Boolean booleanValue(String value) {
        return value.equals("checked");
    }
}

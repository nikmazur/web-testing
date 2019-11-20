package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Checkboxes {

    private String content = "//div[@id='content']";
    private String header = content + "//h3";
    private String form = content + "//form[@id='checkboxes']";

    public Checkboxes() {
        $x(header).shouldHave(Condition.exactText("Checkboxes"));
        $x(form).shouldBe(Condition.visible);
    }

    @Step("Click on checkbox {0}")
    public Checkboxes clickCheckbox(int index) {
        $x(form + "/input[" + index + "]").click();
        return this;
    }

    @Step("Check status of checkbox {0}")
    public Checkboxes verifyCheckbox(int index, boolean checked) {
        if(checked)
            $x(form + "/input[" + index + "]").shouldBe(Condition.value("on"));
        else
            $x(form + "/input[" + index + "]").shouldNotBe(Condition.value("off"));
        return this;
    }
}

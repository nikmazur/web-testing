package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Dropdown {

    private String content = "//div[@id='content']";
    private String header = content + "//h3";
    private String dropdown = content + "//select[@id='dropdown']";
    private String selectedOption = dropdown + "//option[@selected='selected']";

    public Dropdown() {
        $x(header).shouldHave(Condition.exactText("Dropdown List"));
        $x(dropdown).shouldBe(Condition.visible);
    }

    @Step("Select option {0} from list, check text \"{text}\"")
    public Dropdown selectOptionCheckValue(int index, String text) {
        $x(dropdown + "//option[@value='" + index + "']").click();
        $x(selectedOption).shouldHave(Condition.exactText(text));
        return this;
    }

}

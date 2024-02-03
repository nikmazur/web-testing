package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SimpleFormDemo extends PageLayout {
    final String INPUT_PANEL = "//div[text()='Single Input Field']/..";

    final String INPUT_FORM = INPUT_PANEL + "//input[@id='user-message']";
    final String SMOW_MSG_BUTTON = INPUT_PANEL + "//button[text()='Show Message']";
    final String INPUT_RESULT = INPUT_PANEL + "//div[@id='user-message']/*[@id='display']";

    public SimpleFormDemo() {
        $x(INPUT_PANEL).shouldBe(Condition.visible);
    }

    @Step("Input text, click Show Message, check result")
    public SimpleFormDemo inputTextCheckResult(String text) {
        $x(INPUT_FORM).sendKeys(text);
        $x(SMOW_MSG_BUTTON).click();
        $x(INPUT_RESULT).shouldHave(Condition.exactText(text));
        return this;
    }

    @Step("Clear input field")
    public SimpleFormDemo clearSingleInput() {
        $x(INPUT_FORM).clear();
        return this;
    }
}

package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SimpleFormDemo extends PageLayout {
    private static final String singleInputPanel = "//div[text()='Single Input Field']/..";

    private static final String inputForm = singleInputPanel + "//input[@id='user-message']";
    private static final String showMsgButton = singleInputPanel + "//button[text()='Show Message']";
    private static final String inputResult = singleInputPanel + "//div[@id='user-message']/*[@id='display']";

    public SimpleFormDemo() {
        $x(singleInputPanel).shouldBe(Condition.visible);
    }

    @Step("Input text, click Show Message, check result")
    public SimpleFormDemo inputTextCheckResult(String text) {
        $x(inputForm).sendKeys(text);
        $x(showMsgButton).click();
        $x(inputResult).shouldHave(Condition.exactText(text));
        return this;
    }

    @Step("Clear input field")
    public SimpleFormDemo clearSingleInput() {
        $x(inputForm).clear();
        return this;
    }
}

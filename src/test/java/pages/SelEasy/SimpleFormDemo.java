package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SimpleFormDemo extends PageLayout {
    private String singleInputPanel = "//div[text()='Single Input Field']/..";

    private String singleInputForm = singleInputPanel + "//input[@id='user-message']";
    private String showMessageButton = singleInputPanel + "//button[text()='Show Message']";
    private String singleInputResult = singleInputPanel + "//div[@id='user-message']/*[@id='display']";

    public SimpleFormDemo() {
        $x(singleInputPanel).shouldBe(Condition.visible);
    }

    @Step("Input text, click Show Message, check result")
    public SimpleFormDemo inputTextCheckResult(String text) {
        $x(singleInputForm).sendKeys(text);
        $x(showMessageButton).click();
        $x(singleInputResult).shouldHave(Condition.exactText(text));
        return this;
    }

    @Step("Clear input field")
    public SimpleFormDemo clearSingleInput() {
        $x(singleInputForm).clear();
        return this;
    }
}

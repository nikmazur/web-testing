package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class SimpleFormDemo extends PageLayout {
    private final String SINGLEINPUTPANEL = "//div[text()='Single Input Field']/..";

    private final String SINGLEINPUTFORM = SINGLEINPUTPANEL + "//input[@id='user-message']";
    private final String SHOWMESSAGEBUTTON = SINGLEINPUTPANEL + "//button[text()='Show Message']";
    private final String SINGLEINPUTRESULT = SINGLEINPUTPANEL + "//div[@id='user-message']/*[@id='display']";

    public SimpleFormDemo() {
        $x(SINGLEINPUTPANEL).shouldBe(Condition.visible);
    }

    @Step("Input text, click Show Message, check result")
    public SimpleFormDemo inputTextCheckResult(String text) {
        $x(SINGLEINPUTFORM).sendKeys(text);
        $x(SHOWMESSAGEBUTTON).click();
        $x(SINGLEINPUTRESULT).shouldHave(Condition.exactText(text));
        return this;
    }

    @Step("Clear input field")
    public SimpleFormDemo clearSingleInput() {
        $x(SINGLEINPUTFORM).clear();
        return this;
    }
}

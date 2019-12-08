package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class KeyPresses {

    private String content = "//div[@id='content']";
    private String header = content + "//h3";
    private String input = content + "//input[@id='target']";
    private String result = content + "//*[@id='result']";

    public KeyPresses() {
        $x(header).shouldHave(Condition.exactText("Key Presses"));
        $x(input).shouldBe(Condition.visible);
    }

    @Step("Press {res} key, check that it's name is displayed on the page")
    public KeyPresses pressKeyCheckResult(Keys keyCode, String res) {
        $x(input).sendKeys(keyCode);
        $x(result).shouldHave(Condition.exactText("You entered: " + res));
        return this;
    }
}

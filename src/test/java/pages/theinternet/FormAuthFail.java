package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FormAuthFail {

    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h2";
    final String FORM = CONTENT + "//form[@id='login']";
    final String MSG = "//*[@id='flash']";

    final String USERNAME = FORM + "//input[@id='username']";
    final String PASSWORD = FORM + "//input[@id='password']";

    final String SUBMIT = FORM + "//button[@type='submit']";

    public FormAuthFail() {
        $x(HEADER).shouldHave(Condition.exactText("Login Page"));
    }

    @Step("Check that not logged in")
    public FormAuthFail checkLoginFailed() {
        $x(MSG).shouldHave(Condition.text("Your username is invalid!"));
        $x(USERNAME).shouldBe(Condition.visible);
        $x(PASSWORD).shouldBe(Condition.visible);
        $x(SUBMIT).shouldBe(Condition.visible);
        return this;
    }


}

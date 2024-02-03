package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FormAuthSuccess {

    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h2";
    final String FORM = CONTENT + "//form[@id='login']";
    final String MSG = "//*[@id='flash']";

    final String USERNAME = FORM + "//input[@id='username']";
    final String PASSWORD = FORM + "//input[@id='password']";

    final String SUBMIT = FORM + "//button[@type='submit']";
    final String LOGOUT = CONTENT + "//*[@href='/logout']";

    public FormAuthSuccess() {
        $x(HEADER).shouldHave(Condition.exactText("Secure Area"));
    }

    @Step("Check that logged in successfully")
    public FormAuthSuccess checkLoggedIn() {
        $x(MSG).shouldHave(Condition.text("You logged into a secure area!"));
        $x(LOGOUT).shouldBe(Condition.visible);
        return this;
    }

    @Step("Press Logout")
    public FormAuthSuccess logout() {
        $x(LOGOUT).click();
        return this;
    }

    @Step("Check that logged out")
    public FormAuthSuccess checkLoggedOut() {
        $x(MSG).shouldHave(Condition.text("You logged out of the secure area!"));
        $x(USERNAME).shouldBe(Condition.visible);
        $x(PASSWORD).shouldBe(Condition.visible);
        $x(SUBMIT).shouldBe(Condition.visible);
        return this;
    }


}

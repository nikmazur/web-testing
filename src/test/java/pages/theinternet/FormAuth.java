package pages.theinternet;

import com.codeborne.selenide.Condition;
import helpers.Methods;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FormAuth {

    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h2";
    final String FORM = CONTENT + "//form[@id='login']";

    final String USERNAME = FORM + "//input[@id='username']";
    final String PASSWORD = FORM + "//input[@id='password']";

    final String SUBMIT = FORM + "//button[@type='submit']";

    public FormAuth() {
        $x(HEADER).shouldHave(Condition.exactText("Login Page"));
        $x(SUBMIT).shouldBe(Condition.visible);
    }

    @Step("Type username")
    public FormAuth inputLogin(String login) {
        $x(USERNAME).sendKeys(login);
        return this;
    }

    @Step("Type password")
    public FormAuth inputPass(String pass) {
        $x(PASSWORD).sendKeys(pass);
        return this;
    }

    // Example of generic method which returns new class of type passed as argument.
    // Can be used in cases where a single action can lead to multiple different pages.
    @Step("Press Submit")
    public <T> T submit(Class<T> tClass) {
        $x(SUBMIT).click();
        return Methods.newInstance(tClass);
    }


}

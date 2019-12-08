package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FormAuth {

    private String content = "//div[@id='content']";
    private String header = content + "//h2";
    private String form = content + "//form[@id='login']";
    private String msg = "//*[@id='flash']";

    private String username = form + "//input[@id='username']";
    private String password = form + "//input[@id='password']";

    private String submit = form + "//button[@type='submit']";
    private String logout = content + "//*[@href='/logout']";

    public FormAuth() {
        $x(header).shouldHave(Condition.exactText("Login Page"));
        $x(submit).shouldBe(Condition.visible);
    }

    @Step("Type username and password")
    public FormAuth inputLoginPass(String login, String pass) {
        $x(username).sendKeys(login);
        $x(password).sendKeys(pass);
        return this;
    }

    @Step("Press Submit")
    public FormAuth submit() {
        $x(submit).click();
        return this;
    }

    @Step("Check that we have been logged in successfully")
    public FormAuth checkLoggedIn() {
        $x(msg).shouldHave(Condition.text("You logged into a secure area!"));
        $x(header).shouldHave(Condition.exactText("Secure Area"));
        $x(logout).shouldBe(Condition.visible);
        return this;
    }

    @Step("Press Logout")
    public FormAuth logout() {
        $x(logout).click();
        return this;
    }

    @Step("Check that we have been logged out")
    public FormAuth checkLoggedOut() {
        $x(msg).shouldHave(Condition.text("You logged out of the secure area!"));
        $x(username).shouldBe(Condition.visible);
        $x(password).shouldBe(Condition.visible);
        $x(submit).shouldBe(Condition.visible);
        return this;
    }


}

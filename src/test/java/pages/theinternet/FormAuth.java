package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FormAuth {

    private static final String content = "//div[@id='content']";
    private static final String header = content + "//h2";
    private static final String form = content + "//form[@id='login']";
    private static final String msg = "//*[@id='flash']";

    private static final String username = form + "//input[@id='username']";
    private static final String password = form + "//input[@id='password']";

    private static final String submit = form + "//button[@type='submit']";
    private static final String logout = content + "//*[@href='/logout']";

    public FormAuth() {
        $x(header).shouldHave(Condition.exactText("Login Page"));
        $x(submit).shouldBe(Condition.visible);
    }

    @Step("Type username")
    @When("I enter login {word}")
    public FormAuth inputLogin(String login) {
        $x(username).sendKeys(login);
        return this;
    }

    @Step("Type password")
    @When("I enter password {word}")
    public FormAuth inputPass(String pass) {
        $x(password).sendKeys(pass);
        return this;
    }

    @Step("Press Submit")
    @When("I press Submit")
    public FormAuth submit() {
        $x(submit).click();
        return this;
    }

    @Step("Check that we have been logged in successfully")
    @Then("I am logged in")
    public FormAuth checkLoggedIn() {
        $x(msg).shouldHave(Condition.text("You logged into a secure area!"));
        $x(header).shouldHave(Condition.exactText("Secure Area"));
        $x(logout).shouldBe(Condition.visible);
        return this;
    }

    @Step("Press Logout")
    @When("I press Logout")
    public FormAuth logout() {
        $x(logout).click();
        return this;
    }

    @Step("Check that we have been logged out")
    @Then("I am logged out")
    public FormAuth checkLoggedOut() {
        $x(msg).shouldHave(Condition.text("You logged out of the secure area!"));
        $x(username).shouldBe(Condition.visible);
        $x(password).shouldBe(Condition.visible);
        $x(submit).shouldBe(Condition.visible);
        return this;
    }


}

package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class FormAuth {

    private final String CONTENT = "//div[@id='content']";
    private final String HEADER = CONTENT + "//h2";
    private final String FORM = CONTENT + "//form[@id='login']";
    private final String MSG = "//*[@id='flash']";

    private final String USERNAME = FORM + "//input[@id='username']";
    private final String PASSWORD = FORM + "//input[@id='password']";

    private final String SUBMIT = FORM + "//button[@type='submit']";
    private final String LOGOUT = CONTENT + "//*[@href='/logout']";

    public FormAuth() {
        $x(HEADER).shouldHave(Condition.exactText("Login Page"));
        $x(SUBMIT).shouldBe(Condition.visible);
    }

    @Step("Type username")
    @When("I enter login {word}")
    public FormAuth inputLogin(String login) {
        $x(USERNAME).sendKeys(login);
        return this;
    }

    @Step("Type password")
    @When("I enter password {word}")
    public FormAuth inputPass(String pass) {
        $x(PASSWORD).sendKeys(pass);
        return this;
    }

    @Step("Press Submit")
    @When("I press Submit")
    public FormAuth submit() {
        $x(SUBMIT).click();
        return this;
    }

    @Step("Check that we have been logged in successfully")
    @Then("I am logged in")
    public FormAuth checkLoggedIn() {
        $x(MSG).shouldHave(Condition.text("You logged into a secure area!"));
        $x(HEADER).shouldHave(Condition.exactText("Secure Area"));
        $x(LOGOUT).shouldBe(Condition.visible);
        return this;
    }

    @Step("Press Logout")
    @When("I press Logout")
    public FormAuth logout() {
        $x(LOGOUT).click();
        return this;
    }

    @Step("Check that we have been logged out")
    @Then("I am logged out")
    public FormAuth checkLoggedOut() {
        $x(MSG).shouldHave(Condition.text("You logged out of the secure area!"));
        $x(USERNAME).shouldBe(Condition.visible);
        $x(PASSWORD).shouldBe(Condition.visible);
        $x(SUBMIT).shouldBe(Condition.visible);
        return this;
    }


}

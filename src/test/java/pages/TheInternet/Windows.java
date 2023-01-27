package pages.TheInternet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Windows {
    private final String CONTENT = "//div[@id='content']";
    private final String HEADER = CONTENT + "//h3";
    private final String NEWWINLINK = CONTENT + "//a[@href='windows/new.html']";

    public Windows() {
        checkPageTitle("The Internet");
        $x(HEADER).shouldHave(Condition.text("Opening a new window"));
        $x(NEWWINLINK).shouldBe(Condition.visible);
    }

    @Step("Check page title: {pageTitle}")
    @Then("Page title should be {string}")
    public void checkPageTitle(String pageTitle) {
        Selenide.title().equals(pageTitle);
    }

    @Step("Switch to tab: {windowName}")
    @When("I switch to tab {string}")
    public void switchToWindow(String windowName) {
        Selenide.switchTo().window(windowName);
    }

    @Step("Click on the link to open new tab")
    @When("I click link to open New Window")
    public NewWindow openNewWindow() {
        $x(NEWWINLINK).click();
        return new NewWindow();
    }

    public class NewWindow {

        private final String HEADER = "//div[@class='example']//h3";

        public NewWindow() {
            switchToWindow("New Window");
            $x(HEADER).shouldHave(Condition.text("New Window"));
            checkPageTitle("New Window");
        }

        @Step("Switch back to previous tab")
        public Windows switchBackToMainWindow() {
            switchToWindow("The Internet");
            return new Windows();
        }
    }

}

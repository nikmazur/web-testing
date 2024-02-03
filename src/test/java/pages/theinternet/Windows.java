package pages.theinternet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class Windows {
    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h3";
    final String NEW_WINDOW = CONTENT + "//a[@href='windows/new.html']";

    public Windows() {
        checkPageTitle("The Internet");
        $x(HEADER).shouldHave(Condition.text("Opening a new window"));
        $x(NEW_WINDOW).shouldBe(Condition.visible);
    }

    @Step("Check page title: {pageTitle}")
    @Then("Page title should be {string}")
    public void checkPageTitle(String pageTitle) {
        assertEquals(pageTitle, Selenide.title());
    }

    @Step("Switch to tab: {windowName}")
    @When("I switch to tab {string}")
    public void switchToWindow(String windowName) {
        Selenide.switchTo().window(windowName);
    }

    @Step("Click on the link to open new tab")
    @When("I click link to open New Window")
    public NewWindow openNewWindow() {
        $x(NEW_WINDOW).click();
        return new NewWindow();
    }

    public class NewWindow {

        final String HEADER = "//div[@class='example']//h3";
        final String NEW_WINDOW = "New Window";

        public NewWindow() {
            switchToWindow(NEW_WINDOW);
            $x(HEADER).shouldHave(Condition.text(NEW_WINDOW));
            checkPageTitle(NEW_WINDOW);
        }

        @Step("Switch back to previous tab")
        public Windows switchBackToMainWindow() {
            switchToWindow("The Internet");
            return new Windows();
        }
    }

}

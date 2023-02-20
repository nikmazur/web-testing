package pages.theinternet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class Windows {
    private static final String content = "//div[@id='content']";
    private static final String header = content + "//h3";
    private static final String newWindowLink = content + "//a[@href='windows/new.html']";

    public Windows() {
        checkPageTitle("The Internet");
        $x(header).shouldHave(Condition.text("Opening a new window"));
        $x(newWindowLink).shouldBe(Condition.visible);
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
        $x(newWindowLink).click();
        return new NewWindow();
    }

    public class NewWindow {

        private static final String header = "//div[@class='example']//h3";
        private static final String nWindow = "New Window";

        public NewWindow() {
            switchToWindow(nWindow);
            $x(header).shouldHave(Condition.text(nWindow));
            checkPageTitle(nWindow);
        }

        @Step("Switch back to previous tab")
        public Windows switchBackToMainWindow() {
            switchToWindow("The Internet");
            return new Windows();
        }
    }

}

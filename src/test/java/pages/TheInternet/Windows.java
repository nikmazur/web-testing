package pages.TheInternet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class Windows {
    private String content = "//div[@id='content']";
    private String header = content + "//h3";
    private String newWinLink = content + "//a[@href='windows/new.html']";

    public Windows() {
        $x(header).shouldHave(Condition.exactText("Opening a new window"));
        $x(newWinLink).shouldBe(Condition.visible);
    }

    @Step("Click on the link to open new tab")
    public NewWindow openNewWindow() {
        $x(newWinLink).click();
        return new NewWindow();
    }

    public class NewWindow {

        private String header = "//div[@class='example']//h3";

        public NewWindow() {
            Selenide.switchTo().window("New Window");
            $x(header).shouldHave(Condition.exactText("New Window"));
        }

        @Step("Switch back to previous tab")
        public Windows switchWindows() {
            Selenide.switchTo().window("The Internet");
            return new Windows();
        }
    }
}

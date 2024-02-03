package pages.seleasy;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class JavascriptAlertsPage extends PageLayout {
    final String HEADER = "//h3";
    final String BUTTON_CONFIRM_ALERT = "//button[@onclick='myConfirmFunction()']";

    public JavascriptAlertsPage() {
        $x(HEADER).shouldHave(Condition.text("JavaScript has three kind of popup boxes"));
        $x(BUTTON_CONFIRM_ALERT).shouldBe(Condition.visible);
    }

    @Step("Display alert pop-up, dismiss it by Accepting")
    public JavascriptAlertsPage showAndConfirmAlert() {
        $x(BUTTON_CONFIRM_ALERT).click();
        Selenide.switchTo().alert(Duration.ofSeconds(3)).accept();
        //Verify that the alert has closed by switching to it again and catching the exception
        try {
            Selenide.switchTo().alert();
        } catch (Error ignored) {}
        return this;
    }
}

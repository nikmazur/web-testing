package pages.SelEasy;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class JavascriptAlertsPage extends PageLayout {
    private String header = "//h3";
    private String buttonConfirmAlert = "//button[@onclick='myConfirmFunction()']";

    public JavascriptAlertsPage() {
        $x(header).shouldHave(Condition.text("JavaScript has three kind of popup boxes"));
        $x(buttonConfirmAlert).shouldBe(Condition.visible);
    }

    @Step("Display alert pop-up, dismiss it by Accepting")
    public JavascriptAlertsPage showAndConfirmAlert() {
        $x(buttonConfirmAlert).click();
        Selenide.switchTo().alert(Duration.ofSeconds(3)).accept();
        //Verify that the alert has closed by switching to it again and catching the exception
        try {
            Selenide.switchTo().alert();
        } catch (TimeoutException ignored) {}
        return this;
    }
}

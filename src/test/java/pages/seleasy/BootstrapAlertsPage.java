package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BootstrapAlertsPage extends PageLayout {

    final String HEADER = "//h2";
    final String ALERTS_BOX = "//div[@class='col-md-4']";
    final String ALERTS_NOTIF_BOX = "//div[contains(@class,'alert')]";

    public BootstrapAlertsPage() {
        $x(HEADER).shouldHave(Condition.exactText("Bootstrap Alert messages"));
        $x(ALERTS_BOX).shouldBe(Condition.visible);
    }

    @Step("Click on {alertName}, wait for it to close")
    public BootstrapAlertsPage clickAlertAndWait(int alertId, String alertName) {
        String alertNotification = ALERTS_NOTIF_BOX + "[" + alertId + "]";

        $x(ALERTS_BOX + "/button[contains(text(),'" + alertName + "')]").click();
        $x(alertNotification).shouldBe(Condition.visible);
        $x(alertNotification).shouldHave(Condition.text(alertName.toLowerCase()));
        $x(alertNotification).shouldBe(Condition.hidden, Duration.ofSeconds(10));
        return this;
    }

    @Step("Click on {alertName}, close, check it's closed")
    public BootstrapAlertsPage clickAlertAndClose(int alertId, String alertName) {
        var alertNotification = ALERTS_NOTIF_BOX + "[" + alertId + "]";

        $x(ALERTS_BOX + "/button[contains(text(),'" + alertName + "')]").click();
        $x(alertNotification).shouldBe(Condition.visible);
        $x(alertNotification).shouldHave(Condition.text(alertName.toLowerCase()));
        $x(alertNotification + "/button").click();
        $x(alertNotification).shouldBe(Condition.hidden);
        return this;
    }
}

package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BootstrapAlertsPage extends PageLayout {

    private final String HEADER = "//h2";
    private final String ALERTSBOX = "//div[@class='col-md-4']";
    private final String ALERTSNOTIFBOX = "//div[contains(@class,'alert')]";

    public BootstrapAlertsPage() {
        $x(HEADER).shouldHave(Condition.exactText("Bootstrap Alert messages"));
        $x(ALERTSBOX).shouldBe(Condition.visible);
    }

    @Step("Click on {alertName}, wait for it to close")
    public BootstrapAlertsPage clickAlertAndWait(int alertId, String alertName) {
        String alertNotification = ALERTSNOTIFBOX + "[" + alertId + "]";

        $x(ALERTSBOX + "/button[contains(text(),'" + alertName + "')]").click();
        $x(alertNotification).shouldBe(Condition.visible);
        $x(alertNotification).shouldHave(Condition.text(alertName.toLowerCase()));
        $x(alertNotification).shouldBe(Condition.hidden, Duration.ofSeconds(10));
        return this;
    }

    @Step("Click on {alertName}, close, check it's closed")
    public BootstrapAlertsPage clickAlertAndClose(int alertId, String alertName) {
        String alertNotification = ALERTSNOTIFBOX + "[" + alertId + "]";

        $x(ALERTSBOX + "/button[contains(text(),'" + alertName + "')]").click();
        $x(alertNotification).shouldBe(Condition.visible);
        $x(alertNotification).shouldHave(Condition.text(alertName.toLowerCase()));
        $x(alertNotification + "/button").click();
        $x(alertNotification).shouldBe(Condition.hidden);
        return this;
    }
}

package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class BootstrapAlertsPage extends PageLayout {

    private String header = "//h2";
    private String alertsBox = "//div[@class='col-md-4']";
    private String alertsNotifBox = "//div[contains(@class,'alert')]";

    public BootstrapAlertsPage() {
        $x(header).shouldHave(Condition.exactText("Bootstrap Alert messages"));
        $x(alertsBox).shouldBe(Condition.visible);
    }

    @Step("Click on {alertName}, wait for it to close")
    public BootstrapAlertsPage clickAlertAndWait(int alertId, String alertName) {
        String alertNotification = alertsNotifBox + "[" + alertId + "]";

        $x(alertsBox + "/button[contains(text(),'" + alertName + "')]").click();
        $x(alertNotification).shouldBe(Condition.visible);
        $x(alertNotification).shouldHave(Condition.text(alertName.toLowerCase()));
        $x(alertNotification).waitUntil(Condition.hidden, 10000);
        return this;
    }

    @Step("Click on {alertName}, close, check it's closed")
    public BootstrapAlertsPage clickAlertAndClose(int alertId, String alertName) {
        String alertNotification = alertsNotifBox + "[" + alertId + "]";

        $x(alertsBox + "/button[contains(text(),'" + alertName + "')]").click();
        $x(alertNotification).shouldBe(Condition.visible);
        $x(alertNotification).shouldHave(Condition.text(alertName.toLowerCase()));
        $x(alertNotification + "/button").click();
        $x(alertNotification).shouldBe(Condition.hidden);
        return this;
    }
}

package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DownloadBarPage extends PageLayout {

    private static final String header = "//h2";
    private static final String downloadButton = "//button[@id='downloadButton']";

    private static final String dlDialog = "//div[@id='dialog']";
    private static final String dlStatus = dlDialog + "/div[text()='Complete!']";
    private static final String dialogButton = dlDialog + "/..//button[text()='Close']";

    public DownloadBarPage() {
        $x(header).shouldHave(Condition.exactText("JQuery UI Progress bar - Download Dialog"));
        $x(downloadButton).shouldBe(Condition.visible);
    }

    @Step("Press Start Download, wait until 1) dialog reads \"File download Complete!\" 2) button has Close text")
    public DownloadBarPage runDownload() {
        $x(downloadButton).click();
        $x(dlStatus).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $x(dialogButton).shouldBe(Condition.visible);
        return this;
    }
}

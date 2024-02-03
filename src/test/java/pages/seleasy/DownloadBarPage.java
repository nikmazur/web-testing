package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DownloadBarPage extends PageLayout {

    final String HEADER = "//h2";
    final String DOWNLOAD_BUTTON = "//button[@id='downloadButton']";

    final String DL_DIALOG = "//div[@id='dialog']";
    final String DOWNLOAD_STATUS = DL_DIALOG + "/div[text()='Complete!']";
    final String DIALOG_BUTTON = DL_DIALOG + "/..//button[text()='Close']";

    public DownloadBarPage() {
        $x(HEADER).shouldHave(Condition.exactText("JQuery UI Progress bar - Download Dialog"));
        $x(DOWNLOAD_BUTTON).shouldBe(Condition.visible);
    }

    @Step("Press Start Download, wait until 1) dialog reads \"File download Complete!\" 2) button has Close text")
    public DownloadBarPage runDownload() {
        $x(DOWNLOAD_BUTTON).click();
        $x(DOWNLOAD_STATUS).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $x(DIALOG_BUTTON).shouldBe(Condition.visible);
        return this;
    }
}

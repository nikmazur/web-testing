package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class DownloadBarPage extends PageLayout {

    private final String HEADER = "//h2";
    private final String DOWNLOADBUTTON = "//button[@id='downloadButton']";

    private final String DLDIALOG = "//div[@id='dialog']";
    private final String DLSTATUS = DLDIALOG + "/div[text()='Complete!']";
    private final String DIALOGBUTTON = DLDIALOG + "/..//button[text()='Close']";

    public DownloadBarPage() {
        $x(HEADER).shouldHave(Condition.exactText("JQuery UI Progress bar - Download Dialog"));
        $x(DOWNLOADBUTTON).shouldBe(Condition.visible);
    }

    @Step("Press Start Download, wait until 1) dialog reads \"File download Complete!\" 2) button has Close text")
    public DownloadBarPage runDownload() {
        $x(DOWNLOADBUTTON).click();
        $x(DLSTATUS).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $x(DIALOGBUTTON).shouldBe(Condition.visible);
        return this;
    }
}

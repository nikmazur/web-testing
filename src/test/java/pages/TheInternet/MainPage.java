package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private String header = "//h1";
    private String footer = "page-footer";

    private String formAuth = "//a[@href='login.html']";
    private String checkboxesLink = "//a[@href='checkboxes.html']";
    private String dropdownLink = "//a[@href='dropdown.html']";
    private String keyPressesLink = "//a[@href='key_presses.html']";
    private String windowsLink = "//a[@href='windows.html']";
    private String sliderLink = "//a[@href='horizontal_slider.html']";
    private String largeTableLink = "//a[@href='large.html']";

    public MainPage() {
        $x(header).shouldHave(Condition.text("Welcome to the-internet"), Duration.ofSeconds(10));
        $(By.id(footer)).shouldBe(Condition.visible);
    }

    @Step("Click link to Basic Auth page")
    public FormAuth openFormAuth() {
        $x(formAuth).click();
        return new FormAuth();
    }

    @Step("Click link to Checkboxes page")
    public Checkboxes openCheckboxes() {
        $x(checkboxesLink).click();
        return new Checkboxes();
    }

    @Step("Click link to Dropdown page")
    public Dropdown openDropdown() {
        $x(dropdownLink).click();
        return new Dropdown();
    }

    @Step("Click link to Key Presses page")
    public KeyPresses openKeyPresses() {
        $x(keyPressesLink).click();
        return new KeyPresses();
    }

    @Step("Click link to Multiple Windows page")
    public Windows openWindows() {
        $x(windowsLink).click();
        return new Windows();
    }

    @Step("Click link to Horizontal Slider page")
    public Slider openSlider() {
        $x(sliderLink).click();
        return new Slider();
    }

    @Step("Click link to Large Table page")
    public LargeTable openLargeTable() {
        $x(largeTableLink).click();
        return new LargeTable();
    }
}

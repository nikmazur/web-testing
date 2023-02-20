package pages.theinternet;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Given;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private static final String header = "//h1";
    private static final String footer = "page-footer";

    private static final String formAuth = "//a[@href='login.html']";
    private static final String checkboxesLink = "//a[@href='checkboxes.html']";
    private static final String dropdownLink = "//a[@href='dropdown.html']";
    private static final String keyPressesLink = "//a[@href='key_presses.html']";
    private static final String windowsLink = "//a[@href='windows.html']";
    private static final String sliderLink = "//a[@href='horizontal_slider.html']";
    private static final String largeTableLink = "//a[@href='large.html']";

    private static final String projPath = new File("").getAbsolutePath();
    private static final String s = File.separator;

    public MainPage() {
        // Opens local site mirror in the proj directory
        Selenide.open("file://" + projPath + s + "sites" + s + "theinternet" + s + "index.html");
        $x(header).shouldHave(Condition.text("Welcome to the-internet"), Duration.ofSeconds(10));
        $(By.id(footer)).shouldBe(Condition.visible);
    }

    @Step("Click link to Basic Auth page")
    @Given("I am on the Basic Auth page")
    public FormAuth openFormAuth() {
        $x(formAuth).click();
        return new FormAuth();
    }

    @Step("Click link to Checkboxes page")
    @Given("I am on the Checkboxes page")
    public Checkboxes openCheckboxes() {
        $x(checkboxesLink).click();
        return new Checkboxes();
    }

    @Step("Click link to Dropdown page")
    @Given("I am on the Dropdown page")
    public Dropdown openDropdown() {
        $x(dropdownLink).click();
        return new Dropdown();
    }

    @Step("Click link to Key Presses page")
    @Given("I am on the Key Presses page")
    public KeyPresses openKeyPresses() {
        $x(keyPressesLink).click();
        return new KeyPresses();
    }

    @Step("Click link to Multiple Windows page")
    @Given("I am on the Multiple Windows page")
    public Windows openWindows() {
        $x(windowsLink).click();
        return new Windows();
    }

    @Step("Click link to Horizontal Slider page")
    @Given("I am on the Horizontal Slider page")
    public SliderPage openSlider() {
        $x(sliderLink).click();
        return new SliderPage();
    }

    @Step("Click link to Large Table page")
    @Given("I am on the Large Table page")
    public LargeTable openLargeTable() {
        $x(largeTableLink).click();
        return new LargeTable();
    }
}

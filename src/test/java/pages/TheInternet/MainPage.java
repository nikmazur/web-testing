package pages.TheInternet;

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

    private final String HEADER = "//h1";
    private final String FOOTER = "page-footer";

    private final String FORMAUTH = "//a[@href='login.html']";
    private final String CHECKBOXESLINK = "//a[@href='checkboxes.html']";
    private final String DROPDOWNLINK = "//a[@href='dropdown.html']";
    private final String KEYPRESSESLINK = "//a[@href='key_presses.html']";
    private final String WINDOWSLINK = "//a[@href='windows.html']";
    private final String SLIDERLINK = "//a[@href='horizontal_slider.html']";
    private final String LARGETABLELINK = "//a[@href='large.html']";

    final String PROJ_PATH = new File("").getAbsolutePath();
    final String S = File.separator;

    public MainPage() {
        // Opens local site mirror in the proj directory
        Selenide.open("file://" + PROJ_PATH + S + "sites" + S + "theinternet" + S + "index.html");
        $x(HEADER).shouldHave(Condition.text("Welcome to the-internet"), Duration.ofSeconds(10));
        $(By.id(FOOTER)).shouldBe(Condition.visible);
    }

    @Step("Click link to Basic Auth page")
    @Given("I am on the Basic Auth page")
    public FormAuth openFormAuth() {
        $x(FORMAUTH).click();
        return new FormAuth();
    }

    @Step("Click link to Checkboxes page")
    @Given("I am on the Checkboxes page")
    public Checkboxes openCheckboxes() {
        $x(CHECKBOXESLINK).click();
        return new Checkboxes();
    }

    @Step("Click link to Dropdown page")
    @Given("I am on the Dropdown page")
    public Dropdown openDropdown() {
        $x(DROPDOWNLINK).click();
        return new Dropdown();
    }

    @Step("Click link to Key Presses page")
    @Given("I am on the Key Presses page")
    public KeyPresses openKeyPresses() {
        $x(KEYPRESSESLINK).click();
        return new KeyPresses();
    }

    @Step("Click link to Multiple Windows page")
    @Given("I am on the Multiple Windows page")
    public Windows openWindows() {
        $x(WINDOWSLINK).click();
        return new Windows();
    }

    @Step("Click link to Horizontal Slider page")
    @Given("I am on the Horizontal Slider page")
    public Slider openSlider() {
        $x(SLIDERLINK).click();
        return new Slider();
    }

    @Step("Click link to Large Table page")
    @Given("I am on the Large Table page")
    public LargeTable openLargeTable() {
        $x(LARGETABLELINK).click();
        return new LargeTable();
    }
}

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

    final String HEADER = "//h1";
    final String FOOTER = "page-footer";

    final String FORM_AUTH = "//a[@href='login.html']";
    final String CHECKBOXES = "//a[@href='checkboxes.html']";
    final String DROPDOWN = "//a[@href='dropdown.html']";
    final String KEY_PRESSES = "//a[@href='key_presses.html']";
    final String WINDOWS = "//a[@href='windows.html']";
    final String SLIDER = "//a[@href='horizontal_slider.html']";
    final String LARGE_TABLE = "//a[@href='large.html']";

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
        $x(FORM_AUTH).click();
        return new FormAuth();
    }

    @Step("Click link to Checkboxes page")
    @Given("I am on the Checkboxes page")
    public Checkboxes openCheckboxes() {
        $x(CHECKBOXES).click();
        return new Checkboxes();
    }

    @Step("Click link to Dropdown page")
    @Given("I am on the Dropdown page")
    public Dropdown openDropdown() {
        $x(DROPDOWN).click();
        return new Dropdown();
    }

    @Step("Click link to Key Presses page")
    @Given("I am on the Key Presses page")
    public KeyPresses openKeyPresses() {
        $x(KEY_PRESSES).click();
        return new KeyPresses();
    }

    @Step("Click link to Multiple Windows page")
    @Given("I am on the Multiple Windows page")
    public Windows openWindows() {
        $x(WINDOWS).click();
        return new Windows();
    }

    @Step("Click link to Horizontal Slider page")
    @Given("I am on the Horizontal Slider page")
    public SliderPage openSlider() {
        $x(SLIDER).click();
        return new SliderPage();
    }

    @Step("Click link to Large Table page")
    @Given("I am on the Large Table page")
    public LargeTable openLargeTable() {
        $x(LARGE_TABLE).click();
        return new LargeTable();
    }
}

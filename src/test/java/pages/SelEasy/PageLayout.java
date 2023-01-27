package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PageLayout {

    private final String LOGO = "//div[@class='logo']";
    private final String FOOTER = "//footer[@class='footer']";
    private final String MENU = "//*[@id='treemenu']";

    private final String MENUINPUTFORMS = MENU + "//a[text()='Input Forms']";
    private final String MENUSIMPLEFORMDEMO = MENUINPUTFORMS + "/..//a[text()='Simple Form Demo']";
    private final String MENUINPUTFORMSUBMIT = MENUINPUTFORMS + "/..//a[text()='Input Form Submit']";

    private final String MENUTABLE = MENU + "//a[text()='Table']";
    private final String MENUTABLEDATASEARCH = MENUTABLE + "/..//a[text()='Table Data Search']";

    private final String MENUPROGRESSBARS = MENU + "//a[text()='Progress Bars & Sliders']";
    private final String MENUDOWNLOADBAR = MENUPROGRESSBARS + "/..//a[text()='JQuery Download Progress bars']";
    private final String MENUSLIDERS = MENUPROGRESSBARS + "/..//a[text()='Drag & Drop Sliders']";

    private final String MENUALERTS = MENU + "//a[text()='Alerts & Modals']";
    private final String MENUBOOTSTRAPALERTS = MENUALERTS + "/..//a[text()='Bootstrap Alerts']";
    private final String MENUJAVASCRIPTALERTS = MENUALERTS + "/..//a[text()='Javascript Alerts']";

    public PageLayout() {
        checkGeneralLayout();
    }

    @Step("Check that page layout is loaded (Logo, Menu, Footer)")
    private void checkGeneralLayout() {
        $x(LOGO).shouldBe(Condition.visible);
        $x(MENU).shouldBe(Condition.visible);
        $x(FOOTER).shouldBe(Condition.visible);
    }

    @Step("Open Simple Form Demo page from the menu")
    public SimpleFormDemo openSimpleFormDemo() {
        $x(MENUINPUTFORMS).click();
        $x(MENUSIMPLEFORMDEMO).click();
        return new SimpleFormDemo();
    }

    @Step("Open Input Form Submit page from the menu")
    public InputFormSubmit openInputFormSubmit() {
        $x(MENUINPUTFORMS).click();
        $x(MENUINPUTFORMSUBMIT).click();
        return new InputFormSubmit();
    }

    @Step("Open Input Form Submit page from the menu")
    public TableDataSearch openTableDataSearch() {
        $x(MENUTABLE).click();
        $x(MENUTABLEDATASEARCH).click();
        return new TableDataSearch();
    }

    @Step("Open Download Progress Bar page from the menu")
    public DownloadBarPage openDownloadBarPage() {
        $x(MENUPROGRESSBARS).click();
        $x(MENUDOWNLOADBAR).click();
        return new DownloadBarPage();
    }

    @Step("Open Drag & Drop Sliders page from the menu")
    public SlidersPage openSlidersPage() {
        $x(MENUPROGRESSBARS).click();
        $x(MENUSLIDERS).click();
        return new SlidersPage();
    }

    @Step("Open Bootstrap Alerts page from the menu")
    public BootstrapAlertsPage openBootstrapAlertsPage() {
        $x(MENUALERTS).click();
        $x(MENUBOOTSTRAPALERTS).click();
        return new BootstrapAlertsPage();
    }

    @Step("Open Javascript Alerts page from the menu")
    public JavascriptAlertsPage openJavascriptAlertsPage() {
        $x(MENUALERTS).click();
        $x(MENUJAVASCRIPTALERTS).click();
        return new JavascriptAlertsPage();
    }
}

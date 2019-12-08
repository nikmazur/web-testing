package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PageLayout {

    private String logo = "//div[@class='logo']";
    private String footer = "//footer[@class='footer']";
    private String menu = "//*[@id='treemenu']";

    private String menuInputForms = menu + "//a[text()='Input Forms']";
    private String menuSimpleFormDemo = menuInputForms + "/..//a[text()='Simple Form Demo']";
    private String menuInputFormSubmit = menuInputForms + "/..//a[text()='Input Form Submit']";

    private String menuTable = menu + "//a[text()='Table']";
    private String menuTableDataSearch = menuTable + "/..//a[text()='Table Data Search']";

    private String menuProgressBars = menu + "//a[text()='Progress Bars & Sliders']";
    private String menuDownloadBar = menuProgressBars + "/..//a[text()='JQuery Download Progress bars']";
    private String menuSliders = menuProgressBars + "/..//a[text()='Drag & Drop Sliders']";

    private String menuAlerts = menu + "//a[text()='Alerts & Modals']";
    private String menuBootstrapAlerts = menuAlerts + "/..//a[text()='Bootstrap Alerts']";
    private String menuJavascriptAlerts = menuAlerts + "/..//a[text()='Javascript Alerts']";

    public PageLayout() {
        checkGeneralLayout();
    }

    @Step("Check that page layout is loaded (Logo, Menu, Footer)")
    private void checkGeneralLayout() {
        $x(logo).shouldBe(Condition.visible);
        $x(menu).shouldBe(Condition.visible);
        $x(footer).shouldBe(Condition.visible);
    }

    @Step("Open Simple Form Demo page from the menu")
    public SimpleFormDemo openSimpleFormDemo() {
        $x(menuInputForms).click();
        $x(menuSimpleFormDemo).click();
        return new SimpleFormDemo();
    }

    @Step("Open Input Form Submit page from the menu")
    public InputFormSubmit openInputFormSubmit() {
        $x(menuInputForms).click();
        $x(menuInputFormSubmit).click();
        return new InputFormSubmit();
    }

    @Step("Open Input Form Submit page from the menu")
    public TableDataSearch openTableDataSearch() {
        $x(menuTable).click();
        $x(menuTableDataSearch).click();
        return new TableDataSearch();
    }

    @Step("Open Download Progress Bar page from the menu")
    public DownloadBarPage openDownloadBarPage() {
        $x(menuProgressBars).click();
        $x(menuDownloadBar).click();
        return new DownloadBarPage();
    }

    @Step("Open Drag & Drop Sliders page from the menu")
    public SlidersPage openSlidersPage() {
        $x(menuProgressBars).click();
        $x(menuSliders).click();
        return new SlidersPage();
    }

    @Step("Open Bootstrap Alerts page from the menu")
    public BootstrapAlertsPage openBootstrapAlertsPage() {
        $x(menuAlerts).click();
        $x(menuBootstrapAlerts).click();
        return new BootstrapAlertsPage();
    }

    @Step("Open Javascript Alerts page from the menu")
    public JavascriptAlertsPage openJavascriptAlertsPage() {
        $x(menuAlerts).click();
        $x(menuJavascriptAlerts).click();
        return new JavascriptAlertsPage();
    }
}

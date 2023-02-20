package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PageLayout {

    private static final String logo = "//div[@class='logo']";
    private static final String footer = "//footer[@class='footer']";
    private static final String menu = "//*[@id='treemenu']";

    private static final String menuInputForms = menu + "//a[text()='Input Forms']";
    private static final String simpleFormDemo = menuInputForms + "/..//a[text()='Simple Form Demo']";
    private static final String inputFormSubmit = menuInputForms + "/..//a[text()='Input Form Submit']";

    private static final String menuTable = menu + "//a[text()='Table']";
    private static final String tableDataSearch = menuTable + "/..//a[text()='Table Data Search']";

    private static final String menuProgressBars = menu + "//a[text()='Progress Bars & Sliders']";
    private static final String downloadBar = menuProgressBars + "/..//a[text()='JQuery Download Progress bars']";
    private static final String menuSliders = menuProgressBars + "/..//a[text()='Drag & Drop Sliders']";

    private static final String menuAlerts = menu + "//a[text()='Alerts & Modals']";
    private static final String bootstrapAlerts = menuAlerts + "/..//a[text()='Bootstrap Alerts']";
    private static final String javascriptAlerts = menuAlerts + "/..//a[text()='Javascript Alerts']";

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
        $x(simpleFormDemo).click();
        return new SimpleFormDemo();
    }

    @Step("Open Input Form Submit page from the menu")
    public InputFormSubmit openInputFormSubmit() {
        $x(menuInputForms).click();
        $x(inputFormSubmit).click();
        return new InputFormSubmit();
    }

    @Step("Open Input Form Submit page from the menu")
    public TableDataSearch openTableDataSearch() {
        $x(menuTable).click();
        $x(tableDataSearch).click();
        return new TableDataSearch();
    }

    @Step("Open Download Progress Bar page from the menu")
    public DownloadBarPage openDownloadBarPage() {
        $x(menuProgressBars).click();
        $x(downloadBar).click();
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
        $x(bootstrapAlerts).click();
        return new BootstrapAlertsPage();
    }

    @Step("Open Javascript Alerts page from the menu")
    public JavascriptAlertsPage openJavascriptAlertsPage() {
        $x(menuAlerts).click();
        $x(javascriptAlerts).click();
        return new JavascriptAlertsPage();
    }
}

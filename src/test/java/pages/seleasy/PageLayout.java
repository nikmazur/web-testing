package pages.seleasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class PageLayout {

    final String LOGO = "//div[@class='logo']";
    final String FOOTER = "//footer[@class='footer']";
    final String MENU = "//*[@id='treemenu']";

    final String MENU_INPUT_FORMS = MENU + "//a[text()='Input Forms']";
    final String SIMPLE_FORM_DEMO = MENU_INPUT_FORMS + "/..//a[text()='Simple Form Demo']";
    final String INPUT_FORM_SUBMIT = MENU_INPUT_FORMS + "/..//a[text()='Input Form Submit']";

    final String MENU_TABLE = MENU + "//a[text()='Table']";
    final String TABLE_DATA_SEARCH = MENU_TABLE + "/..//a[text()='Table Data Search']";

    final String MENU_PROGRESS_BARS = MENU + "//a[text()='Progress Bars & Sliders']";
    final String DOWNLOAD_BAR = MENU_PROGRESS_BARS + "/..//a[text()='JQuery Download Progress bars']";
    final String MENU_SLIDERS = MENU_PROGRESS_BARS + "/..//a[text()='Drag & Drop Sliders']";

    final String MENU_ALERTS = MENU + "//a[text()='Alerts & Modals']";
    final String BOOTSTRAP_ALERTS = MENU_ALERTS + "/..//a[text()='Bootstrap Alerts']";
    final String JS_ALERTS = MENU_ALERTS + "/..//a[text()='Javascript Alerts']";

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
        $x(MENU_INPUT_FORMS).click();
        $x(SIMPLE_FORM_DEMO).click();
        return new SimpleFormDemo();
    }

    @Step("Open Input Form Submit page from the menu")
    public InputFormSubmit openInputFormSubmit() {
        $x(MENU_INPUT_FORMS).click();
        $x(INPUT_FORM_SUBMIT).click();
        return new InputFormSubmit();
    }

    @Step("Open Input Form Submit page from the menu")
    public TableDataSearch openTableDataSearch() {
        $x(MENU_TABLE).click();
        $x(TABLE_DATA_SEARCH).click();
        return new TableDataSearch();
    }

    @Step("Open Download Progress Bar page from the menu")
    public DownloadBarPage openDownloadBarPage() {
        $x(MENU_PROGRESS_BARS).click();
        $x(DOWNLOAD_BAR).click();
        return new DownloadBarPage();
    }

    @Step("Open Drag & Drop Sliders page from the menu")
    public SlidersPage openSlidersPage() {
        $x(MENU_PROGRESS_BARS).click();
        $x(MENU_SLIDERS).click();
        return new SlidersPage();
    }

    @Step("Open Bootstrap Alerts page from the menu")
    public BootstrapAlertsPage openBootstrapAlertsPage() {
        $x(MENU_ALERTS).click();
        $x(BOOTSTRAP_ALERTS).click();
        return new BootstrapAlertsPage();
    }

    @Step("Open Javascript Alerts page from the menu")
    public JavascriptAlertsPage openJavascriptAlertsPage() {
        $x(MENU_ALERTS).click();
        $x(JS_ALERTS).click();
        return new JavascriptAlertsPage();
    }
}

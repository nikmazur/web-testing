package pages.SelEasy;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private String greetingText = "//div[@id='home']//h3";

    private String menuInputForms = PageLayout.getMenu() + "//a[text()='Input Forms']";
    private String menuSimpleFormDemo = menuInputForms + "/..//a[text()='Simple Form Demo']";
    private String menuInputFormSubmit = menuInputForms + "/..//a[text()='Input Form Submit']";

    private String menuTable = PageLayout.getMenu() + "//a[text()='Table']";
    private String menuTableDataSearch = menuTable + "/..//a[text()='Table Data Search']";

    private String menuProgressBars = PageLayout.getMenu() + "//a[text()='Progress Bars & Sliders']";
    private String menuDownloadBar = menuProgressBars + "/..//a[text()='JQuery Download Progress bars']";
    private String menuSliders = menuProgressBars + "/..//a[text()='Drag & Drop Sliders']";

    public MainPage() {
        PageLayout layout = new PageLayout();
        $x(greetingText).shouldHave(Condition.exactText("Welcome to Selenium Easy Demo"));
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
    public DownloadBar openDownloadBarPage() {
        $x(menuProgressBars).click();
        $x(menuDownloadBar).click();
        return new DownloadBar();
    }

    @Step("Open Drag & Drop Sliders page from the menu")
    public Sliders openSlidersPage() {
        $x(menuProgressBars).click();
        $x(menuSliders).click();
        return new Sliders();
    }

}

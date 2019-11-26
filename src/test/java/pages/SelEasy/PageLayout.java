package pages.SelEasy;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class PageLayout {

    private String logo = "//div[@class='logo']";
    private String footer = "//footer[@class='footer']";
    private static String menu = "//*[@id='treemenu']";

    public PageLayout() {
        $x(logo).shouldBe(Condition.visible);
        $x(menu).shouldBe(Condition.visible);
        $x(footer).shouldBe(Condition.visible);
    }

    public static String getMenu() {
        return menu;
    }
}

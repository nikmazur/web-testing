package pages.seleasy;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    final String GREETING_TEXT = "//div[@id='home']//h3";

    public MainPage() {
        $x(GREETING_TEXT).shouldHave(Condition.exactText("Welcome to Selenium Easy Demo"));
    }

    public PageLayout menu() {
        return new PageLayout();
    }

}

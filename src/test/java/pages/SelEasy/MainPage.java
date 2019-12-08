package pages.SelEasy;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private String greetingText = "//div[@id='home']//h3";

    public MainPage() {
        $x(greetingText).shouldHave(Condition.exactText("Welcome to Selenium Easy Demo"));
    }

    public PageLayout menu() {
        return new PageLayout();
    }

}

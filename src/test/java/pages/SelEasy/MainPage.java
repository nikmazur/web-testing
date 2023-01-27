package pages.SelEasy;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final String GREETINGTEXT = "//div[@id='home']//h3";

    public MainPage() {
        $x(GREETINGTEXT).shouldHave(Condition.exactText("Welcome to Selenium Easy Demo"));
    }

    public PageLayout menu() {
        return new PageLayout();
    }

}

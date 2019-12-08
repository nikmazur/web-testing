package pages.TheInternet;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class Slider {

    private String content = "//div[@id='content']";
    private String header = content + "//h3";
    private String slider = content + "//input[@type='range']";
    private String value = content + "//span[@id='range']";

    public Slider() {
        $x(header).shouldHave(Condition.exactText("Horizontal Slider"));
        $x(slider).shouldBe(Condition.visible);
    }

    @Step("Move slider {direction} by {range}, verify result: {result}")
    public Slider moveSliderAssertValue(char direction, double range, String result) {
        if(direction == 'R')
            for(double i = 0; i < range; i += 0.5)
                $x(slider).sendKeys(Keys.ARROW_RIGHT);
        else if(direction == 'L')
            for(double i = 0; i < range; i += 0.5)
                $x(slider).sendKeys(Keys.ARROW_LEFT);

        $x(value).shouldHave(Condition.exactText(result));
        return this;
    }

}

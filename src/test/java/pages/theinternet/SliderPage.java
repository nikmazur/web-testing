package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class SliderPage {

    private static final String content = "//div[@id='content']";
    private static final String header = content + "//h3";
    private static final String slider = content + "//input[@type='range']";
    private static final String value = content + "//span[@id='range']";

    public SliderPage() {
        $x(header).shouldHave(Condition.exactText("Horizontal Slider"));
        $x(slider).shouldBe(Condition.visible);
    }

    @Step("Move slider {direction} by {range}")
    @When("I move the slider {rangeValue} times to the {word}")
    public SliderPage moveSliderAssertValue(double range, String direction) {
        if(direction.equals("right"))
            for(double i = 0; i < range; i += 0.5)
                $x(slider).sendKeys(Keys.ARROW_RIGHT);
        else if(direction.equals("left"))
            for(double i = 0; i < range; i += 0.5)
                $x(slider).sendKeys(Keys.ARROW_LEFT);

        return this;
    }

    @Step("Verify result: {result}")
    @Then("Slider value is {word}")
    public SliderPage checkValue(String result) {
        $x(value).shouldHave(Condition.exactText(result));
        return this;
    }

    @ParameterType(".*")
    public Double rangeValue(String presses) {
        return Integer.parseInt(presses) * 0.5;
    }

}

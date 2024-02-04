package pages.theinternet;

import com.codeborne.selenide.Condition;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class SliderPage {

    final String CONTENT = "//div[@id='content']";
    final String HEADER = CONTENT + "//h3";
    final String SLIDER = CONTENT + "//input[@type='range']";
    final String VALUE = CONTENT + "//span[@id='range']";

    public SliderPage() {
        $x(HEADER).shouldHave(Condition.exactText("Horizontal Slider"));
        $x(SLIDER).shouldBe(Condition.visible);
    }

    @Step("Move slider {direction} by {range}")
    @When("I move the slider {rangeValue} times to the {word}")
    public SliderPage moveSliderAssertValue(double range, String direction) {
        if(direction.equals("right"))
            for(var i = 0.0; i < range; i += 0.5)
                $x(SLIDER).sendKeys(Keys.ARROW_RIGHT);
        else if(direction.equals("left"))
            for(var i = 0.0; i < range; i += 0.5)
                $x(SLIDER).sendKeys(Keys.ARROW_LEFT);

        return this;
    }

    @Step("Verify result: {result}")
    @Then("Slider value is {word}")
    public SliderPage checkValue(String result) {
        $x(VALUE).shouldHave(Condition.exactText(result));
        return this;
    }

    @ParameterType(".*")
    public Double rangeValue(String presses) {
        return Integer.parseInt(presses) * 0.5;
    }

}

package pages.SelEasy;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import helpers.Methods;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class SlidersPage extends PageLayout {

    private final String HEADER = "//h2";

    public SlidersPage() {
        $x(HEADER).shouldHave(Condition.exactText("Range Sliders"));
    }

    @Step("Set Slider {sliderId} to: {newValue}")
    public SlidersPage moveSlider(String sliderId, int newValue) {
        int counter = Integer.parseInt($x("//*[@id='slider" + sliderId + "']//output").getText());

        if(counter == newValue)
            Assert.fail("New value same as current slider value. Please select a different number.");

        final int EXPCOUNTER = sliderMover($x("//*[@id='slider" + sliderId + "']//input"), counter, newValue);

        Methods.waitForSuccess(()->
                        assertEquals(Integer.parseInt($x("//*[@id='slider" + sliderId + "']//output").getText()), EXPCOUNTER),
                10, 250);
        return this;
    }

    @Step("Move from {counter} to {newValue}")
    private int sliderMover(SelenideElement slider, int counter, int newValue) {
        //Using Math.abs to get the absolute difference
        final int DIFF = Math.abs(newValue - counter);
        for(int i = 0; i < DIFF; i++) {
            if (newValue > counter) {
                //If counter is lower, move slider right by pressing Right Arrow key and increase counter
                slider.sendKeys(Keys.ARROW_RIGHT);
                counter++;
            }   else {
                //If higher, the same but reverse (Left Arrow key, decrease counter)
                slider.sendKeys(Keys.ARROW_LEFT);
                counter--;
            }
        }
        return counter;
    }
}

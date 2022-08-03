package tests;

import helpers.GeneralBrowser;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static helpers.Methods.openTheInternet;

@Epic("Web Testing")
@Feature("Testing TheInternet website")
@Test(groups = "TheInternet")
public class TheInternetTests extends GeneralBrowser {

    @Test(description = "Test login page, login & logout")
    public void testLogin() {
        openTheInternet()
                .openFormAuth()
                .inputLoginPass("tomsmith", "SuperSecretPassword!")
                .submit()
                .checkLoggedIn()
                .logout()
                .checkLoggedOut();
    }

    @Test(description = "Checkbox test, switch state & assert")
    public void testCheckboxes() {
        openTheInternet()
                .openCheckboxes()
                .clickCheckbox(1)
                .clickCheckbox(2)
                .verifyCheckbox(1, true)
                .verifyCheckbox(1, false);
    }

    @Test(description = "Select an option from a drop-down list")
    public void testList() {
        openTheInternet()
                .openDropdown()
                .selectOptionCheckValue(1, "Option 1")
                .selectOptionCheckValue(2, "Option 2");
    }

    @Test(description = "Check keyboard presses")
    @Description("Test for checking keyboard presses. The page prints the last pressed key on the keyboard.")
    public void testKeyboard() {
        openTheInternet()
                .openKeyPresses()
                .pressKeyCheckResult(Keys.PAUSE, "PAUSE")
                .pressKeyCheckResult(Keys.NUMPAD6, "NUMPAD6");
    }


    @Test(description = "Test for handling multiple browser windows")
    public void testNewWind() {
        openTheInternet()
                .openWindows()
                .openNewWindow()
                .switchWindows();
    }

    @Test(description = "Slider element test")
    @Description("We move the slider by keyboard arrow presses and check the resulting value.")
    public void testSliderVal() {
        openTheInternet()
                .openSlider()
                .moveSliderAssertValue('R', 1, "1")
                .moveSliderAssertValue('R', 1.5, "2.5")
                .moveSliderAssertValue('L', 0.5, "2")
                .moveSliderAssertValue('L', 0.5, "1.5");
    }

    @Test(description = "Table test")
    @Description("Test large table with data (50 x 50 cells) by counting rows and columns, and selecting random cells")
    public void testTable() {
        openTheInternet()
                .openLargeTable()
                .runRandomSelections(10);
    }
}

package tests;

import helpers.GeneralBrowser;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import pages.theinternet.FormAuthFail;
import pages.theinternet.FormAuthSuccess;
import pages.theinternet.MainPage;

@Epic("Web Testing")
@Feature("Testing TheInternet website")
@Test(groups = "TheInternet")
public class TheInternetTests extends GeneralBrowser {

    @Test(description = "Test login successful, login & logout")
    public void testLoginSuccess() {
        new MainPage()
                .openFormAuth()
                .inputLogin("tomsmith").inputPass("SuperSecretPassword!")
                .submit(FormAuthSuccess.class)
                .checkLoggedIn()
                .logout()
                .checkLoggedOut();
    }

    @Test(description = "Test login failed")
    public void testLoginFail() {
        new MainPage()
                .openFormAuth()
                .inputLogin("bad login").inputPass("bad pass")
                .submit(FormAuthFail.class)
                .checkLoginFailed();
    }

    @Test(description = "Checkbox test, switch state & assert")
    public void testCheckboxes() {
        new MainPage()
                .openCheckboxes()
                .clickCheckbox(1)
                .clickCheckbox(2)
                .verifyCheckbox(1, true)
                .verifyCheckbox(1, false);
    }

    @Test(description = "Select an option from a drop-down list")
    public void testList() {
        new MainPage()
                .openDropdown()
                .selectOption(1).checkValue("Option 1")
                .selectOption(2).checkValue("Option 2");
    }

    @Test(description = "Check keyboard presses")
    @Description("Test for checking keyboard presses. The page prints the last pressed key on the keyboard.")
    public void testKeyboard() {
        new MainPage()
                .openKeyPresses()
                .pressKey("PAUSE").checkResult("PAUSE")
                .pressKey("NUMPAD6").checkResult("NUMPAD6");
    }


    @Test(description = "Test for handling multiple browser windows")
    public void testNewWind() {
        new MainPage()
                .openWindows()
                .openNewWindow()
                .switchBackToMainWindow();
    }

    @Test(description = "Slider element test")
    @Description("Move the slider by keyboard arrow presses and check the resulting value.")
    public void testSliderVal() {
        new MainPage()
                .openSlider()
                .moveSliderAssertValue(1, "right").checkValue("1")
                .moveSliderAssertValue(1.5, "right").checkValue("2.5")
                .moveSliderAssertValue(0.5, "left").checkValue("2")
                .moveSliderAssertValue(0.5, "left").checkValue("1.5");
    }

    @Test(description = "Table test")
    @Description("Test large table with data (50 x 50 cells) by counting rows and columns, and selecting random cells")
    public void testTable() {
        new MainPage()
                .openLargeTable()
                .runRandomSelections(10);
    }
}

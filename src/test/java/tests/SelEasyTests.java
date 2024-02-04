package tests;

import helpers.GeneralBrowser;
import helpers.Methods;
import helpers.RandomPerson;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static helpers.Methods.openSelEasy;

@Epic("Web Testing")
@Feature("Testing Selenium Easy website")
@Test(groups = "SelEasy")
public class SelEasyTests extends GeneralBrowser {

    @Test(description = "Text input")
    @Description("Check simple input field with random text, which prints it back")
    public void inputText() {
        openSelEasy()
                .menu().openSimpleFormDemo()
                .inputTextCheckResult("Sample Message")
                .clearSingleInput()
                .inputTextCheckResult(RandomStringUtils.randomAlphanumeric(30));
    }

    @Test(description = "Fill out form")
    @Description("Fill out a sample form with contact details")
    public void fillForm() {
        openSelEasy()
                .menu().openInputFormSubmit()
                .fillInputForm(new RandomPerson(), true, "A very special project")
                .fillInputForm(new RandomPerson(), false, RandomStringUtils.randomAlphanumeric(50));
    }

    @Test(description = "Filter table")
    @Description("Filter a sample Tasks table by some text, check number of rows after filtration")
    public void filterTable() {
        openSelEasy()
                .menu().openTableDataSearch()
                .filterCheckResult("John", 2)
                .filterCheckResult("7", 1)
                .filterCheckResult("In progress", 3);
    }

    @Test(description = "Long download process")
    @Description("Initiate the fake download process (takes about 12 seconds)")
    public void progressMsg() {
        openSelEasy()
                .menu().openDownloadBarPage()
                .runDownload();
    }

    @Test(description = "Move slider")
    @Description("Check different slider widgets by moving them a random number of times in a random direction")
    public void moveSlider() {
        openSelEasy()
                .menu().openSlidersPage()
                .moveSlider("1", 26)
                .moveSlider("2", Methods.RNG.nextInt(1, 101))
                .moveSlider("6", Methods.RNG.nextInt(1, 101));
    }

    @Test(description = "HTML alert messages")
    @Description("Check various buttons which display alert messages")
    public void alerts() {
        openSelEasy()
                .menu().openBootstrapAlertsPage()
                .clickAlertAndWait(3, "Autocloseable warning message")
                .clickAlertAndClose(8, "Normal info message");
    }

    @Test(description = "JS alert messages")
    @Description("Display and dismiss alert pop-up")
    public void alertClose() {
        openSelEasy()
                .menu().openJavascriptAlertsPage()
                .showAndConfirmAlert();
    }
}

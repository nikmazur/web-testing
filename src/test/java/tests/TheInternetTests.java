package tests;

import helpers.Methods;
import io.qameta.allure.Description;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "TheInternet")
public class TheInternetTests extends Methods {

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
                .selectOption(1)
                .checkValue("Option 1")
                .selectOption(2)
                .checkValue("Option 2");
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

    //Download a txt file from a page to location dir.
    //Requires to be logged in, so the test depends on the login test.
    @Test
    public void testFileDL() {
        openTheInternet()
                .download();
//        Methods.driver.get("https://the-internet.herokuapp.com/download_secure");
//        //Find the download link by text, click on it
//        Methods.driver.findElement(By.partialLinkText("some-file.txt")).click();
//
//        //Assert file is present in the project directory (exists method)
//        File doc = new File(Methods.projPath + "\\bin\\download\\some-file.txt");
//        screen("testFileDL");
//        assert(doc.exists());
//        //Delete the downloaded file (allows to re-run the test again)
//        doc.delete();
    }

    //Slider element test.
    //We move the slider by keyboard arrow presses and check the resulting values.
//    @Test
//    public void testSliderVal() {
//        Methods.driver.get("https://the-internet.herokuapp.com/horizontal_slider");
//
//        //Locate the slider element and the value field
//        WebElement slider = Methods.driver.findElement(By.xpath("/html/body/div[2]/div/div/div/input"));
//        WebElement value = Methods.driver.findElement(By.id("range"));
//
//        //Assert that initial value is 0
//        assertEquals(value.getText(), "0");
//
//        //Move right, assert new value. Each move adds 0.5.
//        slider.sendKeys(Keys.ARROW_RIGHT);
//        assertEquals(value.getText(), "0.5");
//
//        //Move right x2, assert new value
//        slider.sendKeys(Keys.ARROW_RIGHT);
//        slider.sendKeys(Keys.ARROW_RIGHT);
//        screen("testSliderVal");
//        assertEquals(value.getText(), "1.5");
//    }

    //Testing large table with data (50 x 50 cells) by counting rows and columns, and selecting 10 random cells
//    @Test
//    public void testTable() {
//        Methods.driver.get("https://the-internet.herokuapp.com/large");
//
//        //Retrieve rows and columns to lists, assert size
//        List<WebElement> columns = Methods.driver.findElements(By.cssSelector("#large-table th"));
//        List<WebElement> rows = Methods.driver.findElements(By.cssSelector("#large-table tr"));
//        assertEquals(columns.size(), 50);
//        //Size + 1 for rows, the 51st row is a header with titles
//        assertEquals(rows.size(), 51);
//        screen("testTable");
//
//        /* Locate 10 different cells using random numbers, assert that the cell text is equal to numbers each time.
//         * For each step we will be using a 'soft assert', which in case of an exception
//         * Instead of stopping will resume to the next assertion.*/
//        SoftAssert sAssert = new SoftAssert();
//        for(int i = 0; i < 10; i++) {
//            //Generating 2 random numbers between 1 - 50
//            final int RROW = RandomUtils.nextInt(1, 51);
//            final int RCOL = RandomUtils.nextInt(1, 51);
//
//            WebElement rCell = Methods.driver.findElement(By.cssSelector
//                    (".row-" + RROW + " > td:nth-child(" + RCOL + ")"));
//            sAssert.assertEquals(rCell.getText(), RROW + "." + RCOL);
//        }
//        //This method in soft assert will throw an exception if it was caught in any of the asserts previously
//        sAssert.assertAll();
//    }
}

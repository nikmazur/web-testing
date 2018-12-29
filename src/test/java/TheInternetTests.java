import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import static org.testng.Assert.assertEquals;

@Test
public class TheInternetTests extends Methods {

    /* Test login page with the text fields in an alert popup.
     * This test presents an interesting case when we have a separate popup JavaScript window where
     * we have to navigate to 2 separate fields and fill them with data. In this case I used a Robot
     * for navigation and the system clipboard for storing & pasting data to the 2nd field.*/
    @Test
    public void testLogin() {
        //Open required page
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        //Switch to alert with input fields
        Alert alert = driver.switchTo().alert();
        final String ADM = "admin";

        //Fill in the login, press Tab to cycle to 2nd field
        alert.sendKeys(ADM);
        rob.keyPress(KeyEvent.VK_TAB);

        //We take the string with our password (same as login) and place it in the system clipboard
        StringSelection stringSelection = new StringSelection(ADM);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        //Make the robot press Ctrl + V to Paste the string and submit
        rob.keyPress(KeyEvent.VK_CONTROL);
        rob.keyPress(KeyEvent.VK_V);
        rob.keyRelease(KeyEvent.VK_V);
        rob.keyRelease(KeyEvent.VK_CONTROL);
        alert.accept();

        //Wait until the greeting text is visible, screenshot & assert
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));
        WebElement text = driver.findElement(By.id("content"));
        screen("testLogin");
        final String EXP = "Basic Auth\n" +
                "Congratulations! You must have the proper credentials.";
        assertEquals(text.getText(), EXP);
    }

    //Checkbox test. Switch state & assert
    public void testCheckboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        //Locate checkboxes by xpath
        WebElement check1 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        WebElement check2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));

        //Click both
        check1.click();
        check2.click();

        //Check their new state
        screen("testCheckboxes");
        assert(check1.isSelected());
        assert(!check2.isSelected());
    }

    //Selecting an option from a drop-down list
    public void testList() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        //Create list object, select 2nd option
        Select list = new Select(driver.findElement(By.id("dropdown")));
        list.selectByIndex(2);

        //Get the currently selected option, check it's text value
        WebElement option = list.getFirstSelectedOption();
        screen("testList");
        assertEquals(option.getText(), "Option 2");
    }

    //Test for checking keyboard presses.
    //The page prints the last pressed key on the keyboard.
    public void testKeyboard() {
        driver.get("https://the-internet.herokuapp.com/key_presses");

        //Press Pause, retrieve text and check
        rob.keyPress(KeyEvent.VK_PAUSE);
        WebElement res = driver.findElement(By.id("result"));
        assertEquals(res.getText(),"You entered: PAUSE");

        //Same, but with NumPad6 key
        rob.keyPress(KeyEvent.VK_NUMPAD6);
        screen("testKeyboard");
        assertEquals(res.getText(), "You entered: NUMPAD6");
    }

    //Test for handling multiple browser windows
    public void testNewWind() {
        driver.get("https://the-internet.herokuapp.com/windows");

        //Click on the link for opening a new window, allow 0.5s for it to open
        driver.findElement(By.xpath("/html/body/div[2]/div/div/a")).click();

        //Wait until new window is opened (expecting 2 windows)
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        //Save the handle of the main window before switching (we will need this to return)
        final String MAINWIND = driver.getWindowHandle();

        //Cycle through open windows in order to switch to the 2nd (new) one
        for(String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);

        //Check title and close 2nd window
        assertEquals(driver.getTitle(), "New Window");
        screen("testNewWind");
        driver.close();

        //Switch back to main using saved handle
        driver.switchTo().window(MAINWIND);
    }

    //Download a txt file from a page to location dir.
    //Requires to be logged in, so the test depends on the login test.
    @Test (dependsOnMethods = "testLogin")
    public void testFileDL() {
        driver.get("https://the-internet.herokuapp.com/download_secure");
        //Find the download link by text, click on it
        driver.findElement(By.partialLinkText("some-file.txt")).click();

        //Assert file is present in the project directory (exists method)
        File doc = new File(projPath + "\\bin\\download\\some-file.txt");
        screen("testFileDL");
        assert(doc.exists());
        //Delete the downloaded file (allows to re-run the test again)
        doc.delete();
    }

    //Slider element test.
    //We move the slider by keyboard arrow presses and check the resulting values.
    public void testSliderVal() {
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        //Locate the slider element and the value field
        WebElement slider = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/input"));
        WebElement value = driver.findElement(By.id("range"));

        //Assert that initial value is 0
        assertEquals(value.getText(), "0");

        //Move right, assert new value. Each move adds 0.5.
        slider.sendKeys(Keys.ARROW_RIGHT);
        assertEquals(value.getText(), "0.5");

        //Move right x2, assert new value
        slider.sendKeys(Keys.ARROW_RIGHT);
        slider.sendKeys(Keys.ARROW_RIGHT);
        screen("testSliderVal");
        assertEquals(value.getText(), "1.5");
    }

    //Testing large table with data (50 x 50 cells) by counting rows and columns, and selecting 10 random cells
    public void testTable() {
        driver.get("https://the-internet.herokuapp.com/large");

        //Retrieve rows and columns to lists, assert size
        List<WebElement> columns = driver.findElements(By.cssSelector("#large-table th"));
        List<WebElement> rows = driver.findElements(By.cssSelector("#large-table tr"));
        assertEquals(columns.size(), 50);
        //Size + 1 for rows, the 51st row is a header with titles
        assertEquals(rows.size(), 51);
        screen("testTable");

        /* Locate 10 different cells using random numbers, assert that the cell text is equal to numbers each time.
         * For each step we will be using a 'soft assert', which in case of an exception
         * Instead of stopping will resume to the next assertion.*/
        SoftAssert sAssert = new SoftAssert();
        for(int i = 0; i < 10; i++) {
            //Generating 2 random numbers between 1 - 50
            final int RROW = RandomUtils.nextInt(1, 51);
            final int RCOL = RandomUtils.nextInt(1, 51);

            WebElement rCell = driver.findElement(By.cssSelector
                    (".row-" + RROW + " > td:nth-child(" + RCOL + ")"));
            sAssert.assertEquals(rCell.getText(), RROW + "." + RCOL);
        }
        //This method in soft assert will throw an exception if it was caught in any of the asserts previously
        sAssert.assertAll();
    }
}

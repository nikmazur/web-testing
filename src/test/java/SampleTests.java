import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;

public class SampleTests extends Methods {

    /*Test login page with the text fields in an alert popup.
    This test presents an interesting case when we have a separate popup JavaScript window where
    we have to navigate to 2 separate fields and fill them with data. In this case I used a Robot
    for navigation and the system clipboard for storing & pasting data to the 2nd field.*/
    @Test
    public void login() {
        //Open required page
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        //Switch to alert with input fields
        Alert alert = driver.switchTo().alert();
        final String adm = "admin";

        //Fill in the login, press Tab to cycle to 2nd field
        alert.sendKeys(adm);
        rob.keyPress(KeyEvent.VK_TAB);

        //We take the string with out password and place it in the system clipboard
        StringSelection stringSelection = new StringSelection(adm);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        //Make the robot press Ctrl + V to Paste the string
        rob.keyPress(KeyEvent.VK_CONTROL);
        rob.keyPress(KeyEvent.VK_V);
        rob.keyRelease(KeyEvent.VK_V);
        rob.keyRelease(KeyEvent.VK_CONTROL);

        //Take screenshot and submit
        screen("login");
        alert.accept();
    }

    //Checkbox test. Switch state & assert
    @Test
    public void checkboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        //Locate checkboxes by xpath
        WebElement check1 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        WebElement check2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));

        //Click both
        check1.click();
        check2.click();

        //Check their new state
        screen("checkboxes");
        assert(check1.isSelected());
        assert(!check2.isSelected());
    }

    //Selecting an option from a drop-down list
    @Test
    public void selectFromList() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        //Create list object, select 2nd option
        Select list = new Select(driver.findElement(By.id("dropdown")));
        list.selectByIndex(2);

        //Get the currently selected option, check it's text value
        WebElement option = list.getFirstSelectedOption();
        screen("selectFromList");
        assertEquals("Option 2", option.getText());
    }

    //Test for checking keyboard presses.
    //The page prints the last pressed key on the keyboard.
    @Test
    public void keyboardCheck() {
        driver.get("https://the-internet.herokuapp.com/key_presses");

        //Press Pause, retrieve text and check
        rob.keyPress(KeyEvent.VK_PAUSE);
        WebElement res = driver.findElement(By.id("result"));
        assertEquals("You entered: PAUSE", res.getText());

        //Same, but with NumPad6 key
        rob.keyPress(KeyEvent.VK_NUMPAD6);
        screen("keyboardCheck");
        assertEquals("You entered: NUMPAD6", res.getText());
    }

    //Test for handling multiple browser windows.
    @Test
    public void newWind() {
        driver.get("https://the-internet.herokuapp.com/windows");

        //Click on the link for opening a new window, allow 0.5s for it to open
        clickXpath("/html/body/div[2]/div/div/a");
        sleep(500);

        //Save the handle of the main window before switching (we will need this to return)
        final String mainWind = driver.getWindowHandle();

        //Cycle through open windows in order to switch to the 2nd (new) one
        for(String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);

        //Check title and close 2nd window
        assertEquals("New Window", driver.getTitle());
        screen("newWind");
        driver.close();

        //Swithc back to main using the handle
        driver.switchTo().window(mainWind);
    }

    //Download a txt file from a page to location dir.
    //Requires to be logged in, so the test depends on the login test.
    @Test (dependsOnMethods = "login")
    public void fileDL() {
        driver.get("https://the-internet.herokuapp.com/download_secure");
        //Find the download link by text, click on it
        driver.findElement(By.partialLinkText("some-file.txt")).click();

        //Assert file is present in the project directory (exists method), then delete it
        File doc = new File(projPath + "\\bin\\download\\some-file.txt");
        screen("fileDL");
        assert(doc.exists());
        doc.delete();
    }

    //Slider element test.
    //We move the slider by keyboard arrow presses and check the resulting values.
    @Test
    public void sliderVal() {
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        //Locate the slider element and the value field
        WebElement slider = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/input"));
        WebElement value = driver.findElement(By.id("range"));

        //Assert that initial value is 0
        assertEquals("0", value.getText());

        //Move right, assert new value
        slider.sendKeys(Keys.ARROW_RIGHT);
        sleep(200);
        assertEquals("0.5", value.getText());

        //Move right x2, assert new value
        slider.sendKeys(Keys.ARROW_RIGHT);
        slider.sendKeys(Keys.ARROW_RIGHT);
        sleep(200);
        screen("sliderVal");
        assertEquals("1.5", value.getText());
    }

    //Testing large table with data (50 x 50) by counting rows and columns, and selecting a random cell
    @Test
    public void dataTable() {
        driver.get("https://the-internet.herokuapp.com/large");

        //Retrieve rows and columns to lists, assert size
        List<WebElement> columns = driver.findElements(By.cssSelector("#large-table th"));
        List<WebElement> rows = driver.findElements(By.cssSelector("#large-table tr"));
        assertEquals(50, columns.size());
        //The 51st row is a header with titles
        assertEquals(51, rows.size());

        //Randomly generate 2 numbers between 1 - 50
        final int rRow = ThreadLocalRandom.current().nextInt(1, 51);
        final int rCol = ThreadLocalRandom.current().nextInt(1, 51);

        //Locate random cell, assert that it's text is equal
        WebElement rCell = driver.findElement(By.cssSelector
                (".row-" + rRow + " > td:nth-child(" + rCol + ")"));
        screen("dataTable");
        assertEquals(rRow + "." + rCol, rCell.getText());
    }
}

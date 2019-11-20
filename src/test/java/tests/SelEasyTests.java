package tests;

import helpers.Methods;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;

@Test(groups = "SelEasy")
public class SelEasyTests extends Methods {

    //Check simple input field with random text, which prints it back
    @Test
    public void inputText() {
        //Open URL
        Methods.driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

        //Locate input field
        WebElement input = Methods.driver.findElement(By.id("user-message"));
        //Generate random text string (8 chars, letters & numbers)
        final String TEXT = RandomStringUtils.randomAlphanumeric(8);
        //Send text to the field
        input.sendKeys(TEXT);

        //Hit submit
        Methods.driver.findElement(By.cssSelector("#get-input > button")).click();
        //Locate the element which prints the submitted text
        WebElement result = Methods.driver.findElement(By.cssSelector("#display"));
        //Take screenshot
        screen("inputText");
        //Check result
        assertEquals(result.getText(), TEXT);
    }

    //Check input form by submitting random text
    @Test
    public void inputForm() {
        Methods.driver.get("https://www.seleniumeasy.com/test/ajax-form-submit-demo.html");

        //Locate name field, send random text
        WebElement name = Methods.driver.findElement(By.id("title"));
        final String RNAME = RandomStringUtils.randomAlphanumeric(10);
        name.sendKeys(RNAME);

        //Locate comment field, send long random text (300 chars)
        WebElement comm = Methods.driver.findElement(By.id("description"));
        final String RCOMM = RandomStringUtils.randomAlphanumeric(300);
        comm.sendKeys(RCOMM);

        //Hit submit
        Methods.driver.findElement(By.id("btn-submit")).click();

        //Wait until the form is submitted (success message will be displayed)
        Methods.wait.until(ExpectedConditions.textToBe(By.id("submit-control"), "Form submited Successfully!"));
        screen("inputForm");
    }

    //Check the calendar widget by selecting a random date from the last 3 years
    @Test
    public void randCalendarDate() {
        Methods.driver.get("https://www.seleniumeasy.com/test/bootstrap-date-picker-demo.html");

        //Generate random number of days to go back (from 1 to 1095)
        final int DAYS = RandomUtils.nextInt(1, 1096);
        //Set date format
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Form the final date by using the Java Date lib
        final String DATE = form.format(LocalDate.now().minusDays(DAYS));

        //Submit the generated date
        WebElement selDate = Methods.driver.findElement(By.className("form-control"));
        selDate.sendKeys(DATE);
        selDate.sendKeys(Keys.ENTER);

        /* https://stackoverflow.com/questions/7852287/using-selenium-web-driver-to-retrieve-value-of-a-html-input
         * Interesting case where the text is stored as a web element attribute.
         * Retrieve the attribute to a string, and then check for match.*/
        String nDate = selDate.getAttribute("value");
        screen("randCalendarDate");
        assertEquals(nDate, DATE);
    }

    //Check the search function in a table
    @Test
    public void filterTable() {
        Methods.driver.get("https://www.seleniumeasy.com/test/table-search-filter-demo.html");

        //Send the text match we are looking for (Assignee: John)
        WebElement input = Methods.driver.findElement(By.id("task-table-filter"));
        input.sendKeys("John");

        //Retrieve the table elements to a list
        List<WebElement> rows = Methods.driver.findElements(By.cssSelector("#task-table > tbody > tr"));

        /* Filtered rows are still listed in HTML code, and therefore are present in the list.
         * In order to check the search results we count the rows where the style attribute is empty (row is shown).
         * Rows with 'display none' style attribute are hidden from view.*/
        int count = 0;
        //Iterate through rows, increase counter on each match
        for(WebElement row : rows) {
            if(!row.getAttribute("style").contains("display: none;"))
                count++;
        }

        screen("filterTable");
        //There are 2 Assignees with 'John' as part of their names
        assertEquals(count, 2);
    }

    //Initiate the fake download process (takes about 12 seconds)
    @Test
    public void progressMsg() {
        Methods.driver.get("https://www.seleniumeasy.com/test/jquery-download-progress-bar-demo.html");

        //Hit download
        Methods.driver.findElement(By.id("downloadButton")).click();

        //Wait until the 'Complete!' dialog is shown
        Methods.wait.until(ExpectedConditions.textToBe(By.id("dialog"), "Complete!"));
        screen("progressMsg");
    }

    //Check generated file download.
    //Page allows to generate a txt file with the entered text & download it.
    @Test
    public void generateFileDL() throws FileNotFoundException {
        Methods.driver.get("https://www.seleniumeasy.com/test/generate-file-to-download-demo.html");

        //Locate text box, send random string (500 char max)
        WebElement textBox = Methods.driver.findElement(By.id("textbox"));
        final String TEXT = RandomStringUtils.randomAlphanumeric(500);
        textBox.sendKeys(TEXT);

        //Hit create & download
        Methods.driver.findElement(By.id("create")).click();
        Methods.driver.findElement(By.id("link-to-download")).click();
        screen("generateFileDL");

        //Set up file object in the directory it was downloaded to
        File doc = new File(Methods.projPath + "\\bin\\download\\easyinfo.txt");
        //Check if file exists
        if(doc.exists()) {
            //Create scanner to read contents of the downloaded file
            Scanner sc = new Scanner(doc);

            //Check that file contents match the generated string
            assertEquals(sc.nextLine(), TEXT);

            //Close scanner & delete the downloaded file (allows to re-run the test again)
            sc.close();
            doc.delete();
        //Fail test if file does not exist
        } else
            Assert.fail("File easyinfo.txt not found!");
    }

    //Check 3 different slider widgets by moving them a random number of times in random direction.
    //This test uses a data provider 'getSliders' (listed next) and will run 3 times.
    @Test (dataProvider = "getSliders")
    //ADDR and VALUE are passed from the data provider on each run
    public void moveSlider(final String ADDR, final String VALUE) {
        Methods.driver.get("https://www.seleniumeasy.com/test/drag-drop-range-sliders-demo.html");

        //Locate the slider
        WebElement slider = Methods.driver.findElement(By.cssSelector(ADDR));
        //Retrieve current slider counter and convert it from string to int
        int counter = Integer.parseInt((Methods.driver.findElement(By.cssSelector(VALUE))).getText());
        //Retrieve min and max slider values for establishing range
        final int MIN = Integer.parseInt(slider.getAttribute("min"));
        final int MAX = Integer.parseInt(slider.getAttribute("max"));

        int offset = counter;
        while(counter == offset)
            //Select random number within the range which will be the new value.
            //We take MAX + 1 because in RandomUtils the lower bound is inclusive, and upper is exclusive.
            offset = RandomUtils.nextInt(MIN, MAX + 1);

        //Move in a random direction based on counter and offset comparison
        //(Using Math.abs to get the absolute difference)
        final int DIFF = Math.abs(offset - counter);
        for(int i = 0; i < DIFF; i++) {
            if (offset > counter) {
                //If counter is lower, move slider right by pressing Right Arrow key and increase counter
                slider.sendKeys(Keys.ARROW_RIGHT);
                counter++;
            }   else {
                //If higher, the same but reverse (Left Arrow key, decrease counter)
                slider.sendKeys(Keys.ARROW_LEFT);
                counter--;
            }
        }

        //Retrieve the new counter value after moving
        int nCounter = Integer.parseInt((Methods.driver.findElement(By.cssSelector(VALUE))).getText());
        screen("moveSlider");

        //Compare current and expected counters
        assertEquals(nCounter, counter);
    }

    //Data provider for the moveSlider test.
    //Passes the location of the sliders and counters to the test, so it is ran multiple times.
    @DataProvider
    public Object[][] getSliders()
    {
        //Object for holding the data
        Object[][] data = new Object[3][2];

        //CssSelectors of sliders and counters.
        //Data contains 3 rows and 2 columns, so the test will be ran 3 times with 2 values on each run
        data[0][0] = "#slider1 > div:nth-child(2) > input:nth-child(1)";
        data[0][1] = "#range";
        data[1][0] = ".range-success > input:nth-child(1)";
        data[1][1] = "#rangeSuccess";
        data[2][0] = ".range-warning > input:nth-child(1)";
        data[2][1] = "#rangeWarning";

        return data;
    }

    //Check message which is shown by button press and disappears after 3 seconds
    @Test
    public void autoCloseMsg() {
        Methods.driver.get("https://www.seleniumeasy.com/test/bootstrap-alert-messages-demo.html");

        //Hit button to show message
        Methods.driver.findElement(By.id("autoclosable-btn-warning")).click();

        //Wait until message disappears (element 'style' attribute will be changed to 'display: none;'
        Methods.wait.until(ExpectedConditions.attributeToBe(
                By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]"), "style", "display: none;"));
        screen("autoCloseMsg");
    }

    //Check Java Script alert box, where input text is displayed on the main page
    @Test
    public void textFromAlert() {
        Methods.driver.get("https://www.seleniumeasy.com/test/javascript-alert-box-demo.html");

        //Locate & open alert box
        Methods.driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[3]/div[2]/button")).click();

        //Generate random text to send
        final String TEXT = RandomStringUtils.randomAlphanumeric(8);

        //Switch from main window to alert, send text, hit Accept
        Alert alert = Methods.driver.switchTo().alert();
        alert.sendKeys(TEXT);
        alert.accept();

        //Locate the line where the entered text is shown, check for match
        WebElement rText = Methods.driver.findElement(By.id("prompt-demo"));
        screen("textFromAlert");
        assertEquals(rText.getText(), "You have entered '" + TEXT + "' !");
    }

    //Negative tests
    //Access a non-existing page (part of the URL is randomly generated text)
    @Test
    public void negPage404() {
        //Set URL + append random text
        Methods.driver.get("https://www.seleniumeasy.com/test/" + RandomStringUtils.randomAlphanumeric(10));
        screen("negPage404");

        //Check page title (should be 'Page not found')
        Assert.assertEquals(Methods.driver.getTitle(), "Page not found | Selenium Easy");
    }

    //Check two fields where you can write numbers and sum them, but enter letters instead
    @Test
    public void negAddFields() {
        Methods.driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

        //Locate 2 input fields
        WebElement field1 = Methods.driver.findElement(By.id("sum1"));
        WebElement field2 = Methods.driver.findElement(By.id("sum2"));

        //Send random alphabetic letter to each
        field1.sendKeys(RandomStringUtils.randomAlphabetic(1));
        field2.sendKeys(RandomStringUtils.randomAlphabetic(1));

        //Hit 'Get Total' button
        Methods.driver.findElement(By.cssSelector("#gettotal > button")).click();

        //Locate line with result
        WebElement res = Methods.driver.findElement(By.id("displayvalue"));
        screen("negAddFields");

        //Check for NaN (Not A Number) message
        assertEquals(res.getText(), "NaN");
    }

    //Same as 'inputText' test, but send random Chinese words. Will work fine since the website supports this.
    @Test
    public void negChineseText() {
        Methods.driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

        //Got the Chinese text from here:
        //http://generator.lorem-ipsum.info/_chinese
        WebElement input = Methods.driver.findElement(By.id("user-message"));
//        input.sendKeys(TEXT);

        Methods.driver.findElement(By.cssSelector("#get-input > button")).click();
        WebElement result = Methods.driver.findElement(By.cssSelector("#display"));
        screen("negChineseText");
//        assertEquals(result.getText(), TEXT);
    }

}

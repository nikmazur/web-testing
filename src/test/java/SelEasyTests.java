import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;

public class SelEasyTests extends Methods {

    //Check simple input field with random text, which prints it back
    @Test
    public void inputText() {
        //Open URL
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

        //Locate input field
        WebElement input = driver.findElement(By.id("user-message"));
        //Generate random text string (8 chars, letters & numbers)
        final String TEXT = RandomStringUtils.randomAlphanumeric(8);
        //Send text to the field
        input.sendKeys(TEXT);

        //Hit submit
        driver.findElement(By.cssSelector("#get-input > button")).click();
        //Locate the element which prints the submitted text
        WebElement result = driver.findElement(By.cssSelector("#display"));
        //Take screenshot
        screen("inputText");
        //Check result
        assertEquals(result.getText(), TEXT);
    }

    //Check input form by submitting random text
    @Test
    public void inputForm() {
        driver.get("https://www.seleniumeasy.com/test/ajax-form-submit-demo.html");

        //Locate name field, send random text
        WebElement name = driver.findElement(By.id("title"));
        final String RNAME = RandomStringUtils.randomAlphanumeric(10);
        name.sendKeys(RNAME);

        //Locate comment field, send long random text (300 chars)
        WebElement comm = driver.findElement(By.id("description"));
        final String RCOMM = RandomStringUtils.randomAlphanumeric(300);
        comm.sendKeys(RCOMM);

        //Hit submit
        driver.findElement(By.id("btn-submit")).click();

        //Wait until the form is submitted (success message will be displayed)
        wait.until(ExpectedConditions.textToBe(By.id("submit-control"), "Form submited Successfully!"));
        screen("inputForm");
    }

    //Check the calendar widget by selecting a random date from the last 3 years
    @Test
    public void randCalendarDate() {
        driver.get("https://www.seleniumeasy.com/test/bootstrap-date-picker-demo.html");

        //Generate random number of days to go back (from 1 to 1095)
        final int DAYS = RandomUtils.nextInt(1, 1096);
        //Set date format
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Form the final date by using the Java Date lib
        final String DATE = form.format(LocalDate.now().minusDays(DAYS));

        //Submit the generated date
        WebElement selDate = driver.findElement(By.className("form-control"));
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
        driver.get("https://www.seleniumeasy.com/test/table-search-filter-demo.html");

        //Send the text match we are looking for (Assignee: John)
        WebElement input = driver.findElement(By.id("task-table-filter"));
        input.sendKeys("John");

        //Retrieve the table elements to a list
        List<WebElement> rows = driver.findElements(By.cssSelector("#task-table > tbody > tr"));

        /* Filtered rows are still listed in HTML code, and therefore are present in the list.
         * In order to check the search results we count the rows where the style attribute is empty (row is shown).
         * Rows with 'display none' style attribute are hidden from view.*/
        int count = 0;
        //Iterate through rows, increase counter on each match
        for(WebElement row : rows) {
            if(row.getAttribute("style").equals(""))
                count++;
        }

        screen("filterTable");
        //There are 2 Assignees with 'John' as part of their names
        assertEquals(count, 2);
    }

    //Initiate the fake download process (takes about 12 seconds)
    @Test
    public void progressMsg() {
        driver.get("https://www.seleniumeasy.com/test/jquery-download-progress-bar-demo.html");

        //Hit download
        driver.findElement(By.id("downloadButton")).click();

        //Wait until the 'Complete!' dialog is shown
        wait.until(ExpectedConditions.textToBe(By.id("dialog"), "Complete!"));
        screen("progressMsg");
    }

    //Check generated file download.
    //Page allows to generate a txt file with the entered text & download it.
    @Test
    public void generateFileDL() throws FileNotFoundException {
        driver.get("https://www.seleniumeasy.com/test/generate-file-to-download-demo.html");

        //Locate text box, send random string (500 char max)
        WebElement textBox = driver.findElement(By.id("textbox"));
        final String TEXT = RandomStringUtils.randomAlphanumeric(500);
        textBox.sendKeys(TEXT);

        //Hit create & download
        driver.findElement(By.id("create")).click();
        driver.findElement(By.id("link-to-download")).click();
        screen("generateFileDL");

        //Set up file object in the directory it was downloaded to
        File doc = new File(projPath + "\\bin\\download\\easyinfo.txt");
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

    //Check one of the slider widgets by moving it a random number of times in one of the directions
    @Test
    public void moveSlider() {
        driver.get("https://www.seleniumeasy.com/test/drag-drop-range-sliders-demo.html");

        //Locate the slider
        WebElement slider = driver.findElement(By.cssSelector("#slider2 > div > input"));
        //Retrieve current slider counter and convert it from string to int
        int counter = Integer.parseInt((driver.findElement(By.cssSelector("#rangePrimary"))).getText());

        //Generate random number of steps (valid slider values are 1 - 100)
        final int OFFSET = RandomUtils.nextInt(1, 50);

        //Move random number of times in a random direction (random bool)
        if(RandomUtils.nextBoolean()) {
            //If true, move slider right by pressing Right Arrow key on each step and increasing counter
            for(int i = counter + OFFSET; counter < i; counter++)
                slider.sendKeys(Keys.ARROW_RIGHT);
        } else {
            //If false, the same but reverse (Left Arrow key, decrease counter)
            for(int i = counter - OFFSET; counter > i; counter--)
                slider.sendKeys(Keys.ARROW_LEFT);
        }

        //Retrieve the new counter value after moving
        int nCounter = Integer.parseInt((driver.findElement(By.cssSelector("#rangePrimary"))).getText());
        screen("moveSlider");

        //Compare current and expected counters
        assertEquals(nCounter, counter);
    }

    //Check message which is shown by button press and disappears after 3 seconds
    @Test
    public void autoCloseMsg() {
        driver.get("https://www.seleniumeasy.com/test/bootstrap-alert-messages-demo.html");

        //Hit button to show message
        driver.findElement(By.id("autoclosable-btn-warning")).click();

        //Wait until message disappears (element 'style' attribute will be changed to 'display: none;'
        wait.until(ExpectedConditions.attributeToBe(
                By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]"), "style", "display: none;"));
        screen("autoCloseMsg");
    }

    //Check Java Script alert box, where input text is displayed on the main page
    @Test
    public void textFromAlert() {
        driver.get("https://www.seleniumeasy.com/test/javascript-alert-box-demo.html");

        //Locate & open alert box
        driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[3]/div[2]/button")).click();

        //Generate random text to send
        final String TEXT = RandomStringUtils.randomAlphanumeric(8);

        //Switch from main window to alert, send text, hit Accept
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(TEXT);
        alert.accept();

        //Locate the line where the entered text is shown, check for match
        WebElement rText = driver.findElement(By.id("prompt-demo"));
        screen("textFromAlert");
        assertEquals(rText.getText(), "You have entered '" + TEXT + "' !");
    }

    //Negative tests
    //Access a non-existing page (part of the URL is randomly generated text)
    @Test
    public void negPage404() {
        //Set URL + append random text
        driver.get("https://www.seleniumeasy.com/test/" + RandomStringUtils.randomAlphanumeric(10));
        screen("negPage404");

        //Check page title (should be 'Page not found')
        assertEquals(driver.getTitle(), "Page not found | Selenium Easy");
    }

    //Check two fields where you can write numbers and sum them, but enter letters instead
    @Test
    public void negAddFields() {
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

        //Locate 2 input fields
        WebElement field1 = driver.findElement(By.id("sum1"));
        WebElement field2 = driver.findElement(By.id("sum2"));

        //Send random alphabetic letter to each
        field1.sendKeys(RandomStringUtils.randomAlphabetic(1));
        field2.sendKeys(RandomStringUtils.randomAlphabetic(1));

        //Hit 'Get Total' button
        driver.findElement(By.cssSelector("#gettotal > button")).click();

        //Locate line with result
        WebElement res = driver.findElement(By.id("displayvalue"));
        screen("negAddFields");

        //Check for NaN (Not A Number) message
        assertEquals(res.getText(), "NaN");
    }

    //Same as 'inputText' test, but send random Chinese words. Will work fine since the website supports this.
    @Test
    public void negChineseText() {
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");

        //Got the Chinese text from here:
        //http://generator.lorem-ipsum.info/_chinese
        WebElement input = driver.findElement(By.id("user-message"));
        final String TEXT = "記富力長参激下高副能穂開募年祉著橋。臨内約宿掲人供妊況出託寄向崩己。演変不県発属吹信浩藤計木可。";
        input.sendKeys(TEXT);

        driver.findElement(By.cssSelector("#get-input > button")).click();
        WebElement result = driver.findElement(By.cssSelector("#display"));
        screen("negChineseText");
        assertEquals(result.getText(), TEXT);
    }

}

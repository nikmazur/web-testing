package tests;

import helpers.Methods;
import helpers.RandomPerson;
import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;

@Test(groups = "SelEasy")
public class SelEasyTests extends Methods {

    @Test(description = "Text Input")
    @Description("Check simple input field with random text, which prints it back")
    public void inputText() {
        openSelEasy()
                .menu().openSimpleFormDemo()
                .inputTextCheckResult("Sample Message")
                .clearSingleInput()
                .inputTextCheckResult(RandomStringUtils.randomAlphanumeric(30));
    }

    @Test(description = "Fill Out Form")
    @Description("Fill out a sample form with contact details")
    public void fillForm() {
        openSelEasy()
                .menu().openInputFormSubmit()
                .fillInputForm(new RandomPerson(), true, "A very special project")
                .fillInputForm(new RandomPerson(), false, RandomStringUtils.randomAlphanumeric(50));
    }

    @Test(description = "Filter Table")
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

    @Test(description = "Move Slider")
    @Description("Check different slider widgets by moving them a random number of times in a random direction.")
    public void moveSlider() {
        openSelEasy()
                .menu().openSlidersPage()
                .moveSlider("1", 26)
                .moveSlider("2", RandomUtils.nextInt(1, 101))
                .moveSlider("6", RandomUtils.nextInt(1, 101));
    }

//    //Check message which is shown by button press and disappears after 3 seconds
//    @Test
//    public void autoCloseMsg() {
//        Methods.driver.get("https://www.seleniumeasy.com/test/bootstrap-alert-messages-demo.html");
//
//        //Hit button to show message
//        Methods.driver.findElement(By.id("autoclosable-btn-warning")).click();
//
//        //Wait until message disappears (element 'style' attribute will be changed to 'display: none;'
//        Methods.wait.until(ExpectedConditions.attributeToBe(
//                By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[3]"), "style", "display: none;"));
//        screen("autoCloseMsg");
//    }
//
//    //Check Java Script alert box, where input text is displayed on the main page
//    @Test
//    public void textFromAlert() {
//        Methods.driver.get("https://www.seleniumeasy.com/test/javascript-alert-box-demo.html");
//
//        //Locate & open alert box
//        Methods.driver.findElement(By.xpath("//*[@id=\"easycont\"]/div/div[2]/div[3]/div[2]/button")).click();
//
//        //Generate random text to send
//        final String TEXT = RandomStringUtils.randomAlphanumeric(8);
//
//        //Switch from main window to alert, send text, hit Accept
//        Alert alert = Methods.driver.switchTo().alert();
//        alert.sendKeys(TEXT);
//        alert.accept();
//
//        //Locate the line where the entered text is shown, check for match
//        WebElement rText = Methods.driver.findElement(By.id("prompt-demo"));
//        screen("textFromAlert");
//        assertEquals(rText.getText(), "You have entered '" + TEXT + "' !");
//    }
//
//    //Negative tests
//    //Access a non-existing page (part of the URL is randomly generated text)
//    @Test
//    public void negPage404() {
//        //Set URL + append random text
//        Methods.driver.get("https://www.seleniumeasy.com/test/" + RandomStringUtils.randomAlphanumeric(10));
//        screen("negPage404");
//
//        //Check page title (should be 'Page not found')
//        Assert.assertEquals(Methods.driver.getTitle(), "Page not found | Selenium Easy");
//    }
//
//    //Check two fields where you can write numbers and sum them, but enter letters instead
//    @Test
//    public void negAddFields() {
//        Methods.driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");
//
//        //Locate 2 input fields
//        WebElement field1 = Methods.driver.findElement(By.id("sum1"));
//        WebElement field2 = Methods.driver.findElement(By.id("sum2"));
//
//        //Send random alphabetic letter to each
//        field1.sendKeys(RandomStringUtils.randomAlphabetic(1));
//        field2.sendKeys(RandomStringUtils.randomAlphabetic(1));
//
//        //Hit 'Get Total' button
//        Methods.driver.findElement(By.cssSelector("#gettotal > button")).click();
//
//        //Locate line with result
//        WebElement res = Methods.driver.findElement(By.id("displayvalue"));
//        screen("negAddFields");
//
//        //Check for NaN (Not A Number) message
//        assertEquals(res.getText(), "NaN");
//    }
//
//    //Same as 'inputText' test, but send random Chinese words. Will work fine since the website supports this.
//    @Test
//    public void negChineseText() {
//        Methods.driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");
//
//        //Got the Chinese text from here:
//        //http://generator.lorem-ipsum.info/_chinese
//        WebElement input = Methods.driver.findElement(By.id("user-message"));
////        input.sendKeys(TEXT);
//
//        Methods.driver.findElement(By.cssSelector("#get-input > button")).click();
//        WebElement result = Methods.driver.findElement(By.cssSelector("#display"));
//        screen("negChineseText");
////        assertEquals(result.getText(), TEXT);
//    }

}

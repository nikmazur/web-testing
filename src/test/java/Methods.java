import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Methods {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String projPath;
    protected static Robot rob;

    /*This method is called every time any test is ran (based on the @BeforeSuite tag).
    It performs preliminary steps for setting up the webdriver and other objects.*/
    @BeforeSuite
    public void setWebdriver() {
        //This allows us to determine the path where the project directory is located in the file system.
        //Will be used later for saving files.
        File cDir = new File("");
        projPath = cDir.getAbsolutePath();

        //Setting up a profile for Firefox with custom options
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(setOptions());

        //Launch geckodriver, maximize window, configure wait object
        System.setProperty("webdriver.gecko.driver", "bin/geckodriver.exe");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        wait=new WebDriverWait(driver, 10);

        //Java robot for emulating keypresses from the keyboard
        try {
            rob = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void clickXpath(String path) {
        WebElement elem = driver.findElement(By.xpath(path));
        elem.click();
    }

    public static void enterXpath(String input, String path) {
        WebElement elem = driver.findElement(By.xpath(path));
        elem.clear();
        elem.sendKeys(input);
    }

    //Manual pauses with the duration in ms passed as parameter
    public static void sleep(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Profile for Firefox with settings which are needed for downloading files.
    //Sets Firefox to download to a specified dir without any prompts.
    private FirefoxProfile setOptions() {
        FirefoxProfile prof = new FirefoxProfile();
        prof.setPreference("browser.helperApps.neverAsk.saveToDisk" , "application/octet-stream;");
        prof.setPreference("browser.helperApps.alwaysAsk.force", false);
        prof.setPreference("browser.download.manager.showWhenStarting",false);
        prof.setPreference("browser.download.folderList", 2);
        prof.setPreference("browser.download.dir", projPath + "\\bin\\download");
        return prof;
    }

    //For taking and saving screenshots to project dir at the end of each test.
    //Takes the name of the test as an argument.
    public void screen(final String tName) {
        try {
            //Timestamp with the current date & time
            final String time = new SimpleDateFormat("ddMMyy_HHmmss_").format(new Date());
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            //Append timestamp & test name to file name, save it
            FileUtils.copyFile(scrFile,
                    new File(projPath + "\\bin\\screenshots\\" + time + tName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Generates random 8 char passwords
    public String randPass() {
        byte[] array = new byte[8];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    //Called after all tests are ran.
    //Closes the Firefox browser window and quits the webdriver
    @AfterSuite
    public void driverQuit() {
//        driver.quit();
    }
}

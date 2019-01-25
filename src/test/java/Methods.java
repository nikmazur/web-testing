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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Methods {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String projPath;
    protected static Robot rob;

    //This method is called every time any test is ran (based on the @BeforeSuite tag).
    //It performs preliminary steps for setting up the webdriver and other objects.
    @BeforeSuite
    public void setWebdriver() throws AWTException {
        //This allows us to determine the path where the project directory is located in the file system.
        //Required for Firefox profile settings (download location).
        File cDir = new File("");
        projPath = cDir.getAbsolutePath();

        //Setting up a profile for Firefox with custom options
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(setOptions());

        //Launch geckodriver, maximize window, configure wait object
        System.setProperty("webdriver.gecko.driver", projPath + "\\bin\\geckodriver.exe");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        wait=new WebDriverWait(driver, 20);

        //Java robot for emulating key presses from the keyboard
        rob = new Robot();
    }

    //Profile for Firefox with settings which are needed for downloading files.
    //Sets Firefox to download to a specified directory without prompts.
    private FirefoxProfile setOptions() {
        FirefoxProfile prof = new FirefoxProfile();
        prof.setPreference("browser.helperApps.neverAsk.saveToDisk" , "application/octet-stream;text/plain");
        prof.setPreference("browser.helperApps.alwaysAsk.force", false);
        prof.setPreference("browser.download.manager.showWhenStarting",false);
        prof.setPreference("browser.download.folderList", 2);
        prof.setPreference("browser.download.dir", projPath + "\\bin\\download");
        return prof;
    }

    //For taking and saving screenshots to project directory after each test.
    //Takes the name of the test as an argument.
    public void screen(final String TNAME) {
        try {
            //Timestamp with the current date & time
            final String TIME = new SimpleDateFormat("ddMMyy_HHmmss_").format(new Date());
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            //Append timestamp & test name to file name, save file
            FileUtils.moveFile(scrFile, new File(projPath + "\\bin\\screenshots\\" + TIME + TNAME + ".jpg"));
        } catch (IOException e) {
            System.out.println("Could not create screenshot file for test: " + TNAME);
        }
    }

    //Called after all tests are ran. Quits webdriver
    @AfterSuite
    public void driverQuit() {
        driver.quit();
    }
}

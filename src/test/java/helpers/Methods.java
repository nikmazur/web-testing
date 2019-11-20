package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import pages.TheInternet.MainPage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;
import static com.codeborne.selenide.WebDriverRunner.source;
import static com.codeborne.selenide.WebDriverRunner.url;

public class Methods {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static String projPath;
    public static Robot rob;

    //This method is called every time any test is ran (based on the @BeforeSuite tag).
    //It performs preliminary steps for setting up the webdriver and other objects.
//    @BeforeSuite(alwaysRun = true)
//    public void setWebdriver() throws AWTException {
//        //This allows us to determine the path where the project directory is located in the file system.
//        //Required for Firefox profile settings (download location).
//        File cDir = new File("");
//        projPath = cDir.getAbsolutePath();
//
//        //Setting up a profile for Firefox with custom options
//        FirefoxOptions options = new FirefoxOptions();
//        options.setProfile(setOptions());
//
//        //Launch geckodriver, maximize window, configure wait object
//        System.setProperty("webdriver.gecko.driver", projPath + "\\bin\\geckodriver.exe");
//        driver = new FirefoxDriver(options);
//        driver.manage().window().maximize();
//        wait=new WebDriverWait(driver, 20);
//
//        //Java robot for emulating key presses from the keyboard
//        rob = new Robot();
//    }

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

    @Step("Open TheInternet main page")
    public static MainPage openTheInternet() {
        Selenide.open("https://the-internet.herokuapp.com");
        return new MainPage();
    }

    @AfterMethod(alwaysRun = true, description = "Save page screenshot and HTML")
    public void saveScreenAndHTML() {
        screenshot("Screen " + url());
        HTML();
    }

    @Attachment(value = "{name}", type = "image/png")
    public byte[] screenshot(String name) {
        File screen = takeScreenShotAsFile();
        byte[] buffer = new byte[(int) screen.length()];
        InputStream is;
        try {
            is = new FileInputStream(screen);
            is.read(buffer);
            is.close();
        } catch (IOException ignored) {
        }
        return buffer;
    }

    @Attachment(type = "text/html")
    public byte[] HTML() {
        return source().getBytes(StandardCharsets.UTF_8);
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
//        driver.quit();
    }
}

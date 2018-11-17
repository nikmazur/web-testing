import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Methods {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static String projPath;

    @BeforeSuite
    public void setWebdriver() {
        File currentDir = new File("");
        projPath = currentDir.getAbsolutePath();

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(setOptions());

        System.setProperty("webdriver.gecko.driver", "bin/geckodriver.exe");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        wait=new WebDriverWait(driver, 5);
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

    public static void sleep(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public FirefoxProfile setOptions() {
        FirefoxProfile prof = new FirefoxProfile();
        prof.setPreference("browser.helperApps.neverAsk.saveToDisk" , "application/octet-stream;");
        prof.setPreference("browser.helperApps.alwaysAsk.force", false);
        prof.setPreference("browser.download.manager.showWhenStarting",false);
        prof.setPreference("browser.download.folderList", 2);
        prof.setPreference("browser.download.dir", projPath + "\\bin\\download");
        return prof;
    }

    public void screen(final String tName) {
        try {
            String time = new SimpleDateFormat("ddMMyy_HHmmss_").format(new Date());
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile,
                    new File(projPath + "\\bin\\screenshots\\" + time + tName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void driverQuit() {
//        driver.quit();
    }
}

package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;
import static com.codeborne.selenide.WebDriverRunner.source;
import static com.codeborne.selenide.WebDriverRunner.url;

public class Methods {

    final Properties PROP = ConfigFactory.create(Properties.class);
    final static String PROJ_PATH = new File("").getAbsolutePath();
    final static String S = File.separator;

    @BeforeMethod(alwaysRun = true, description = "Browser Setup")
    public void setupBrowser(Method method) throws MalformedURLException  {
        Allure.addAttachment("Remote", String.valueOf(PROP.remote()));

        if(!PROP.remote()) {
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
            Configuration.headless = PROP.headless();
        } else {
            DesiredCapabilities desCaps = DesiredCapabilities.firefox();
            desCaps.setBrowserName("firefox");
            desCaps.setCapability("enableVNC", true);
            //Capabilites for Zalenium (test name & file name)
            desCaps.setCapability("name", method.getName());
            desCaps.setCapability("testFileNameTemplate", "REC_{testName}_{timestamp}");

            RemoteWebDriver driver = new RemoteWebDriver(URI.create(PROP.selenoidUrl()).toURL(), desCaps);
            driver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @Step("Open TheInternet home page")
    public static pages.TheInternet.MainPage openTheInternet() {
        // Opens local site mirror in the proj directory
        Selenide.open(PROJ_PATH + S + "sites" + S + "theinternet" + S + "index.html");
        return new pages.TheInternet.MainPage();
    }

    @Step("Open Selenium Easy home page")
    public static pages.SelEasy.MainPage openSelEasy() {
        Selenide.open(PROJ_PATH + S + "sites" + S + "seleniumeasy" + S + "index.html");
        return new pages.SelEasy.MainPage();
    }

    @Step("Attempt multiple times until success")
    public static void waitForSuccess(Runnable run, int steps, int pause) {
        for(int i = 0; i < steps; i++) {
            try {
                execStep(i, run);
                break;
            } catch (Exception | AssertionError ae) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Step("Step {i}")
    private static void execStep(int i, Runnable runnable) {
        runnable.run();
    }

    @AfterMethod(alwaysRun = true, description = "Save page screenshot and HTML")
    public void saveScreenAndHTML() {
        screenshot("Screen " + url());
        HTML();
        WebDriverRunner.driver().close();
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

}

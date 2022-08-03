package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

public class Methods {

    static final Properties PROP = ConfigFactory.create(Properties.class);
    final static String PROJ_PATH = new File("").getAbsolutePath();
    final static String S = File.separator;

    public static void setupBrowser(boolean headless) throws MalformedURLException  {
        Allure.addAttachment("Remote", String.valueOf(PROP.remote()));

        if(!PROP.remote()) {
            WebDriverManager.firefoxdriver().setup();
            Configuration.browser = "firefox";
            Configuration.headless = headless;
        } else {
            DesiredCapabilities desCaps = new DesiredCapabilities();
            desCaps.setBrowserName("firefox");

            RemoteWebDriver driver = new RemoteWebDriver(URI.create(PROP.selenoidUrl()).toURL(), desCaps);
            driver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @Step("Open TheInternet home page")
    public static pages.TheInternet.MainPage openTheInternet() {
        // Opens local site mirror in the proj directory
        Selenide.open("file://" + PROJ_PATH + S + "sites" + S + "theinternet" + S + "index.html");
        return new pages.TheInternet.MainPage();
    }

    @Step("Open Selenium Easy home page")
    public static pages.SelEasy.MainPage openSelEasy() {
        Selenide.open("file://" + PROJ_PATH + S + "sites" + S + "seleniumeasy" + S + "index.html");
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

}

package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.source;
import static com.codeborne.selenide.WebDriverRunner.url;

public class GeneralBrowser {

    final static Properties PROP = ConfigFactory.create(Properties.class);

    @BeforeMethod(alwaysRun = true, description = "Browser Setup")
    public static void setup() throws MalformedURLException {
        Allure.addAttachment("Remote", String.valueOf(PROP.remote()));

        if(!PROP.remote()) {
            WebDriverManager.firefoxdriver().setup();
            Configuration.browser = "firefox";
            Configuration.headless = PROP.headless();
        } else {
            DesiredCapabilities desCaps = new DesiredCapabilities();
            desCaps.setBrowserName("firefox");

            RemoteWebDriver driver = new RemoteWebDriver(URI.create(PROP.selenoidUrl()).toURL(), desCaps);
            driver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @AfterMethod(alwaysRun = true, description = "Save page screenshot and HTML")
    public void saveScreenAndHTML() {
        screenshot("Screen " + url());
        HTML();
        WebDriverRunner.driver().close();
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] screenshot(String name) {
        return Selenide.screenshot(OutputType.BYTES);
    }

    @Attachment(type = "text/html")
    private byte[] HTML() {
        return source().getBytes(StandardCharsets.UTF_8);
    }
}

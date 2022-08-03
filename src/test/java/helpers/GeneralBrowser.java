package helpers;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;
import static com.codeborne.selenide.WebDriverRunner.source;
import static com.codeborne.selenide.WebDriverRunner.url;
import static helpers.Methods.setupBrowser;

public class GeneralBrowser {

    final Properties PROP = ConfigFactory.create(Properties.class);

    @BeforeMethod(alwaysRun = true, description = "Browser Setup")
    public void setup() throws MalformedURLException {
        setupBrowser(PROP.headless());
    }

    @AfterMethod(alwaysRun = true, description = "Save page screenshot and HTML")
    public void saveScreenAndHTML() {
        screenshot("Screen " + url());
        HTML();
        WebDriverRunner.driver().close();
    }

    @Attachment(value = "{name}", type = "image/png")
    private byte[] screenshot(String name) {
        File screen = takeScreenShotAsFile();
        byte[] buffer = new byte[(int) screen.length()];
        InputStream is;
        try {
            is = new FileInputStream(screen);
            is.read(buffer);
            is.close();
        } catch (IOException ignored) {}
        return buffer;
    }

    @Attachment(type = "text/html")
    private byte[] HTML() {
        return source().getBytes(StandardCharsets.UTF_8);
    }
}

package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsFile;
import static com.codeborne.selenide.WebDriverRunner.source;
import static com.codeborne.selenide.WebDriverRunner.url;
import static java.time.DayOfWeek.SUNDAY;

public class Methods {

    @Step("Open TheInternet home page")
    public static pages.TheInternet.MainPage openTheInternet() {
        Selenide.open("https://the-internet.herokuapp.com");
        return new pages.TheInternet.MainPage();
    }

    @Step("Open Selenium Easy home page")
    public static pages.SelEasy.MainPage openSelEasy() {
        Selenide.open("https://www.seleniumeasy.com/test");
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

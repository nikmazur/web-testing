package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import java.io.File;

public class Methods {

    final static String PROJ_PATH = new File("").getAbsolutePath();
    final static String S = File.separator;

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
        boolean success = false;
        for(int i = 0; i < steps - 1; i++) {
            try {
                execStep(i, run);
                success = true;
                break;
            } catch (Exception | AssertionError ae) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException ignored) {}
            }
        }
        if(!success) execStep(steps, run);
    }

    @Step("Step {i}")
    private static void execStep(int i, Runnable runnable) {
        runnable.run();
    }

}

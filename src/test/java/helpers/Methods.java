package helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

import java.io.File;
import java.util.concurrent.locks.LockSupport;

public class Methods {

    public static final String PROJ_PATH = new File("").getAbsolutePath();
    public static final String S = File.separator;
    public static final UniformRandomProvider RNG = RandomSource.XO_RO_SHI_RO_64_S.create();

    @Step("Open Selenium Easy home page")
    public static pages.seleasy.MainPage openSelEasy() {
        Selenide.open("file://" + PROJ_PATH + S + "sites" + S + "seleniumeasy" + S + "index.html");
        return new pages.seleasy.MainPage();
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
                LockSupport.parkNanos(pause);
            }
        }
        if(!success) execStep(steps, run);
    }

    @Step("Step {i}")
    private static void execStep(int i, Runnable runnable) {
        runnable.run();
    }

    // Method for returning new passed class instance
    public static <T> T newInstance(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}

package tests;

import com.github.romankh3.image.comparison.model.Rectangle;
import helpers.ScreensBrowser;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static helpers.Methods.openSelEasy;
import static helpers.Methods.openTheInternet;

@Epic("Web Testing")
@Feature("Testing using screenshots")
@Test(groups = "Screens")
public class ScreenTests extends ScreensBrowser {
    private final String CLASSNAME = this.getClass().getSimpleName();

    @Test(description = "Selenium Easy home page")
    public void selEasyHomePage() {
        openSelEasy();
        assertPage(CLASSNAME, new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @Test(description = "Specific area on a page (large table)")
    @Description("Compare screens of a specific page area")
    public void largeTable() {
        openTheInternet()
                .openLargeTable();
        assertPageArea(CLASSNAME, new Object(){}.getClass().getEnclosingMethod().getName(), $x("//table"));
    }

    @Test(description = "Failing test with edited screen")
    @Description("Failing test where the expected screen has been manually edited in paint")
    public void editedImage() {
        openSelEasy();
        assertPage(CLASSNAME, new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @Test(description = "Page assert with ignored area")
    @Description("The expected screen has been edited in paint by adding 2 lines - red and blue. " +
            "However the red line is over the banner area, which is ignored, while blue is not.")
    public void pageWIgnoredArea() {
        openSelEasy();
        List<Rectangle> ignore = Collections.singletonList(
                calcElemLocation("Banner", $x("//div[@role='listbox']")));
        assertPageWIgnore(CLASSNAME, new Object(){}.getClass().getEnclosingMethod().getName(), ignore);
    }
}

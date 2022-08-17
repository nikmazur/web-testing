package helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.github.romankh3.image.comparison.model.Rectangle;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Screenshots.takeScreenShotAsImage;
import static com.codeborne.selenide.Selenide.$x;
import static com.github.romankh3.image.comparison.ImageComparisonUtil.readImageFromResources;
import static com.github.romankh3.image.comparison.ImageComparisonUtil.saveImage;
import static helpers.Methods.setupBrowser;
import static org.testng.Assert.assertEquals;

public class ScreensBrowser {

    final static String S = File.separator;
    final static String SCREENS_PATH = "src" + S + "test" + S + "resources" + S + "screens" + S;

    @BeforeMethod(alwaysRun = true, description = "Browser Setup")
    public void setup() throws MalformedURLException {
        //For screens always need to use headless, otherwise screens will be of a browser viewport instead of full page
        setupBrowser(true);
    }

    @Step("Save screenshot to file")
    public void saveScreen(String className, String methodName, SelenideElement elem) {
        //If SelenideElement is null - save image of the whole page, else of the element
        saveImage(new File(SCREENS_PATH + className + S + methodName + ".png"),
                takeScreenShotAsImage(Objects.requireNonNullElseGet(elem, () -> $x("/html"))));
    }

    @Step("Compare current page screenshot with saved file")
    public void assertPage(String className, String methodName) {
        assertScreens(className, methodName, null, Collections.emptyList());
    }

    @Step("Compare area of the current page screenshot with saved file")
    public void assertPageArea(String className, String methodName, SelenideElement elem) {
        assertScreens(className, methodName, elem, Collections.emptyList());
    }

    @Step("Compare current page screenshot (excluding ignored areas) with saved file")
    public void assertPageWIgnore(String className, String methodName, List<Rectangle> ignores) {
        assertScreens(className, methodName, null, ignores);
    }

    @Step("Calculate {0} coordinates for adding to ignore list")
    public Rectangle calcElemLocation(String elemName, SelenideElement elem) {
        int minX = elem.getRect().getX();
        int minY = elem.getRect().getY();
        int maxX = minX + elem.getRect().getWidth();
        int maxY = minY + elem.getRect().getHeight();
        return new Rectangle(minX, minY, maxX, maxY);
    }

    private void assertScreens(String className, String methodName, SelenideElement elem, List<Rectangle> ignores) {
        //Fluent wait for page to fully load using JS readyState
        Methods.waitForSuccess(()->
                        assertEquals(Selenide.executeJavaScript("return document.readyState").toString(), "complete"),
                10, 200);

        //Checks if screen file exists. If not - create a new one
        if(!new File(SCREENS_PATH + className + S + methodName + ".png").exists())
            saveScreen(className, methodName, elem);

        BufferedImage expected = readImageFromResources(SCREENS_PATH + className + S + methodName + ".png");
        //If SelenideElement is null - take screen of the whole page, else of the element
        BufferedImage actual = takeScreenShotAsImage(Objects.requireNonNullElseGet(elem, () -> $x("/html")));

        ImageComparisonResult result = new ImageComparison(expected, actual)
                //Enable and set opacity for ignored areas (will be displayed as green on result image)
                .setDrawExcludedRectangles(true).setExcludedRectangleFilling(true, 50).setExcludedAreas(ignores)
                //Set opacity for difference areas (will be displayed as red on result image)
                .setDifferenceRectangleFilling(true, 50).compareImages();

        if(result.getImageComparisonState() == ImageComparisonState.MATCH) {
            attachPng("Result", result.getResult());
        } else {
            attachPng("Expected", result.getExpected());
            attachPng("Actual", result.getActual());
            attachGif(methodName, result);
        }
        assertEquals(result.getImageComparisonState(), ImageComparisonState.MATCH);
    }

    @Attachment(value = "{0}", type = "image/png")
    private static byte[] attachPng(String fileName, BufferedImage img) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] imageInByte = new byte[0];
        try {
            ImageIO.write(img, "png", stream);
            stream.flush();
            imageInByte = stream.toByteArray();
            stream.close();
        } catch (IOException ignored) {}
        return imageInByte;
    }

    @Attachment(value = "GIF", type = "image/gif")
    private static byte[] attachGif(String methodName, ImageComparisonResult res) {
        File gif = new File("build" + S + "resources" + S + "test" + S + methodName + ".gif");
        gif.getParentFile().mkdirs();
        byte[] gifInByte = new byte[0];
        try {
            ImageOutputStream output = new FileImageOutputStream(gif);
            GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, 1000, true);
            writer.writeToSequence(res.getExpected());
            writer.writeToSequence(res.getActual());
            writer.writeToSequence(res.getResult());
            writer.close();
            output.close();
            gifInByte = Files.readAllBytes(Paths.get(gif.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gifInByte;
    }
}

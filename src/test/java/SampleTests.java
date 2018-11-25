import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;

public class SampleTests extends Methods {

    @Test
    public void login() {
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        Alert alert = driver.switchTo().alert();
        final String adm = "admin";

        alert.sendKeys(adm);
        rob.keyPress(KeyEvent.VK_TAB);

        StringSelection stringSelection = new StringSelection(adm);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        rob.keyPress(KeyEvent.VK_CONTROL);
        rob.keyPress(KeyEvent.VK_V);
        rob.keyRelease(KeyEvent.VK_V);
        rob.keyRelease(KeyEvent.VK_CONTROL);

        alert.accept();
    }

    @Test
    public void checkboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement check1 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        WebElement check2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));

        check1.click();
        check2.click();

        assert(check1.isSelected());
        assert(!check2.isSelected());
    }

    @Test
    public void selectFromList() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        Select list = new Select(driver.findElement(By.id("dropdown")));
        list.selectByIndex(2);

        WebElement option = list.getFirstSelectedOption();
        assertEquals("Option 2", option.getText());
    }

    @Test
    public void keyboardCheck() {
        driver.get("https://the-internet.herokuapp.com/key_presses");

        rob.keyPress(KeyEvent.VK_PAUSE);
        WebElement res = driver.findElement(By.id("result"));
        assertEquals("You entered: PAUSE", res.getText());

        rob.keyPress(KeyEvent.VK_NUMPAD6);
        assertEquals("You entered: NUMPAD6", res.getText());
    }

    @Test
    public void newWind() {
        driver.get("https://the-internet.herokuapp.com/windows");
        clickXpath("/html/body/div[2]/div/div/a");
        sleep(500);
        final String mainWind = driver.getWindowHandle();

        for(String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);

        assertEquals("New Window", driver.getTitle());
        driver.close();
        driver.switchTo().window(mainWind);
    }

    @Test (dependsOnMethods = "login")
    public void fileDL() {
        driver.get("https://the-internet.herokuapp.com/download_secure");
        driver.findElement(By.partialLinkText("some-file.txt")).click();

        File doc = new File(projPath + "\\bin\\download\\some-file.txt");
        assert(doc.exists());
        doc.delete();
    }

    @Test
    public void sliderVal() {
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");
        WebElement slider = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/input"));
        WebElement value = driver.findElement(By.id("range"));

        assertEquals("0", value.getText());

        slider.sendKeys(Keys.ARROW_RIGHT);
        sleep(200);
        assertEquals("0.5", value.getText());

        slider.sendKeys(Keys.ARROW_RIGHT);
        slider.sendKeys(Keys.ARROW_RIGHT);
        sleep(200);
        assertEquals("1.5", value.getText());
    }

    @Test
    public void dataTable() {
        driver.get("https://the-internet.herokuapp.com/large");

        List<WebElement> columns = driver.findElements(By.cssSelector("#large-table th"));
        List<WebElement> rows = driver.findElements(By.cssSelector("#large-table tr"));
        assertEquals(50, columns.size());
        assertEquals(51, rows.size());

        final int rRow = ThreadLocalRandom.current().nextInt(1, 51);
        final int rCol = ThreadLocalRandom.current().nextInt(1, 51);

        WebElement rCell = driver.findElement(By.cssSelector
                (".row-" + rRow + " > td:nth-child(" + rCol + ")"));
        assertEquals(rRow + "." + rCol, rCell.getText());
    }
}

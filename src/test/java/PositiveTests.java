import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class PositiveTests extends Methods {

    @Test
    public void login() {
        driver.get("https://www.phptravels.net/admin");

        WebElement login = driver.findElement(By.name("email"));
        login.sendKeys("admin@phptravels.com");

        WebElement pass = driver.findElement(By.name("password"));
        wait.until(ExpectedConditions.elementToBeClickable(pass));
        pass.sendKeys("demoadmin");

        WebElement submit = driver.findElement(By.xpath("/html/body/div/form[1]/button"));
        submit.click();

        screen("login");
        wait.until(ExpectedConditions.titleIs("Dashboard"));
    }

    @Test (priority = 1, dependsOnMethods = "login")
    public void checkCounter() {
        driver.get("https://www.phptravels.net/admin");

        WebElement counter = driver.findElement(By.cssSelector(".hidden-md > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(4)"));
        String num = (counter.getText()).replace("Paid : ","");
        screen("checkCounter");
        assertNotEquals("0", num);
    }

    @Test (priority = 2, dependsOnMethods = "login")
    public void renamePage() {
        driver.get("https://www.phptravels.net/admin");

        clickXpath("//*[@id=\"social-sidebar-menu\"]/li[4]/a");

        clickXpath("//*[@id=\"menu-ui\"]/li[1]/a");

        enterXpath("Testing", "/html/body/div[2]/div/div/form/div/div[2]/div/div[1]/div[21]/div/input");

        clickXpath("/html/body/div[2]/div/div/form/div/div[3]/button");

        driver.get("https://www.phptravels.net/");
        screen("renamePage");
        assertEquals("Testing", driver.getTitle());
    }

    @Test (priority = 3, dependsOnMethods = "login")
    public void addCustomer() {

        driver.get("https://www.phptravels.net/admin/accounts/customers/add");

        enterXpath("John", "//*[@id=\"content\"]/form/div/div[2]/div/div[1]/div/input");
        enterXpath("Smith", "//*[@id=\"content\"]/form/div/div[2]/div/div[2]/div/input");
        enterXpath("mail@test.com", "//*[@id=\"content\"]/form/div/div[2]/div/div[3]/div/input");

        byte[] array = new byte[8];
        new Random().nextBytes(array);
        String rPass = new String(array, Charset.forName("UTF-8"));
        enterXpath(rPass, "//*[@id=\"content\"]/form/div/div[2]/div/div[4]/div/input");

//        clickXpath("//*[@id=\"select2-drop\"]");
//        Select countries = new Select(driver.findElement(By.xpath("//*[@id=\"select2-drop\"]/ul")));
//        countries.selectByValue("US");

        screen("addCustomer");

    }

    @Test (priority = 4, dependsOnMethods = "login")
    public void deleteHotel() {
        driver.get("https://www.phptravels.net/admin/hotels");
        final String tAddr = "//*[@id=\"content\"]/div/div[2]/div/div/div[1]/div[2]/table";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tAddr + "/tbody/tr")));
        List<WebElement> rowsBefore = driver.findElements(By.xpath(tAddr + "/tbody/tr"));

        int randRow = ThreadLocalRandom.current().nextInt(29, 39);
        clickXpath("//*[@id=\"" + randRow + "\"]/i");

        sleep(1000);
        driver.switchTo().alert().accept();

        sleep(3000);
        List<WebElement> rowsAfter = driver.findElements(By.xpath(tAddr + "/tbody/tr"));

        screen("deleteHotel");
        assertEquals((rowsBefore.size() - 1), rowsAfter.size());
    }

    @Test (priority = 5, dependsOnMethods = "login")
    public void downloadCSV() {
        driver.get("https://www.phptravels.net/admin/cars");
        clickXpath("/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/a[2]");

        sleep(3000);
        for(String winHandle : driver.getWindowHandles())
            driver.switchTo().window(winHandle);
        driver.close();

        screen("downloadCSV");
        File carsCSV = new File(projPath + "\\bin\\download\\Pt_Cars.csv");
        assert(carsCSV.exists());
        carsCSV.delete();
    }

}

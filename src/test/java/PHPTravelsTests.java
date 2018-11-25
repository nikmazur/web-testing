import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class PHPTravelsTests extends Methods {

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
        final String rPass = randPass();

        enterXpath("John", "//*[@id=\"content\"]/form/div/div[2]/div/div[1]/div/input");
        enterXpath("Smith", "//*[@id=\"content\"]/form/div/div[2]/div/div[2]/div/input");
        enterXpath("mail@test.com", "//*[@id=\"content\"]/form/div/div[2]/div/div[3]/div/input");
        enterXpath(rPass, "//*[@id=\"content\"]/form/div/div[2]/div/div[4]/div/input");

        clickXpath("//*[@id=\"s2id_autogen1\"]");
        WebElement country = driver.findElement(By.xpath("/html/body/div[5]/div/input"));
        country.sendKeys("United States");
        sleep(500);
        country.sendKeys(Keys.ENTER);

        clickXpath("/html/body/div[2]/div/div/form/div/div[3]/button");
        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/admin/accounts/customers/"));

        driver.get("https://www.phptravels.net/login");
        enterXpath("mail@test.com", "/html/body/div[5]/div[1]/div[1]/form/div[1]/div[5]/div/div[1]/input");
        enterXpath(rPass, "/html/body/div[5]/div[1]/div[1]/form/div[1]/div[5]/div/div[2]/input");
        clickXpath("/html/body/div[5]/div[1]/div[1]/form/button");

        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/account/"));
        WebElement pName = driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/div/div[1]/h3"));
        screen("addCustomer");
        assertEquals("Hi, John Smith", pName.getText());
    }

    @Test (priority = 4, dependsOnMethods = "login")
    public void editCar() {
        driver.get("https://www.phptravels.net/admin/cars");

        WebElement bEdit = driver.findElement(By.xpath(
                "/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[2]/table/tbody/tr[4]/td[12]/span/a[1]/i"));
        wait.until(ExpectedConditions.visibilityOf(bEdit));
        bEdit.click();
        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/admin/cars/manage/Ford-Focus-2014"));

        enterXpath("Test Car", "/html/body/div[2]/div/div/form/div/div[1]/div/div[1]/div[3]/div/input");
        clickXpath("//*[@id=\"update\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/h4")));

        WebElement tCar = driver.findElement(By.xpath(
                "/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[2]/table/tbody/tr[4]/td[5]"));
        screen("editCar");
        assertEquals("Test Car", tCar.getText());
    }

    @Test (priority = 5, dependsOnMethods = "login")
    public void deleteHotel() {
        driver.get("https://www.phptravels.net/admin/hotels");
        final String tAddr =
                "//*[@id=\"content\"]/div/div[2]/div/div/div[1]/div[2]/table/tbody/tr";

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tAddr)));
        List<WebElement> rowsBefore = driver.findElements(By.xpath(tAddr));

        int randRow = ThreadLocalRandom.current().nextInt(29, 39);
        clickXpath("//*[@id=\"" + randRow + "\"]/i");

        sleep(1000);
        driver.switchTo().alert().accept();

        sleep(3000);
        List<WebElement> rowsAfter = driver.findElements(By.xpath(tAddr));

        screen("deleteHotel");
        assertEquals((rowsBefore.size() - 1), rowsAfter.size());
    }

    @Test (priority = 6, dependsOnMethods = "login")
    public void downloadCSV() {
        driver.get("https://www.phptravels.net/admin/cars");
        clickXpath("/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/a[2]");

        sleep(3000);
        screen("downloadCSV");
        File carsCSV = new File(projPath + "\\bin\\download\\Pt_Cars.csv");
        assert(carsCSV.exists());
        carsCSV.delete();
    }

    @Test (priority = 7, dependsOnMethods = "login")
    public void negNumField() {
        driver.get("https://www.phptravels.net/admin/settings");

        WebElement numField = driver.findElement(By.xpath(
                "/html/body/div[2]/div/div/form/div/div[2]/div/div[1]/div[18]/div/input"));
        numField.clear();
        numField.sendKeys("abc");
        clickXpath("/html/body/div[2]/div/div/form/div/div[3]/button");
        screen("negNumField");
        assertNotEquals(numField.getAttribute("value"), "abc");
    }

    @Test (priority = 8, dependsOnMethods = "login")
    public void negAddTour() {
        driver.get("https://www.phptravels.net/admin/tours/add");
        clickXpath("//*[@id=\"add\"]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div")));
        WebElement warnList = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div"));

        screen("negAddTour");
        final String expect = "The Tour Name field is required.\n" +
                "The Tour Type field is required.\n" +
                "The Adult Price field is required.";
        assertEquals(expect, warnList.getText());
    }

    @Test (priority = 99, dependsOnMethods = "login")
    public void negBadLogin() {
        driver.get("https://www.phptravels.net/admin/logout");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement login = driver.findElement(By.name("email"));
        login.sendKeys("admin@phptravels.com");

        WebElement pass = driver.findElement(By.name("password"));
        wait.until(ExpectedConditions.elementToBeClickable(pass));
        pass.sendKeys("demoadmin");

        clickXpath("/html/body/div/form[1]/button");
        screen("negBadLogin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "/html/body/div/form[1]/div[2]/div")));
    }

}

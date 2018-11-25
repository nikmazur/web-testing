import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class PHPTravelsTests extends Methods {

    //Login to the dashboard.
    //This test is ran first, and all subsequent tests are dependant on it.
    @Test
    public void login() {
        //Open the dashboard page where we are required to login first
        driver.get("https://www.phptravels.net/admin");

        //Enter email
        WebElement login = driver.findElement(By.name("email"));
        login.sendKeys("admin@phptravels.com");

        //Wait for password field to be clickable, enter pass
        WebElement pass = driver.findElement(By.name("password"));
        wait.until(ExpectedConditions.elementToBeClickable(pass));
        pass.sendKeys("demoadmin");

        //Hit submit
        driver.findElement(By.xpath("/html/body/div/form[1]/button")).click();

        //Take screenshot
        screen("login");
        //Wait for the page title to change to Dashboard (means we are logged in)
        wait.until(ExpectedConditions.titleIs("Dashboard"));
    }

    //Check portal activity by retrieving the counter of bookings for the last 90 days, and making sure it is not 0.
    @Test (priority = 1, dependsOnMethods = "login")
    public void checkCounter() {
        driver.get("https://www.phptravels.net/admin");

        WebElement counter = driver.findElement(By.cssSelector(".hidden-md > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(4)"));
        //Get text from the counter, remove the letters before the number
        final String num = (counter.getText()).replace("Paid : ","");
        screen("checkCounter");
        //Assert number is not 0
        assertNotEquals("0", num);
    }

    //Rename the main portal page in settings.
    //We enter the new name and then navigate to the main page to check for the new page title.
    @Test (priority = 2, dependsOnMethods = "login")
    public void renamePage() {
        driver.get("https://www.phptravels.net/admin");

        //Navigate to General -> Settings in the left menu
        //This is to test the menu, in subsequent tests we'll just open the pages directly by their URL.
        driver.findElement(By.xpath("//*[@id=\"social-sidebar-menu\"]/li[4]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"menu-ui\"]/li[1]/a")).click();

        //Enter new page title, hit submit
        enterXpath("Testing", "/html/body/div[2]/div/div/form/div/div[2]/div/div[1]/div[21]/div/input");
        driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div/div[3]/button")).click();

        //Go to main portal page, assert new title
        driver.get("https://www.phptravels.net/");
        screen("renamePage");
        assertEquals("Testing", driver.getTitle());
    }

    //Adds a new customer to the portal.
    // Fills in required fields, navigates to the login page and tries to log in under the newly created account.
    @Test (priority = 3, dependsOnMethods = "login")
    public void addCustomer() {
        driver.get("https://www.phptravels.net/admin/accounts/customers/add");
        //Generate random pass for account
        final String rPass = randPass();

        //Fill in name, email and pass
        enterXpath("John", "//*[@id=\"content\"]/form/div/div[2]/div/div[1]/div/input");
        enterXpath("Smith", "//*[@id=\"content\"]/form/div/div[2]/div/div[2]/div/input");
        enterXpath("mail@test.com", "//*[@id=\"content\"]/form/div/div[2]/div/div[3]/div/input");
        enterXpath(rPass, "//*[@id=\"content\"]/form/div/div[2]/div/div[4]/div/input");

        //Select country by clicking on the input field.
        //Type, wait for search results and hit Enter to submit
        driver.findElement(By.xpath("//*[@id=\"s2id_autogen1\"]")).click();
        WebElement country = driver.findElement(By.xpath("/html/body/div[5]/div/input"));
        country.sendKeys("United States");
        sleep(500);
        country.sendKeys(Keys.ENTER);

        //Hit Submit, wait until we are taken back to customers list
        driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div/div[3]/button")).click();
        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/admin/accounts/customers/"));

        //Go to customers login page, try to log in under the created account
        driver.get("https://www.phptravels.net/login");
        enterXpath("mail@test.com", "/html/body/div[5]/div[1]/div[1]/form/div[1]/div[5]/div/div[1]/input");
        enterXpath(rPass, "/html/body/div[5]/div[1]/div[1]/form/div[1]/div[5]/div/div[2]/input");
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/form/button")).click();

        //Wait until we are redirected to the customer's personal page, assert the greeting text
        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/account/"));
        WebElement pName = driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/div/div[1]/h3"));
        screen("addCustomer");
        assertEquals("Hi, John Smith", pName.getText());
    }

    //Edit one of the cars listed on the portal. Rename and check that the changes are applied.
    @Test (priority = 4, dependsOnMethods = "login")
    public void editCar() {
        driver.get("https://www.phptravels.net/admin/cars");
        final String newName = "Test Car";

        //Wait until the table with data is loaded, click on one of the cars, wait for redirect to edit page
        WebElement bEdit = driver.findElement(By.xpath(
                "/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[2]/table/tbody/tr[4]/td[12]/span/a[1]/i"));
        wait.until(ExpectedConditions.visibilityOf(bEdit));
        bEdit.click();
        wait.until(ExpectedConditions.urlMatches("https://www.phptravels.net/admin/cars/manage/Ford-Focus-2014"));

        //Change name of the car, hit Submit, wait until redirected and table with cars is visible
        enterXpath(newName, "/html/body/div[2]/div/div/form/div/div[1]/div/div[1]/div[3]/div/input");
        driver.findElement(By.xpath("//*[@id=\"update\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/h4")));

        //Retrieve the new name from the table, compare with what we entered
        WebElement tCar = driver.findElement(By.xpath(
                "/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[2]/table/tbody/tr[4]/td[5]"));
        screen("editCar");
        assertEquals(newName, tCar.getText());
    }

    //Randomly delete one of the listed hotels
    @Test (priority = 5, dependsOnMethods = "login")
    public void deleteHotel() {
        driver.get("https://www.phptravels.net/admin/hotels");
        //Address of the table where hotels are listed
        final String tAddr =
                "//*[@id=\"content\"]/div/div[2]/div/div/div[1]/div[2]/table/tbody/tr";

        //Wait until table is loaded, save current state to list object
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tAddr)));
        List<WebElement> rowsBefore = driver.findElements(By.xpath(tAddr));

        //Click on a random row in the table. By default listed hotel indexes are from 28 to 40.
        final int randRow = ThreadLocalRandom.current().nextInt(28, 41);
        driver.findElement(By.xpath("//*[@id=\"" + randRow + "\"]/i")).click();

        //Wait for pop-up confirmation dialog, accept it
        sleep(1000);
        driver.switchTo().alert().accept();

        //Wait for the same table with new data to be loaded, save to another list
        sleep(3000);
        List<WebElement> rowsAfter = driver.findElements(By.xpath(tAddr));

        //Compare sizes of the 2 lists, one should be shorter by one hotel
        screen("deleteHotel");
        assertEquals((rowsBefore.size() - 1), rowsAfter.size());
    }

    //Download a CSV file with the details of all cars which is stored in the project directory
    @Test (priority = 6, dependsOnMethods = "login")
    public void downloadCSV() {
        driver.get("https://www.phptravels.net/admin/cars");
        //Hit the Download CSV button on the Cars page
        driver.findElement(By.xpath
                ("/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/a[2]")).click();
//        clickXpath("/html/body/div[2]/div/div/div/div[2]/div/div/div[1]/div[1]/div[1]/a[2]");

        //Pause 3 secs for the file to download
        sleep(3000);
        screen("downloadCSV");

        //Set full path of the downloaded file, assert it exists, and afterwards delete it
        File carsCSV = new File(projPath + "\\bin\\download\\Pt_Cars.csv");
        assert(carsCSV.exists());
        carsCSV.delete();
    }

    //Negative tests start here.
    //Try to enter a text value into a numeric field in Settings.
    @Test (priority = 7, dependsOnMethods = "login")
    public void negNumField() {
        driver.get("https://www.phptravels.net/admin/settings");
        final String text = "abc";

        //Locate numeric Coupon Code Length field, enter text into it, hit Submit
        WebElement numField = driver.findElement(By.xpath(
                "/html/body/div[2]/div/div/form/div/div[2]/div/div[1]/div[18]/div/input"));
        numField.clear();
        numField.sendKeys(text);
        driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div/div[3]/button")).click();
        screen("negNumField");

        //Retrieve the current field value, check that text value was NOT applied
        assertNotEquals(numField.getAttribute("value"), text);
    }

    //Trying to add a tour without filling any of the required fields.
    //Check for a warning message to be displayed.
    @Test (priority = 8, dependsOnMethods = "login")
    public void negAddTour() {
        driver.get("https://www.phptravels.net/admin/tours/add");
        //Click on submit without filling any fields
        driver.findElement(By.xpath("//*[@id=\"add\"]")).click();

        //Wait until the warning message is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div")));
        WebElement warnList = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div"));

        screen("negAddTour");
        final String expect = "The Tour Name field is required.\n" +
                "The Tour Type field is required.\n" +
                "The Adult Price field is required.";
        //Assert that the warning text matches
        assertEquals(expect, warnList.getText());
    }

    //Try to login with invalid credentials.
    //Log out (priority 99 so that this test is always run last), then try to login with a random pass.
    @Test (priority = 99)
    public void negBadLogin() {
        //Log out by URL, redirected to login page
        driver.get("https://www.phptravels.net/admin/logout");

        //Enter same admin email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement login = driver.findElement(By.name("email"));
        login.sendKeys("admin@phptravels.com");

        //Enter random password
        WebElement pass = driver.findElement(By.name("password"));
        wait.until(ExpectedConditions.elementToBeClickable(pass));
        pass.sendKeys(randPass());

        //Submit, wait for the invalid credentials warning
        driver.findElement(By.xpath("/html/body/div/form[1]/button")).click();
        screen("negBadLogin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "/html/body/div/form[1]/div[2]/div")));
    }

}

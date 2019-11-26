package pages.SelEasy;

import com.codeborne.selenide.Condition;
import helpers.RandomPerson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class InputFormSubmit {

    private String header = "//h2";

    private String form = "//form[@id='contact_form']";
    private String fName = form + "//input[@name='first_name']";
    private String lName = form + "//input[@name='last_name']";
    private String email = form + "//input[@name='email']";
    private String phone = form + "//input[@name='phone']";
    private String address = form + "//input[@name='address']";
    private String city = form + "//input[@name='city']";
    private String state = form + "//select[@name='state']";
    private String zip = form + "//input[@name='zip']";
    private String website = form + "//input[@name='website']";
    private String hostingInput = form + "//label[text()='Do you have hosting?']";
    private String projectDescription = form + "//textarea[@name='comment']";

    public InputFormSubmit() {
        PageLayout layout = new PageLayout();
        $x(header).shouldHave(Condition.exactText("Input form with validations"));
    }

    @Step("Fill out the form with contact details")
    public InputFormSubmit fillInputForm(RandomPerson pers, boolean hosting, String projDesc) {
        $x(fName).sendKeys(pers.firstName);
        $x(lName).sendKeys(pers.lastName);
        $x(email).sendKeys(pers.email);
        $x(phone).sendKeys(pers.phone);
        $x(address).sendKeys(pers.address);
        $x(city).sendKeys(pers.city);
        $x(zip).sendKeys(pers.zipCode);
        $x(website).sendKeys(pers.website);
        $x(projectDescription).sendKeys(projDesc);

        $x(state).click();
        $x(state + "/option[contains(text(),'" + pers.state + "')]").click();

        if(hosting)
            $x(hostingInput + "/..//input[@value='yes']").click();
        else
            $x(hostingInput + "/..//input[@value='no']").click();

        return this;
    }
}

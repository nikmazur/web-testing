package pages.seleasy;

import com.codeborne.selenide.Condition;
import helpers.RandomPerson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class InputFormSubmit extends PageLayout {

    private static final String header = "//h2";

    private static final String form = "//form[@id='contact_form']";
    private static final String fName = form + "//input[@name='first_name']";
    private static final String lName = form + "//input[@name='last_name']";
    private static final String email = form + "//input[@name='email']";
    private static final String phone = form + "//input[@name='phone']";
    private static final String address = form + "//input[@name='address']";
    private static final String city = form + "//input[@name='city']";
    private static final String state = form + "//select[@name='state']";
    private static final String zip = form + "//input[@name='zip']";
    private static final String website = form + "//input[@name='website']";
    private static final String hostingInput = form + "//label[text()='Do you have hosting?']";
    private static final String projDescription = form + "//textarea[@name='comment']";

    public InputFormSubmit() {
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
        $x(projDescription).sendKeys(projDesc);

        $x(state).click();
        $x(state + "/option[contains(text(),'" + pers.state + "')]").click();

        if(hosting)
            $x(hostingInput + "/..//input[@value='yes']").click();
        else
            $x(hostingInput + "/..//input[@value='no']").click();

        return this;
    }
}

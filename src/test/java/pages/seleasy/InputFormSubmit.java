package pages.seleasy;

import com.codeborne.selenide.Condition;
import helpers.RandomPerson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class InputFormSubmit extends PageLayout {

    final String HEADER = "//h2";

    final String FORM = "//form[@id='contact_form']";
    final String FNAME = FORM + "//input[@name='first_name']";
    final String LNAME = FORM + "//input[@name='last_name']";
    final String EMAIL = FORM + "//input[@name='email']";
    final String PHONE = FORM + "//input[@name='phone']";
    final String ADDRESS = FORM + "//input[@name='address']";
    final String CITY = FORM + "//input[@name='city']";
    final String STATE = FORM + "//select[@name='state']";
    final String ZIP = FORM + "//input[@name='zip']";
    final String WEBSITE = FORM + "//input[@name='website']";
    final String HOSTING_INPUT = FORM + "//label[text()='Do you have hosting?']";
    final String PROJ_DESCRIPTION = FORM + "//textarea[@name='comment']";

    public InputFormSubmit() {
        $x(HEADER).shouldHave(Condition.exactText("Input form with validations"));
    }

    @Step("Fill out the form with contact details")
    public InputFormSubmit fillInputForm(RandomPerson pers, boolean hosting, String projDesc) {
        $x(FNAME).sendKeys(pers.firstName);
        $x(LNAME).sendKeys(pers.lastName);
        $x(EMAIL).sendKeys(pers.email);
        $x(PHONE).sendKeys(pers.phone);
        $x(ADDRESS).sendKeys(pers.address);
        $x(CITY).sendKeys(pers.city);
        $x(ZIP).sendKeys(pers.zipCode);
        $x(WEBSITE).sendKeys(pers.website);
        $x(PROJ_DESCRIPTION).sendKeys(projDesc);

        $x(STATE).click();
        $x(STATE + "/option[contains(text(),'" + pers.state + "')]").click();

        if(hosting)
            $x(HOSTING_INPUT + "/..//input[@value='yes']").click();
        else
            $x(HOSTING_INPUT + "/..//input[@value='no']").click();

        return this;
    }
}

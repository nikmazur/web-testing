package pages.SelEasy;

import com.codeborne.selenide.Condition;
import helpers.RandomPerson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class InputFormSubmit extends PageLayout {

    private final String HEADER = "//h2";

    private final String FORM = "//form[@id='contact_form']";
    private final String FNAME = FORM + "//input[@name='first_name']";
    private final String LNAME = FORM + "//input[@name='last_name']";
    private final String EMAIL = FORM + "//input[@name='email']";
    private final String PHONE = FORM + "//input[@name='phone']";
    private final String ADDRESS = FORM + "//input[@name='address']";
    private final String CITY = FORM + "//input[@name='city']";
    private final String STATE = FORM + "//select[@name='state']";
    private final String ZIP = FORM + "//input[@name='zip']";
    private final String WEBSITE = FORM + "//input[@name='website']";
    private final String HOSTINGINPUT = FORM + "//label[text()='Do you have hosting?']";
    private final String PROJECTDESCTIPTION = FORM + "//textarea[@name='comment']";

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
        $x(PROJECTDESCTIPTION).sendKeys(projDesc);

        $x(STATE).click();
        $x(STATE + "/option[contains(text(),'" + pers.state + "')]").click();

        if(hosting)
            $x(HOSTINGINPUT + "/..//input[@value='yes']").click();
        else
            $x(HOSTINGINPUT + "/..//input[@value='no']").click();

        return this;
    }
}

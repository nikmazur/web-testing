package pages.TheInternet;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomUtils;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.*;

public class LargeTable {

    private final String CONTENT = "//div[@id='content']";
    private final String HEADER = CONTENT + "//h3";
    private final String TABLE = CONTENT + "//table[@id='large-table']";

    private static SoftAssert sAssert;

    public LargeTable() {
        $x(HEADER).shouldHave(Condition.exactText("Large & Deep DOM"));
        $x(TABLE).shouldBe(Condition.visible);

        $$x(TABLE + "//th").shouldHave(CollectionCondition.size(50));
        $$x(TABLE + "//tr").shouldHave(CollectionCondition.size(51));
    }

    @Step("Select {runs} random cells and verify their values")
    public LargeTable runRandomSelections(int runs) {
        //Using soft assert so that the test will continue even if any assert fails
        sAssert = new SoftAssert();

        for(int i = 0; i < runs; i++)
            selectVerifyRandomCell(RandomUtils.nextInt(1, 51), RandomUtils.nextInt(1, 51));

        sAssert.assertAll();
        return this;
    }

    @Step("Verify that row {row} column {column} has text: {row}.{column}")
    private void selectVerifyRandomCell(int row, int column) {
        SelenideElement cell = $x(TABLE +
                "//tr[@class='row-" + row + "']/td[@class='column-" + column + "']");

        //Cell content format: "RowNumber.ColumnNumber"
        sAssert.assertEquals(cell.getText(), row + "." + column);
    }

    @Then("Row {int} column {int} should have text {word}")
    public void checkCell(int row, int column, String cellText) {
        $x(TABLE + "//tr[@class='row-" + row + "']/td[@class='column-" + column + "']")
                .shouldHave(Condition.text(cellText));
    }
}

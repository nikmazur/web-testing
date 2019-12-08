package pages.TheInternet;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomUtils;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Selenide.*;

public class LargeTable {

    private String content = "//div[@id='content']";
    private String header = content + "//h3";
    private String table = content + "//table[@id='large-table']";

    private static SoftAssert sAssert;

    public LargeTable() {
        $x(header).shouldHave(Condition.exactText("Large & Deep DOM"));
        $x(table).shouldBe(Condition.visible);

        $$x(table + "//th").shouldHave(CollectionCondition.size(50));
        $$x(table + "//tr").shouldHave(CollectionCondition.size(51));
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
        SelenideElement cell = $x(table +
                "//tr[@class='row-" + row + "']/td[@class='column-" + column + "']");

        //Cell content format: "RowNumber.ColumnNumber"
        sAssert.assertEquals(cell.getText(), row + "." + column);
    }
}

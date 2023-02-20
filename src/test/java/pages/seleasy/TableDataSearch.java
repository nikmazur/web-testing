package pages.seleasy;

import com.codeborne.selenide.Condition;
import helpers.Methods;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class TableDataSearch extends PageLayout{

    private static final String header = "//h2";

    private static final String taskTableFilter = "//input[@id='task-table-filter']";
    private static final String taskTable = "//table[@id='task-table']";

    public TableDataSearch() {
        $x(header).shouldHave(Condition.exactText("Type in your search to filter data by Tasks / Assignee / Status "));
        $x(taskTable).shouldBe(Condition.visible);
    }

    @Step("Filter Tasks table by \"{text}\", should be {numberRows} rows visible")
    public TableDataSearch filterCheckResult(String text, int numberRows) {
        Methods.waitForSuccess(()-> {
            $x(taskTableFilter).clear();
            $x(taskTableFilter).sendKeys(text);
            $$x(taskTable + "//tbody/tr").filter(Condition.visible).shouldHave(size(numberRows));
        }, 10, 100);
        return this;
    }
}

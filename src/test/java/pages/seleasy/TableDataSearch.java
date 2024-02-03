package pages.seleasy;

import com.codeborne.selenide.Condition;
import helpers.Methods;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class TableDataSearch extends PageLayout{

    final String HEADER = "//h2";

    final String TASK_TABLE_FILTER = "//input[@id='task-table-filter']";
    final String TASK_TABLE = "//table[@id='task-table']";

    public TableDataSearch() {
        $x(HEADER).shouldHave(Condition.exactText("Type in your search to filter data by Tasks / Assignee / Status "));
        $x(TASK_TABLE).shouldBe(Condition.visible);
    }

    @Step("Filter Tasks table by \"{text}\", should be {numberRows} rows visible")
    public TableDataSearch filterCheckResult(String text, int numberRows) {
        Methods.waitForSuccess(()-> {
            $x(TASK_TABLE_FILTER).clear();
            $x(TASK_TABLE_FILTER).sendKeys(text);
            $$x(TASK_TABLE + "//tbody/tr").filter(Condition.visible).shouldHave(size(numberRows));
        }, 10, 100);
        return this;
    }
}

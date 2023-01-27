package pages.SelEasy;

import com.codeborne.selenide.Condition;
import helpers.Methods;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class TableDataSearch extends PageLayout{

    private final String HEADER = "//h2";

    private final String TASKTABLEFILTER = "//input[@id='task-table-filter']";
    private final String TASKTABLE = "//table[@id='task-table']";

    public TableDataSearch() {
        $x(HEADER).shouldHave(Condition.exactText("Type in your search to filter data by Tasks / Assignee / Status "));
        $x(TASKTABLE).shouldBe(Condition.visible);
    }

    @Step("Filter Tasks table by \"{text}\", should be {numberRows} rows visible")
    public TableDataSearch filterCheckResult(String text, int numberRows) {
        Methods.waitForSuccess(()-> {
            $x(TASKTABLEFILTER).clear();
            $x(TASKTABLEFILTER).sendKeys(text);
            $$x(TASKTABLE + "//tbody/tr").filter(Condition.visible).shouldHave(size(numberRows));
        }, 10, 100);
        return this;
    }
}

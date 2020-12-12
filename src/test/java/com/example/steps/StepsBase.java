package com.example.steps;

import com.example.pages.PageBase;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public abstract class StepsBase<TSteps extends StepsBase, TPage extends PageBase> {

    protected TPage page;

    protected abstract TPage createPageInstance();

    public TSteps openPage() {
        page = createPageInstance();
        page.navigate().assertOpened();
        return (TSteps) this;
    }

    public boolean checkCurrentUrl(String uniqueUrl) {
        return page.getUrl().equals(uniqueUrl);
    }

    @Step(value = "Check that user is authorized")
    public boolean checkIsAuthorized() {
        return $(By.className("LogOut")).exists();
    }
}

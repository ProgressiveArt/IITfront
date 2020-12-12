package com.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.constants.Constants;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class MainPage extends PageBase<MainPage> {
    @Override
    public String getUrl() {
        return "http://forum.l2multi.ru";
    }

    @Override
    public MainPage waitPageLoaded() {
        $(By.id("logo")).waitUntil(Condition.visible, Constants.PAGE_LOAD_TIME);
        return this;
    }

    public SelenideElement getAuthorizedSidebarContent() {
        return $(By.className("sidebar"));
    }

    public SelenideElement getChatElement() {
        return $(By.id("taigachat_box"));
    }

    public SelenideElement getStatsElement() {
        return $(By.className("stats"));
    }

    public SelenideElement getProfileDropdownMenu(){
        return $(By.className("accountUsername"));
    }

    public SelenideElement getSignOutButton() {
        return $(By.className("LogOut"));
    }

    public SelenideElement getProfileNameLink() {
        return $(By.className("fl"));
    }

    public SelenideElement getTextAreaInput() {
        return $(By.tagName("textarea"));
    }

    public SelenideElement getSendStatusButton() {
        return $("input");
    }

    public SelenideElement getLastSentStatus() {
        return $(By.className("messageContent")).find(By.tagName("article"));
    }

    public SelenideElement getSearchInput() {
        return $(By.id("QuickSearchQuery"));
    }

    public SelenideElement getSearchResult() {
        return $(By.className("baseHtml")).find(By.className("OverlayCloser"));
    }

    public SelenideElement getSearchWithoutResult() {
        return $(By.className("messageBody"));
    }
}

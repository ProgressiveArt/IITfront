package com.example.steps;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.MainPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class MainPageSteps extends StepsBase<MainPageSteps, MainPage> {

    @Override
    protected MainPage createPageInstance() {
        return new MainPage();
    }

    @Step(value = "Check that main page")
    public MainPageSteps checkThatIsMainPage(String UniqueUrl){
        boolean isLogin = checkCurrentUrl(UniqueUrl);
        assertThat(isLogin, equalTo(true));
        return this;
    }

    @Step(value = "Check that exist visitor panel")
    public MainPageSteps checkVisitorPanelExists() {
        page.getAuthorizedSidebarContent().shouldBe(visible)
                                          .shouldHave(text("Пользователи онлайн"))
                                          .shouldHave(text("Статистика посещаемости"))
                                          .shouldHave(text("Именинники"))
                                          .shouldHave(text("Статистика форума"))
                                          .shouldHave(text("Поделиться этой страницей"));
        return this;
    }

    @Step(value = "Check that exist chat")
    public MainPageSteps checkChatExists() {
        SelenideElement element = page.getChatElement();
        element.shouldBe(visible);
        String[] messageArray = element.getText().split("\n");
        assertThat(messageArray.length, greaterThan(0));
        return this;
    }

    @Step(value = "Check that exist profile stats")
    public MainPageSteps checkProfileStatsExist() {
        SelenideElement element = page.getStatsElement();
        element.shouldBe(visible)
               .shouldHave(text("Сообщения"))
               .shouldHave(text("Рейтинги"))
               .shouldHave(text("Баллы"));
        String[] statArray = element.getText().toLowerCase()
                                              .replaceAll("\\w", "")
                                              .replaceAll("[,./:]", "")
                                              .replaceAll("\n\n", "\n")
                                              .split("\n");;
        assertThat(3, equalTo(statArray.length));
        return this;
    }

    @Step(value = "Get profile dropdown menu")
    public MainPageSteps getProfileDropdownMenu() {
        page.getProfileDropdownMenu().click();
        return this;
    }

    @Step(value = "Click on \"Sign out\" button")
    public MainPageSteps clickSignOutButton() {
        page.getSignOutButton().click();
        return this;
    }

    @Step(value = "Click on \"Profile name\" link")
    public MainPageSteps clickProfileNameLink() {
        page.getProfileNameLink().click();
        return this;
    }

    @Step(value = "Set \"{0}\" in text-area input")
    public MainPageSteps setTextAreaInput(String text) {
        page.getTextAreaInput().setValue(text);
        return this;
    }

    @Step(value = "Click on \"Send status\" button")
    public MainPageSteps clickSendStatusButton() {
        page.getSendStatusButton().click();
        return this;
    }

    @Step(value = "Check that status contains sended text \"{0}\"")
    public MainPageSteps checkSendStatus(String sendedText) {
        String message = page.getLastSentStatus().getText();
        assertThat(message, equalTo(sendedText));
        return this;
    }

    @Step(value = "Try search {0}")
    public MainPageSteps searchFor(String sendedText) {
        page.getSearchInput().setValue(sendedText).pressEnter();
        return this;
    }

    @Step(value = "Check that search error \"Unavailable search\"")
    public MainPageSteps checkUnavailableSearchError() {
        String errorMessage = page.getSearchResult().getText();
        assertThat(errorMessage, equalTo("Поиск не может быть выполнен, так как слова в поисковом запросе слишком короткие, слишком длинные или слишком часто встречающиеся."));
        return this;
    }
    @Step(value = "Check that search error \"Without result\"")
    public MainPageSteps checkSearchWithoutResult() {
        String errorMessage = page.getSearchWithoutResult().getText();
        assertThat(errorMessage, equalTo("Ничего не найдено."));
        return this;
    }
}

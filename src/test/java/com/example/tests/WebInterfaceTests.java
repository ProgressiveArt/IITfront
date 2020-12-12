package com.example.tests;

import com.codeborne.selenide.testng.annotations.Report;
import com.example.TestBase;
import com.example.steps.AuthPageSteps;
import com.example.steps.MainPageSteps;
import io.qameta.allure.Story;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Test
@Report
public class WebInterfaceTests extends TestBase {

    @DataProvider(name = "urls")
    public Object[][] createData1() {
        return new Object[][]{{"http://forum.l2multi.ru/login", "http://forum.l2multi.ru"}};
    }

    @Story(value = "Test for signin with correct credentials. it20-75")
    @Test(groups = "positive case", dataProvider = "urls")
    public void checkSignIn(String authPageUrl, String mainPageUrl) {
        MainPageSteps mainPage = new MainPageSteps();
        mainPage.openPage();

        boolean isAuthorized = mainPage.checkIsAuthorized();

        if (isAuthorized){
            SignOut(mainPage, mainPageUrl);
        }

        AuthPageSteps authPageSteps = new AuthPageSteps();
        authPageSteps.openPage()
                .checkThatIsLoginPage(authPageUrl)
                .clearLogin()
                .clearPassword()
                .setLogin("playingtheangel")
                .setPassword("Harward794")
                .clickSignInButton();

        mainPage.openPage()
                .checkThatIsMainPage(mainPageUrl)
                .checkVisitorPanelExists()
                .checkChatExists()
                .checkProfileStatsExist();

        isAuthorized = mainPage.checkIsAuthorized();
        assertThat(isAuthorized, equalTo(true));
    }

    @Story(value = "Test for signout from profile. it20-76")
    @Test(groups = "positive case", dataProvider = "urls")
    public void checkSignOut(String authPageUrl, String mainPageUrl) {
        MainPageSteps mainPage = new MainPageSteps();
        mainPage.openPage();

        boolean isAuthorized = mainPage.checkIsAuthorized();

        if (!isAuthorized){
            SignIn(mainPage, authPageUrl, mainPageUrl);
        }

        mainPage.getProfileDropdownMenu();
        mainPage.clickSignOutButton();

        isAuthorized = mainPage.checkIsAuthorized();
        assertThat(isAuthorized, equalTo(false));
    }

    @Story(value = "Test for sending status in user profile. it20-77")
    @Test(groups = "positive case", dataProvider = "urls")
    public void checkSendingStatus(String authPageUrl, String mainPageUrl) {
        MainPageSteps mainPage = new MainPageSteps();
        mainPage.openPage();

        boolean isAuthorized = mainPage.checkIsAuthorized();

        if (!isAuthorized){
            SignIn(mainPage, authPageUrl, mainPageUrl);
        }

        String message = "I love l2multi!";
        mainPage.getProfileDropdownMenu()
                .clickProfileNameLink()
                .setTextAreaInput(message)
                .clickSendStatusButton()
                .checkSendStatus(message);
    }

    @Story(value = "Testing different behavior of the authorization form when entering" +
            " different incorrect data. it20-80")
    @Test(groups = "negative case", dataProvider = "urls")
    public void checkSignInErrors(String authPageUrl, String mainPageUrl) {
        MainPageSteps mainPage = new MainPageSteps();
        mainPage.openPage();

        boolean isAuthorized = mainPage.checkIsAuthorized();

        if (isAuthorized){
            SignOut(mainPage, mainPageUrl);
        }

        AuthPageSteps authPageSteps = new AuthPageSteps();
        authPageSteps.openPage()
                .checkThatIsLoginPage(authPageUrl)
                .clearLogin()
                .clearPassword()
                .setLogin("playingtheangels")
                .setPassword("Harward794")
                .clickSignInButton()
                .checkUnknownLoginError();

        authPageSteps.openPage()
                .checkThatIsLoginPage(authPageUrl)
                .clearLogin()
                .clearPassword()
                .setLogin("playingtheangel")
                .setPassword("Harward7943")
                .clickSignInButton()
                .checkInvalidPasswordError();

        authPageSteps.openPage()
                .checkThatIsLoginPage(authPageUrl)
                .clearLogin()
                .clearPassword()
                .setLogin("playingtheangels")
                .setPassword("")
                .clickSignInButton()
                .checkThatIsNotLoginPage();

        authPageSteps.openPage()
                .checkThatIsLoginPage(authPageUrl)
                .clearLogin()
                .clearPassword()
                .setLogin("")
                .setPassword("")
                .clickSignInButton()
                .checkThatIsNotLoginPage();
    }

    @Story(value = "Test for with incorrect value search result. it20-79")
    @Test(groups = "negative case", dataProvider = "urls")
    public void checkIncorrectSearch(String authPageUrl, String mainPageUrl) {
        MainPageSteps mainPage = new MainPageSteps();
        mainPage.openPage();

        boolean isAuthorized = mainPage.checkIsAuthorized();

        if (!isAuthorized){
            SignIn(mainPage, authPageUrl, mainPageUrl);
        }

        mainPage.searchFor("_");
        mainPage.checkUnavailableSearchError();
        mainPage.searchFor("qweqweqweqweqwe");
        mainPage.checkSearchWithoutResult();
    }

    private void SignIn(MainPageSteps mainPage, String authPageUrl, String mainPageUrl){
        AuthPageSteps authPageSteps = new AuthPageSteps();
        authPageSteps.openPage()
                .checkThatIsLoginPage(authPageUrl)
                .clearLogin()
                .clearPassword()
                .setLogin("playingtheangel")
                .setPassword("Harward794")
                .clickSignInButton();

        mainPage.checkThatIsMainPage(mainPageUrl)
                .checkVisitorPanelExists()
                .checkChatExists()
                .checkProfileStatsExist();
        boolean isAuthorized = mainPage.checkIsAuthorized();
        assertThat(isAuthorized, equalTo(true));
    }

    private void SignOut(MainPageSteps mainPage, String mainPageUrl){
        mainPage.openPage()
                .checkThatIsMainPage(mainPageUrl)
                .getProfileDropdownMenu()
                .clickSignOutButton();
        boolean isAuthorized = mainPage.checkIsAuthorized();
        assertThat(isAuthorized, equalTo(false));
    }
}

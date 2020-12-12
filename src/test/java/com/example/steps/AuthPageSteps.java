package com.example.steps;

import com.codeborne.selenide.SelenideElement;
import com.example.pages.AuthPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AuthPageSteps extends StepsBase<AuthPageSteps, AuthPage> {

    @Override
    protected AuthPage createPageInstance() {
        return new AuthPage();
    }

    @Step(value = "Check that login page")
    public AuthPageSteps checkThatIsLoginPage(String UniqueUrl){
        boolean isLogin = checkCurrentUrl(UniqueUrl);
        assertThat(isLogin, equalTo(true));
        page.getLoginForm().shouldBe(visible);
        page.getLoginInput().shouldBe(visible);
        page.getPasswordInput().shouldBe(visible);
        page.getSignInButton().shouldBe(visible);
        return this;
    }

    @Step(value = "Check that not login page")
    public AuthPageSteps checkThatIsNotLoginPage(){
        page.getLoginForm().shouldNot(visible);
        return this;
    }

    @Step(value = "Clear login input")
    public AuthPageSteps clearLogin() {
        page.getLoginInput().clear();
        return this;
    }

    @Step(value = "Clear password input")
    public AuthPageSteps clearPassword() {
        page.getPasswordInput().clear();
        return this;
    }

    @Step(value = "Fill in login with \"{0}\"")
    public AuthPageSteps setLogin(String value) {
        page.getLoginInput().setValue(value);
        return this;
    }

    @Step(value = "Fill in password with \"{0}\"")
    public AuthPageSteps setPassword(String value) {
        page.getPasswordInput().setValue(value);
        return this;
    }

    @Step(value = "Check that login error \"Unknown\"")
    public AuthPageSteps checkUnknownLoginError(){
        SelenideElement element = page.getLoginErrors();
        element.shouldBe(visible);

        String user = element.getText().split(" ")[2];
        element.shouldHave(text(String.format("Запрашиваемый пользователь %s не найден.", user)));
        return this;
    }

    @Step(value = "Check that login error \"Invalid password\"")
    public AuthPageSteps checkInvalidPasswordError(){
        page.getPasswordErrors().shouldBe(visible)
                .shouldHave(text("Неверный пароль. Пожалуйста, попробуйте ещё раз."));
        return this;
    }

    @Step(value = "Click on \"Sign in\" button")
    public AuthPageSteps clickSignInButton() {
        page.getSignInButton().click();
        return this;
    }
}

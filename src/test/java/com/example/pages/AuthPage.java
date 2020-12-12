package com.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.constants.Constants;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class AuthPage extends PageBase<AuthPage> {
    @Override
    public String getUrl() {
        return "http://forum.l2multi.ru/login";
    }

    @Override
    public AuthPage waitPageLoaded() {
        $(By.id("ctrl_pageLogin_login")).waitUntil(Condition.visible, Constants.PAGE_LOAD_TIME);
        return this;
    }

    public SelenideElement getLoginForm() {
        return $(By.id("pageLogin"));
    }

    public SelenideElement getLoginInput() {
        return $(By.id("ctrl_pageLogin_login"));
    }

    public SelenideElement getPasswordInput() {
        return $(By.id("ctrl_pageLogin_password"));
    }

    public SelenideElement getSignInButton() {
        return $("input.button.primary");
    }

    public SelenideElement getLoginErrors() {
        return $(By.className("errors"));
    }

    public SelenideElement getPasswordErrors() {
        return $(By.className("errors"));
    }
}

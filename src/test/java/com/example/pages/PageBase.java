package com.example.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.open;

public abstract class PageBase <T extends PageBase<T>> {

    public abstract String getUrl();


    public abstract T waitPageLoaded();


    public T navigate() {

        return (T) open(getUrl(), ((T)this).getClass()).waitPageLoaded();
    }

    public T assertOpened() {

        Assert.assertEquals(cleanseUrl(WebDriverRunner.url()), cleanseUrl(getUrl()));

        return (T) this;
    }

    private String cleanseUrl(String url) {
        String modifiedUri = url.split("[?]")[0];

        if ("/".equals(modifiedUri.substring(modifiedUri.length() - 1)))
            return modifiedUri.substring(0, modifiedUri.length() - 1);

        return modifiedUri;
    }
}

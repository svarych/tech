package com.tober.iotech;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class ProjectsPage {

    private WebElement projectLink;

    public ProjectsPage project(String project) {
        projectLink = $(By.xpath("//a/li[.='" + project + "']"));
        projectLink.click();
        return this;
    }

    public boolean selected() {
        return !$(projectLink).waitUntil(Condition.not(Condition.visible), 15000).is(Condition.visible);
    }

}

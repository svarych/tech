package com.tober.iotech;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ProjectsPage {

    public ProjectsPage(String project) {
        selectProject(project);
    }

    private void selectProject(String name) {
        $(By.xpath("//a/li[.='" + name + "']")).click();
    }
}

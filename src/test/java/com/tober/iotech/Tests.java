package com.tober.iotech;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Tests {

    @BeforeTest
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences preferences = new LoggingPreferences();
        preferences.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, preferences);
        options.addArguments("start-maximized");
        WebDriverRunner.setWebDriver(new ChromeDriver(options));
    }

    /**
     * Вход в систему
     */
    @Test
    void logIn() {
//        new AuthPage().email("gexibawer@one2mail.info").password("q").enter();
        open("https://onthe.io/auth");

        getWebDriver().switchTo().window(getWebDriver().getWindowHandles().toArray()[0].toString());
        $(By.xpath("//input[@name='email']")).setValue("my@email");

    }

    /**
     * Выбор проекта
     */
    @Test
    void selectProject() {
        logIn();
        new ProjectsPage(" RuHighload (prod)");
    }

    @Test
    void articleTest0() throws IOException {
        assert new ArticlePage().requestSent("Ошибка upstream");
    }

    @Test
    void articleTest1() throws IOException {
        new ArticlePage().requestSent("Ошибка 504");
    }

    @Test
    void articleTest2() throws IOException {
        new ArticlePage().requestSent("Как узнать версию Mysql");
    }
}

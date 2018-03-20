package com.tober.iotech;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ArticleTests {

    @BeforeTest
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences preferences = new LoggingPreferences();
        preferences.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, preferences);
        options.addArguments("start-maximized");
        options.addArguments("test-type");
        WebDriverRunner.setWebDriver(new ChromeDriver(options));
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

    @AfterTest
    void closeBrowserAfterMethod(){
        getWebDriver().quit();
    }
}

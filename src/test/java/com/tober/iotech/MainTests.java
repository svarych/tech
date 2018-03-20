package com.tober.iotech;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.assertTrue;

public class MainTests {

    @BeforeTest
    void setUp() {
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
    }

    /**
     * Вход в систему
     */
    @Test
    void logIn() {
        assertTrue(new AuthPage().email("gexibawer@one2mail.info").password("q").enter().loggedIn());
    }

    /**
     * Выбор проекта
     */
    @Test
    void selectProject() {
        logIn();
        assertTrue(new ProjectsPage().project(" RuHighload (prod)").selected());
    }

    @Test
    void changeReports() throws InterruptedException {
        selectProject();
        new HomePage().closePopUp().closeTab("Хайлоад")

                // 1. Меню: Переключение между отчетами (Home / Articles / Authors).
                .goToEditorial(HomePage.EditorialMenu.AUTHORS)
                .goToEditorial(HomePage.EditorialMenu.ARTICLES)
                .goToEditorial(HomePage.EditorialMenu.HOME)

                // 1. Меню: открытие TV Дашборда
                .goToReport(HomePage.ReportsMenu.FULL_SCREEN)
                .closeTab("Welcome");
    }

    @Test
    void changeTimeFrames() throws InterruptedException {
        selectProject();
        new HomePage().closePopUp().closeTab("Хайлоад")

                // Пройтись по таймфреймам
                .goToTimeFrame(HomePage.TimeFrames.MONTH)
                .goToTimeFrame(HomePage.TimeFrames.WEEK)
                .goToTimeFrame(HomePage.TimeFrames.YESTERDAY)
                .goToTimeFrame(HomePage.TimeFrames.TODAY)
                .goToTimeFrame(HomePage.TimeFrames.ONE_HOUR)
                .goToTimeFrame(HomePage.TimeFrames.TEN_MIN)
                .goToTimeFrame(HomePage.TimeFrames.REAL_TIME);
    }

    @Test
    void filterByAuthors() throws InterruptedException {
        selectProject();
        new HomePage().closePopUp().closeTab("Хайлоад")

                // Проверям что статьи принадлежат заявленным авторам
                .goToEditorial(HomePage.EditorialMenu.AUTHORS)
                .checkAuthors(HomePage.TimeFrames.MONTH);
    }

    @Test
    void sortingVariants() throws InterruptedException {
        selectProject();
        new HomePage().closePopUp().closeTab("Хайлоад")

                // Фильтр по авторам за месяц
                .goToEditorial(HomePage.EditorialMenu.AUTHORS)
                .goToTimeFrame(HomePage.TimeFrames.MONTH)

                // Проверка лучших/худших авторов по метрикам
                .checkBestWorst(HomePage.Metric.FINISHED_READING)
                .checkBestWorst(HomePage.Metric.PAGE_VIEWS);
    }

    @AfterMethod
    void closeBrowser() {
        getWebDriver().quit();
    }
}

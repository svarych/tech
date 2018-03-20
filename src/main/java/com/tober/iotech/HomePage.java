package com.tober.iotech;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class HomePage {

    private int views;

    private WebElement infoWidget = $(By.className("widget-overlay__popup"));

    public HomePage closePopUp() {
        if ($(infoWidget).isDisplayed()) {
            $(By.xpath("//button[.='Continue']")).click();
        }
        return this;
    }

    public HomePage goToEditorial(EditorialMenu item) {
        if (item == EditorialMenu.HOME) {
            goEditorial("//div[@qa-id='home']", "data_home", "Home");
        }
        if (item == EditorialMenu.ARTICLES) {
            goEditorial("//div[@qa-id='articles']", "data_articles", "Articles");
        }
        if (item == EditorialMenu.AUTHORS) {
            goEditorial("//div[@qa-id='authors']", "data_authors", "Authors");
        }
        if (item == EditorialMenu.PAGES_CTR) {
            goEditorial("//div[@qa-id='blocks']", "data_blocks", "Pages CTR");
        }
        return this;
    }

    public HomePage goToReport(ReportsMenu item) {
        if (item == ReportsMenu.FULL_SCREEN) {
            $(By.className("reprots_menu")).find(By.className("fullscreen_icon")).click();
            getWebDriver().switchTo().window(getWebDriver().getWindowHandles().toArray()[1].toString());
            $(By.className("report-desc")).waitUntil(Condition.visible, 10000);
        }
        return this;
    }

    public HomePage goToTimeFrame(TimeFrames item) {
        if (item == TimeFrames.REAL_TIME) {
            goTimeFrame("//div[@data-period='realtime']");
        }
        if (item == TimeFrames.TEN_MIN) {
            goTimeFrame("//div[@data-period='now']");
        }
        if (item == TimeFrames.ONE_HOUR) {
            goTimeFrame("//div[@data-period='hour']");
        }
        if (item == TimeFrames.TODAY) {
            goTimeFrame("//div[@data-period='today']");
        }
        if (item == TimeFrames.YESTERDAY) {
            goTimeFrame("//div[@data-period='yesterday']");
        }
        if (item == TimeFrames.WEEK) {
            goTimeFrame("//div[@data-period='week']");
        }
        if (item == TimeFrames.MONTH) {
            goTimeFrame("//div[@data-period='month']");
        }
        return this;
    }

    private void goTimeFrame(String path) {
        $(By.xpath(path)).click();
        $(By.className("loader_container")).waitUntil(Condition.disappear, 15000);
    }

    private void goEditorial(String path, String className, String menuItemName) {
        $(By.xpath(path)).click();
        assert getWebDriver().getTitle().contains(menuItemName);
        $(By.className(className)).waitUntil(Condition.visible, 20000);
    }

    private String clear(String input) {
        return input
                .replace(".", "")
                .replace(",", "");
    }

    public HomePage closeTab(String title) throws InterruptedException {
        for (String tab : getWebDriver().getWindowHandles()) {
            getWebDriver().switchTo().window(tab);

            waitForTitle();
            if (getWebDriver().getTitle().contains(title)) {
                getWebDriver().close();
            }
            getWebDriver().switchTo().window(getWebDriver().getWindowHandles().toArray()[0].toString());
        }
        return this;
    }

    public HomePage checkAuthors(TimeFrames time) {
        goToTimeFrame(time);
        $(By.className("data_regular_authors__list")).waitUntil(Condition.appear, 5000);
        ArrayList<WebElement> root = new ArrayList<>($$(By.className("data_regular_authors__list-item")));
        for (WebElement author : root) {
            try {
                for (WebElement name : author.findElements(By.className("name"))) {
                    String authorName = name.getText();
                    name.click();
                    $(name).waitUntil(Condition.disappear, 5000);
                    for (WebElement o : $$(By.xpath("//div[@data-tooltip='Author']"))) {
                        assert !o.isDisplayed() || o.getText().equals(authorName);
                    }
                    goToEditorial(EditorialMenu.AUTHORS);
                    goToTimeFrame(time);
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public HomePage checkBestWorst(Metric metric) {

        selectMetric(metric);

        WebElement author = $(By.xpath("(//div[@class='row']//div[@class='name'])[1]"));
        String best;
        String worst;

        // Если авторов больше одного
        if ($$(By.xpath("//div[@class='row']//div[@class='name']")).size() > 1) {
            // Лучший автор
            best = $(author).getText();
            System.out.println(metric);
            System.out.println("Best is: " + best);

            // Переключаем слайдер
            $(By.className("slider")).click();

            // Лучший автор должен исчезнуть с первой позиции в списке худших
            $(author).shouldNotBe(Condition.text(best));

            // Худший автор
            worst = $(author).getText();
            System.out.println("Worst is: " + worst);
        }
        return this;
    }

    public enum EditorialMenu {
        HOME, ARTICLES, AUTHORS, PAGES_CTR
    }

    public enum ReportsMenu {
        SETTINGS, FULL_SCREEN, ALERTS
    }

    public enum TimeFrames {
        REAL_TIME, TEN_MIN, ONE_HOUR, TODAY, YESTERDAY, WEEK, MONTH
    }

    public enum Metric {
        FB_TOTAL, FINISHED_READING, PAGE_VIEWS
    }

    private void selectMetric(Metric metric) {
        $(By.className("filter_title_value")).click();

        if (metric == Metric.PAGE_VIEWS) {
            $(By.xpath("//div[@data-name='pageviews']")).should(Condition.visible).click();
        }

        if (metric == Metric.FB_TOTAL) {
            $(By.xpath("//div[@data-name='fb']")).should(Condition.visible).click();
        }

        if (metric == Metric.FINISHED_READING) {
            $(By.xpath("//div[@data-name='readability']")).should(Condition.visible).click();
        }
    }

    private void waitForTitle() throws InterruptedException {
        while (getWebDriver().getTitle().isEmpty()){
            Thread.sleep(100);
        }
    }
}
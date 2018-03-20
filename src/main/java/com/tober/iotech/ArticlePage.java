package com.tober.iotech;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ArticlePage {

    public boolean requestSent(String publication) throws IOException {
        open("https://ruhighload.com");
        $(By.xpath("//a[contains(text(),'" + publication + "')]")).click();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        StringBuilder status = new StringBuilder();

        for (LogEntry entry : getWebDriver().manage().logs().get(LogType.PERFORMANCE).getAll()) {
            if (entry.getMessage().contains("https://tt.onthe.io/?k[]=28:pageviews[domain:ruhighload.com,url:")) {
                node = mapper.readValue(entry.getMessage(), ObjectNode.class);

                if (node.findValue("method").toString().equals("\"Network.requestWillBeSent\"")) {
                    status.append("O");
                }
                if (node.findValue("method").toString().equals("\"Network.responseReceived\"")) {
                    status.append("K");
                }
            }
        }
        return status.toString().startsWith("OK");
    }
}

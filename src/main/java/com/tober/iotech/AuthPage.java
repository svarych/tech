package com.tober.iotech;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthPage {

    public AuthPage() {
        open("https://onthe.io/auth");
    }

    public AuthPage email(String email) {
        $(By.name("email")).setValue(email);
        return this;
    }

    public AuthPage password(String password) {
        $(By.name("pwd")).setValue(password);
        return this;
    }

    public AuthPage enter() {
        $(By.id("auth")).submit();
        return this;
    }

    public boolean loggedIn(){
        $(By.tagName("h1")).waitUntil(Condition.text("Welcome to .io, "), 10000);
        return true;
    }
}

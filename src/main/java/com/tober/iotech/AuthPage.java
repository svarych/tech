package com.tober.iotech;

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

    public void enter() {
        $(By.id("auth")).submit();
    }
}

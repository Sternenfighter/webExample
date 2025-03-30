package eu.sternenfighter.example.pages;

import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public String getUrl() {
        return webDriver.getCurrentUrl();
    }
}

package eu.sternenfighter.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private final WebDriver webDriver;
    @FindBy(id = "login:username")
    private WebElement username;
    @FindBy(id = "login:password")
    private WebElement password;
    @FindBy(id = "login:submit")
    private WebElement submit;
    @FindBy(id = "login:error")
    private WebElement error;

    public LoginPage (WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    /**
     * Fill the input fields on the Login page and click submit
     * @param username username filled in
     * @param password password filled in
     * @return HomePage in case of success
     */
    public HomePage submit(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        submit.submit();
        return new HomePage(webDriver);
    }

    public String getErrorText() {
        return error.getText();
    }
}

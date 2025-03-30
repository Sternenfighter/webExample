package eu.sternenfighter.example.it;

import eu.sternenfighter.example.pages.HomePage;
import eu.sternenfighter.example.pages.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @LocalServerPort
    private Integer port;
    private WebDriver webDriver;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void beforeEach() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless"); // Run in headless mode
        chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.get("http://localhost:" + port + "/login.xhtml");
    }

    @AfterEach
    void afterEach() {
        webDriver.close();
        webDriver.quit();
        webDriver = null;
    }

    @Test
    void failedLogin() {
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.submit("admin", "admin");
        assertEquals("Login failed!", loginPage.getErrorText());
    }

    @Test
    void successLogin() {
        LoginPage loginPage = new LoginPage(webDriver);
        HomePage homePage = loginPage.submit("admin", "123");
        assertEquals("http://localhost:" + port + "/home.xhtml", homePage.getUrl());
        assertEquals("Home", homePage.getTitle());
    }
}

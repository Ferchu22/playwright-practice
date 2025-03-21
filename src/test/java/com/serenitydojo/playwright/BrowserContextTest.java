package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class BrowserContextTest {
    // Se agrego private static a las 3 primeras y se agrego BrowserContext
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;
    private static BrowserContext browserContext;

    @BeforeAll  // cambiamos de BeforeEach a BeforeAll
    public static void setupBrowser(){    // cambiamos de void a public static void
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu"))
        );
        browserContext = browser.newContext();  // nuevo, no entendi bien
    }

    @BeforeEach
    public void setup(){
        page = browserContext.newPage();
    }
    @AfterAll   // tambien cambio de AfterEach a AfterAll
    public static void teardown(){
        browser.close();
        playwright.close();
    }

    @Test
    void shouldShowThePageTitle(){
        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));
    }

    @Test
    void shouldSearchByKeyword(){
        page.navigate("https://practicesoftwaretesting.com/");

        page.locator("[placeholder=Search]").fill("pliers");
        page.locator("button:has-text('Search')").click();

        int matchingSearchResults = page.locator(".card").count();

        Assertions.assertTrue(matchingSearchResults > 0);
    }
}

package com.serenitydojo.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UsePlaywright  // Es una forma mejor de limpiar el codigo
public class ABetterPlaywrightTest {
    /*   // Con el uso del @UsePlaywright podemos borrar este codigo y solo agregar "page" parametros en los tests
         // Agregando (Page page) a cada uno.
    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    void setup(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        page = browser.newPage();
    }

    @AfterEach
    void teardown(){
        browser.close();
        playwright.close();
    }
     */

    @Test
    void shouldShowThePageTitle(Page page){
        /*Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        */ // se metio el codigo a @BeforeEach

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));

        /*
        browser.close();
        playwright.close();
        */ // Se metio el codigo a @AfterEach
    }

    @Test
    void shouldSearchByKeyword(Page page){

        page.navigate("https://practicesoftwaretesting.com/");

        page.locator("[placeholder=Search]").fill("pliers");
        page.locator("button:has-text('Search')").click();

        int matchingSearchResults = page.locator(".card").count();

        Assertions.assertTrue(matchingSearchResults > 0);
    }
}
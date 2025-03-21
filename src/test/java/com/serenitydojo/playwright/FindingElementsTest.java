package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

@UsePlaywright
public class FindingElementsTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    void setup(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu"))
        );
        playwright.selectors().setTestIdAttribute("data-test");
        page = browser.newPage();
    }



    @Test
    void findElementByText(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Bolt Cutters").click();
        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @Test
    void findElementByAlt(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByAltText("Combination Pliers").click();
        PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
    }

    @Test
    void findElementByUsingTitle(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByAltText("Combination Pliers").click();

        page.getByTitle("Practice Software Testing - Toolshop").click();
    }

    @Test
    void findElementsWithLabels(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Contact").click();
        page.getByLabel("First name").fill("Obi-Wan");
        page.getByPlaceholder("Your last name").fill("Kenobi");
    }

    @Test
    void findElementByRole(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByAltText("Combination Pliers").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
        page.getByTitle("Practice Software Testing - Toolshop").click();
    }

    @Test
    void findElementsByIDLocator(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Contact").click();
        page.locator("#first_name").fill("Sarah-Jane");  //by Id locator
        PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("Sarah-Jane");
    }

    @Test
    void findElementsByCSSClassSelector(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Contact").click();
        page.locator("#first_name").fill("Sarah-Jane");
        page.locator(".btnSubmit").click();   // using CSS class locator
        List<String> alertMessages = page.locator(".alert").allTextContents(); // metemos los alerts en esta lista de tipo stg
        Assertions.assertTrue(!alertMessages.isEmpty());
    }

    @Test
    void findElementsByAttribute(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Contact").click();
        page.locator("[placeholder = 'Your last name *']").fill("Smith");
        PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Smith");
    }

    // Solo funcionara si el HTML fue hecho basado en ARIA conventions.

    @Test
    void findingNestedElements(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByText("Contact").click();
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Home")).click(); // basado en ARIA conventions.
        //.getByText("Home").click();  //Si no tiene ARIA conventions

        String pageURL = page.url();
        System.out.println("Practice Software testing "+ pageURL);
    }

    @Test
    void searchForPliers(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByPlaceholder("Search").fill("Pliers");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();

        PlaywrightAssertions.assertThat(page.locator(".card")).hasCount(4);

        List<String> productNames = page.getByTestId("product-name").allTextContents();
        //Assertions.assertThat(productNames).allMatch(name-> name.contains("Pliers"));
    }

    @Test
    void findElementByTestId(Page page){
        page.navigate("https://practicesoftwaretesting.com/");
        page.getByTestId("search-query").fill("Pliers");
        page.getByTestId("search-submit").click();
    }


}
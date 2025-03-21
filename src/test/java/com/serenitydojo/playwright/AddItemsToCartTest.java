package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.*;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@UsePlaywright(HeadlessChromeOptions.class)
public class AddItemsToCartTest {

    @Test
    void shouldShowThePageTitle(Page page){

        page.navigate("https://practicesoftwaretesting.com/");
        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));

    }

    @Test
    void shouldSearchByKeyword(Page page){

        page.navigate("https://practicesoftwaretesting.com/");
        page.locator("[placeholder=Search]").fill("pliers");
        page.locator("button:has-text('Search')").click();

        int matchingSearchResults = page.locator(".card").count();

        Assertions.assertTrue(matchingSearchResults > 0);
    }

    @Test
    void completeForm(Page page) throws URISyntaxException {

        page.navigate("https://practicesoftwaretesting.com/contact");
        var firstNameField = page.getByLabel("First name");
        var lastNameField = page.getByLabel("Last name");
        var emailField = page.getByLabel("Email address");
        var messageText = page.locator("#message");
        var subjectField = page.getByLabel("Subject");
        var uploadField = page.getByLabel("Attachment");

        //  Normal input fields
        firstNameField.fill("Sarah-Jane");
        lastNameField.fill("Smith");
        emailField.fill("sarah-jane@example.com");
        messageText.fill("This message is just a TEST");
        subjectField.selectOption(new SelectOption().setIndex(5));  // Dropdownn option
        //subjectField.selectOption("Warranty");   // la opcion mas comun
        Path fileToUpload = Paths.get(ClassLoader.getSystemResource("data/sample-data.txt").toURI());

        page.setInputFiles("#attachment",fileToUpload);


        PlaywrightAssertions.assertThat(firstNameField).hasValue("Sarah-Jane");
        PlaywrightAssertions.assertThat(lastNameField).hasValue("Smith");
        PlaywrightAssertions.assertThat(emailField).hasValue("sarah-jane@example.com");
        PlaywrightAssertions.assertThat(messageText).hasValue("This message is just a TEST");
        PlaywrightAssertions.assertThat(subjectField).hasValue("warranty");

        String uploadedFile = uploadField.inputValue();
        org.assertj.core.api.Assertions.assertThat(uploadedFile).endsWith("sample-data.txt");
        //PlaywrightAssertions.assertThat(uploadedFile).endsWith("sample-data.txt");
    }

    @Test
    void mandatoryFields(Page page) {

        page.navigate("https://practicesoftwaretesting.com/contact");
        var firstNameField = page.getByLabel("First name");
        var lastNameField = page.getByLabel("Last name");
        var emailField = page.getByLabel("Email address");
        var messageText = page.locator("#message");
        var subjectField = page.getByLabel("Subject");
        var sendButton = page.getByText("Send");

        sendButton.click();

        var errorMessageFirstName = page.getByRole(AriaRole.ALERT).getByText("First name is required");
        PlaywrightAssertions.assertThat(errorMessageFirstName).isVisible();

        var errorMessageLastName = page. getByRole(AriaRole.ALERT).getByText("Last name is required");
        PlaywrightAssertions.assertThat(errorMessageLastName).isVisible();
    }

    /*
    @ParameterizedTest  // Usando el Junit ParameterizedTest, para no tener que poner uno a uno las validaciones.
    @ValueSource(strings = {"First name", "Last name", "Email address" ," Message"})
    void mandatoryFields(String fieldName) {

        page.navigate("https://practicesoftwaretesting.com/contact");
        var firstNameField = page.getByLabel("First name");
        var lastNameField = page.getByLabel("Last name");
        var emailField = page.getByLabel("Email address");
        var messageText = page.locator("#message");
        var sendButton = page.getByText("Send");

        // Fill in the fields
        firstNameField.fill("Sarah-Jane");
        lastNameField.fill("Smith");
        emailField.fill("sarah-jane@example.com");
        messageText.fill("This message is just a TEST");

        // clear one of them
        page.getByLabel(fieldName).clear();
        sendButton.click();

        // Check error message for that field

        var errorMessage = page.getByRole(AriaRole.ALERT).getByText(fieldName +" is required");
        PlaywrightAssertions.assertThat(errorMessage).isVisible();
    }
     */
    //OJO!!
}

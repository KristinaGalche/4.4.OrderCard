package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderTest {

    @BeforeEach
    public void openApp() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSendFormValid() {
        $x("//input[@placeholder=\"Город\"]").val("Владивосток");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Иванова Дарья");
        $x("//input[@name=\"phone\"]").val("+79509992255");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//*[contains(text(), 'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[contains(text(), 'Встреча успешно забронирована')]").should(exactText("Встреча успешно забронирована на " + dateOfMeeting));
    }

    @Test
    public void invalidCity() {
        $x("//input[@placeholder=\"Город\"]").val("Уссурийск");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Иванова Дарья");
        $x("//input[@name=\"phone\"]").val("+79509992255");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=city] .input__sub").should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void invalidName() {
        $x("//input[@placeholder=\"Город\"]").val("Владивосток");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Elena");
        $x("//input[@name=\"phone\"]").val("+79509992255");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=name] .input__sub").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    public void invalidPhone() {
        $x("//input[@placeholder=\"Город\"]").val("Владивосток");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Иванова Дарья");
        $x("//input[@name=\"phone\"]").val("9509992255");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void invalidPhone2() {
        $x("//input[@placeholder=\"Город\"]").val("Владивосток");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Иванова Дарья");
        $x("//input[@name=\"phone\"]").val("89509992255");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldntSendFormNoAgree() {
        $x("//input[@placeholder=\"Город\"]").val("Владивосток");
        String dateOfMeeting = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Иванова Дарья");
        $x("//input[@name=\"phone\"]").val("+79509992255");
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=agreement] .checkbox__text").should(exactText("Я соглашаюсь с условиями обработки и использования  моих персональных данных"));
    }

    @Test
    public void emptyForm() {
        $x("//input[@placeholder=\"Город\"]").val("Владивосток");
        String dateOfMeeting = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeeting);
        $x("//input[@name=\"name\"]").val("Иванова Дарья");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub").should(exactText("Поле обязательно для заполнения"));
    }

}

package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController userController = new UserController();

    // Валидные юзеры
    static User validUser, validUser1, blankName;
    // Невалидные юзеры для создания
    static User invalidEmail, blankLogin, invalidBirthdate;
    // Невалидные юзеры для обновления
    static User newInvalidEmail, newBlankLogin, newInvalidBirthdate;

    @BeforeEach
    void beforeTests() {
        validUser = new User(1, "aa@aa.ru", "aa", "Mike", LocalDate.of(2000, 2, 2));
        validUser1 = new User(2, "oo@oo.ru", "tt", "Sandy", LocalDate.of(1990, 10, 10));
        blankName = new User(3, "ee@ee.com", "loginShouldBeName", "", LocalDate.of(1900, 3, 3));
        invalidEmail = new User(4, "isThisCorrectEmail", "qwerty", "Piter", LocalDate.of(2001, 2, 3));
        blankLogin = new User(5, "ff@ff.ru", " ", "Joe", LocalDate.of(2020, 5, 5));
        invalidBirthdate = new User(6, "gg@gg.ru", "op", "John", LocalDate.of(2022, 5, 15));
        newInvalidEmail = new User(1, "isThisCorrectEmail", "qwerty", "Piter", LocalDate.of(2001, 2, 3));
        newBlankLogin = new User(1, "ads@ads.ru", "", "Artur", LocalDate.of(1987, 2, 18));
        newInvalidBirthdate = new User(1, "sss@sss.ru", "JJ", "Andrew", LocalDate.of(2025, 1, 1));

        userController.clearMap();
    }

    @Test
    void getAll() throws ValidationException {
        userController.addUser(validUser);
        assertEquals(1, userController.getAll().size(), "должен быть один юзер");
        userController.addUser(validUser1);
        assertEquals(2, userController.getAll().size(), "должно быть два юзера");
        userController.addUser(blankName);
        assertEquals(3, userController.getAll().size(), "должно быть три юзера");
    }

    @Test
    void addUser() {
        assertThrows(ValidationException.class, () -> userController.addUser(invalidEmail), "invalid email");
        assertThrows(ValidationException.class, () -> userController.addUser(blankLogin), "blank login");
        assertThrows(ValidationException.class, () -> userController.addUser(invalidBirthdate), "invalid birthdate");
        assertEquals(0, userController.getAll().size(), "созданы невалидные юзеры");
    }

    @Test
    void updateUser() throws ValidationException {
        userController.addUser(validUser);
        assertThrows(ValidationException.class, () -> userController.updateUser(newInvalidEmail), "invalid email");
        assertThrows(ValidationException.class, () -> userController.updateUser(newBlankLogin), "blank login");
        assertThrows(ValidationException.class, () -> userController.updateUser(newInvalidBirthdate), "invalid birthdate");
        assertEquals(1, userController.getAll().size(), "обновлены невалидные юзеры");
    }
}
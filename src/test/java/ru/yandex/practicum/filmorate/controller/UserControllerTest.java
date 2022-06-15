package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
//import ru.yandex.practicum.filmorate.storage.user.impl.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    /*InMemoryUserStorage userStorage = new InMemoryUserStorage();
    UserService userService = new UserService(userStorage, friendsStorage);
    UserController userController = new UserController(userService);

    // Валидные юзеры
    static User validUser, validUser1, blankName;
    // Невалидные юзеры для создания
    static User loginWithSpaces, blankLogin, invalidBirthdate;
    // Невалидные юзеры для обновления
    static User newLoginWithSpaces, newBlankLogin, newInvalidBirthdate;

    @BeforeEach
    void beforeTests() {
        validUser = new User("aa", "Mike", 1,"Mike@gmail.com",LocalDate.of(2000, 2, 2), Set.of());
        validUser1 = new User("oo@edd.ru", "tt", 2, "Sandy@ya.ru", LocalDate.of(1990, 10, 10), Set.of());
        blankName = new User("loginShouldBecomeName", "", 2, "qwerty@qwerty.ru", LocalDate.of(1900, 3, 3), Set.of());
        loginWithSpaces = new User("agf dgs", "qwefty", 3, "Peter@yahoo.com", LocalDate.of(2001, 2, 3), Set.of());
        blankLogin = new User(" ", "ff", 4, "Joe@mail.ru", LocalDate.of(2020, 5, 5), Set.of());
        invalidBirthdate = new User("gg", "op", 5, "John@list.ru", LocalDate.of(2022, 5, 15), Set.of());
        newLoginWithSpaces = new User("gjjgjg gjgjgj", "qwerty", 0, "Peter@pringles.com", LocalDate.of(2001, 2, 3), Set.of());
        newBlankLogin = new User(" ", "GG", 0, "Artur@yandex-team.ru", LocalDate.of(1987, 2, 18), Set.of());
        newInvalidBirthdate = new User("sss", "JJ", 0, "Andrew@bk.ru", LocalDate.of(2025, 1, 1), Set.of());

        userStorage.clearMap();
    }

    @Test
    void getAll() throws ValidationException {
        userController.addUser(validUser);
        assertEquals(1, userController.getAllUsers().size(), "должен быть один юзер");
        userController.addUser(validUser1);
        assertEquals(2, userController.getAllUsers().size(), "должно быть два юзера");
        userController.addUser(blankName);
        assertEquals(3, userController.getAllUsers().size(), "должно быть три юзера");
    }

    @Test
    void addUser() {
        assertThrows(ValidationException.class, () -> userController.addUser(loginWithSpaces), "login with spaces");
        assertThrows(ValidationException.class, () -> userController.addUser(blankLogin), "blank login");
        assertThrows(ValidationException.class, () -> userController.addUser(invalidBirthdate), "invalid birthdate");
        assertEquals(0, userController.getAllUsers().size(), "созданы невалидные юзеры");
    }

    @Test
    void updateUser() throws ValidationException {
        userController.addUser(validUser);
        assertThrows(ValidationException.class, () -> userController.updateUser(newLoginWithSpaces), "login with spaces");
        assertThrows(ValidationException.class, () -> userController.updateUser(newBlankLogin), "blank login");
        assertThrows(ValidationException.class, () -> userController.updateUser(newInvalidBirthdate), "invalid birthdate");
        assertNotEquals(validUser, newLoginWithSpaces, "обновлен невалидный логин: пробелы");
        assertNotEquals(validUser, newBlankLogin, "обновлен невалидный логин: пустой");
        assertNotEquals(validUser, newInvalidBirthdate, "обновлена невалидная дата рождения");
    }
}*/
}
package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@RestController
@Slf4j
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public HashMap<Integer, User> getAll() {
        return users;
    }

    @PostMapping("/users")
    public void addUser(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthdate().isAfter(LocalDate.now()) || user.getLogin().contains(" ")) {
            log.debug("Ошибка валидации по запросу POST /user");
            throw new ValidationException();
        } else if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Добавлен новый пользователь: {}", user);
        users.put(user.getUserId(), user);
    }

    @PutMapping("/users")
    public void updateUser(@Valid @RequestBody User changedUser) throws ValidationException {
        if (changedUser.getBirthdate().isAfter(LocalDate.now()) || changedUser.getLogin().contains(" ")) {
            log.debug("Ошибка валидации по запросу PUT /user");
            throw new ValidationException();
        } else if (changedUser.getName().isBlank()) {
            changedUser.setName(changedUser.getName());
        }
        log.debug("Данные пользователя {} успешно обновлены", changedUser.getName());
        User savedUser = users.get(changedUser.getUserId());
        savedUser.setName(changedUser.getName());
        savedUser.setEmail(changedUser.getEmail());
        savedUser.setBirthdate(changedUser.getBirthdate());
        savedUser.setLogin(changedUser.getLogin());
    }

    // метод для очистки мапы. нужен для тестов
    public void clearMap() {
        users.clear();
    }
}

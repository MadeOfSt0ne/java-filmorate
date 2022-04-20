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

@RestController
@Slf4j
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping("/users")
    public HashMap<Integer, User> getAll() {
        return users;
    }

    @PostMapping("/users")
    public void addUser(@Valid @RequestBody User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())
                || user.getLogin().contains(" ")
                || !user.getEmail().contains("@")) {
            log.debug("Ошибка валидации по запросу POST /user");
            throw new ValidationException();
        } else if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Добавлен новый пользователь: {}, {}", id, user);
        users.put(id, user);
        id++;
    }

    @PutMapping("/users")
    public void updateUser(@Valid @RequestBody User changedUser) throws ValidationException {
        if (!users.containsKey(changedUser.getId())) {
            log.debug("пользователь не найден. id: {}", changedUser.getId());
            return;
        }
        if (changedUser.getBirthday().isAfter(LocalDate.now())
                || changedUser.getLogin().contains(" ")
                || !changedUser.getEmail().contains("@")) {
            log.debug("Ошибка валидации по запросу PUT /user");
            throw new ValidationException();
        } else if (changedUser.getName().isBlank()) {
            changedUser.setName(changedUser.getName());
        }
        log.debug("Данные пользователя {} успешно обновлены", changedUser.getName());
        User savedUser = users.get(changedUser.getId());
        savedUser.setName(changedUser.getName());
        savedUser.setEmail(changedUser.getEmail());
        savedUser.setBirthday(changedUser.getBirthday());
        savedUser.setLogin(changedUser.getLogin());
    }

    // метод для очистки мапы. нужен для тестов
    public void clearMap() {
        users.clear();
        id = 1;
    }
}

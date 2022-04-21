package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
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
    public List<User> getAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    public void addUser(@Valid @RequestBody User user) throws ValidationException {
        if (isInvalid(user)) {
            log.debug("Ошибка валидации по запросу POST /user");
            throw new ValidationException();
        }
        checkName(user);
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
        if (isInvalid(changedUser)) {
            log.debug("Ошибка валидации по запросу PUT /user");
            throw new ValidationException();
        }
        checkName(changedUser);
        log.debug("Данные пользователя {} успешно обновлены", changedUser.getName());
        User savedUser = users.get(changedUser.getId());
        savedUser.setName(changedUser.getName());
        savedUser.setEmail(changedUser.getEmail());
        savedUser.setBirthday(changedUser.getBirthday());
        savedUser.setLogin(changedUser.getLogin());
    }

    // метод для проверки имени: если имя пустое, используется логин
    private void checkName(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    // метод для валидации юзера
    private boolean isInvalid(User user) {
        return (user.getBirthday().isAfter(LocalDate.now())
                || user.getLogin().contains(" ")
                || !user.getEmail().contains("@"));
    }

    // метод для очистки мапы. нужен для тестов
    public void clearMap() {
        users.clear();
        id = 1;
    }
}

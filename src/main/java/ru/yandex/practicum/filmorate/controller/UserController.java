package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        log.debug("Текущее количество пользователей: {}", userService.getAllUsers().size());
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        return userService.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<Long> getFriends(@PathVariable int id) {
        return userService.findFriendsById(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<Long> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }

}

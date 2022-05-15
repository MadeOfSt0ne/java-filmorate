package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    // добавление пользователя
    User addUser(User user);
    // обновление пользователя
    User updateUser(User user);
    // поиск пользователя
    User getUser(long id);
    // список всех пользователей
    List<User> getAllUsers();
    // удаление пользователя
    void deleteUser(long id);
}

package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> users = new HashMap<>();
    private static long id = 1;

    private static long getNextId() {
        return id++;
    }

    @Override
    public User addUser(User user) {
        if (isInvalid(user)) {
            throw new ValidationException("Невалидные данные");
        }
        checkName(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User changedUser) {
        if (!users.containsKey(changedUser.getId())) {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        if (isInvalid(changedUser)) {
            throw new ValidationException("Невалидные данные!");
        }
        checkName(changedUser);
        User savedUser = users.get(changedUser.getId());
        savedUser.setName(changedUser.getName());
        savedUser.setEmail(changedUser.getEmail());
        savedUser.setBirthday(changedUser.getBirthday());
        savedUser.setLogin(changedUser.getLogin());
        savedUser.setFriends(changedUser.getFriends());
        return savedUser;
    }

    @Override
    public void deleteUser(long userId) {
        users.remove(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(long userId) {
        if (userId < 0 || !users.containsKey(userId)) {
            throw new UserNotFoundException("User not found");
        }
        return users.get(userId);
    }

    // метод для проверки имени: если имя пустое, используется логин
    private void checkName(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    // метод для валидации юзера
    private boolean isInvalid(User user) {
         return user.getBirthday().isAfter(LocalDate.now())
                || user.getLogin().contains(" ")
                || !user.getEmail().contains("@");
    }

    // вспомогательный метод для очистки таблицы
    public void clearMap() {
        users.clear();
        id = 0;
    }

}

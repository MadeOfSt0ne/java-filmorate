package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // добавление нового друга
    public void addFriend(long id, long newFriendId) {
        checkUserAndFriend(id, newFriendId);
        User user = userStorage.getUser(id);
        user.getFriends().add(newFriendId);
        userStorage.updateUser(user);
    }

    // удаление друга
    public void removeFriend(long id, long friendToRemoveId) {
        checkUserAndFriend(id, friendToRemoveId);
        User user = userStorage.getUser(id);
        user.getFriends().remove(friendToRemoveId);
        userStorage.updateUser(user);
    }

    // поиск общих друзей для двух пользователей
    public Collection<Long> getCommonFriends(int user1, int user2) {
        return findFriendsById(user1).stream()
                .distinct()
                .filter(findFriendsById(user2)::contains)
                .collect(Collectors.toSet());
    }

    // получение всех пользователей
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    // создание пользователя
    public User createUser(User user) {
        return userStorage.addUser(user);
    }

    // обновление пользователя
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    // поиск по id
    public User findUserById(int id) {
        return userStorage.getUser(id);
    }

    // получение списка друзей
    public Collection<Long> findFriendsById(int id) {
        return userStorage.getUser(id).getFriends();
    }

    // удаление пользователя
    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }

    // метод для проверки существования юзера и друга
    public void checkUserAndFriend(long userId, long friendId) {
        if (userStorage.getUser(userId) == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        if (userStorage.getUser(friendId) == null) {
            throw new UserNotFoundException("Друг не найден!");
        }
    }
}

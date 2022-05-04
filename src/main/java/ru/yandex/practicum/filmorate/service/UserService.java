package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private InMemoryUserStorage inMemoryUserStorage;

    private final UserStorage userStorage;
    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
    public void addFriend(int id, long newFriendId) {
        userStorage.getUser(id).getFriends().add(newFriendId);
    }

    public void removeFriend(int id, long friendToRemoveId) {
        inMemoryUserStorage.getUser(id).getFriends().remove(friendToRemoveId);
    }

    public Collection<Long> getCommonFriends(int user1, int user2) {
        return inMemoryUserStorage.getFriendsById(user1).stream()
                .distinct()
                .filter(inMemoryUserStorage.getFriendsById(user2)::contains)
                .collect(Collectors.toSet());
    }

    public Collection<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User createUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public User findUserById(int id) {
        return inMemoryUserStorage.getUser(id);
    }

    public Collection<Long> findFriendsById(int id) {
        return inMemoryUserStorage.getFriendsById(id);
    }

    public void deleteUser(int id) {
        inMemoryUserStorage.deleteUser(id);
    }
}

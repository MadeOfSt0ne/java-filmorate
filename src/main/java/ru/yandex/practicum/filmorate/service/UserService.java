package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void addFriend(int id, long newFriendId) {
        userStorage.getUser(id).getFriends().add(newFriendId);
    }

    public void removeFriend(int id, long friendToRemoveId) {
        userStorage.getUser(id).getFriends().remove(friendToRemoveId);
    }

    public Collection<Long> getCommonFriends(int user1, int user2) {
        return findFriendsById(user1).stream()
                .distinct()
                .filter(findFriendsById(user2)::contains)
                .collect(Collectors.toSet());
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User findUserById(int id) {
        return userStorage.getUser(id);
    }

    public Collection<Long> findFriendsById(int id) {
        return userStorage.getUser(id).getFriends();
    }

    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }
}

package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // добавление нового друга
    public void addFriend(long id, long newFriendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(newFriendId);
        Set<Long> friends1;
        Set<Long> friends2;
        // получаем список друзей первого пользователя
        if (user.getFriends() == null) {
            friends1 = new HashSet<>();
        } else {
            friends1 = user.getFriends();
        }
        friends1.add(newFriendId);   // добавляем id второго
        // получаем список друзей второго пользователя
        if (friend.getFriends() == null) {
            friends2 = new HashSet<>();
        } else {
            friends2 = friend.getFriends();
        }
        friends2.add(id);   // добавляем id первого
        // обновляем списки друзей
        user.setFriends(friends1);
        friend.setFriends(friends2);
        // обновляем пользователей
        updateUser(user);
        updateUser(friend);
    }

    // удаление друга
    public void removeFriend(long id, long friendToRemoveId) {
        User user = userStorage.getUser(id);
        User notFriend = userStorage.getUser(friendToRemoveId);
        user.getFriends().remove(notFriend.getId());
        notFriend.getFriends().remove(user.getId());
        userStorage.updateUser(user);
        userStorage.updateUser(notFriend);
    }

    // поиск общих друзей для двух пользователей
    public Set<User> getCommonFriends(int user1, int user2) {
        if (userStorage.getUser(user1).getFriends() == null || userStorage.getUser(user2).getFriends() == null) {
            return new HashSet<>();
        }
        // получение списка id общих друзей
        /*return userStorage.getUser(user1).getFriends().stream()
                .distinct()
                .filter(userStorage.getUser(user2).getFriends()::contains)
                .collect(Collectors.toSet());*/
        // получение списка друзей общих друзей
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
    public Collection<User> findFriendsById(int id) {
        return userStorage.getUser(id).getFriends().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    // удаление пользователя
    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }

}

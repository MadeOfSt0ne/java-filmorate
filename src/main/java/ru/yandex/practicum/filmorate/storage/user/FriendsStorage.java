package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FriendsStorage {
    void addFriend(Friendship friendship);
    void deleteFriend(Friendship friendship);
    Collection<Long> getFriends(long userId);
}

package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDbStorage implements UserStorage, FriendsStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_USER = "INSERT INTO users (name, login, email, birthday) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET " +
            "name = ?, login = ?, email = ?, birthday = ? WHERE id = ?";
    private static final String SQL_GET_USER = "SELECT id, name, login, email, birthday " +
            "FROM users WHERE id = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT id, name, login, email, birthday FROM users";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SQL_ADD_FRIEND = "INSERT INTO friends (user_id, friend_id, accepted) " + "VALUES (?, ?, ?);";
    private static final String SQL_DELETE_FRIEND = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?;";
    private static final String SQL_GET_FRIENDS = "SELECT friend_id FROM friends WHERE user_id = ?;";

    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(SQL_INSERT_USER, new String[] {"id"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(SQL_UPDATE_USER,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public User getUser(long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_USER, this::mapRowToUser, id);
        } catch (Exception e) {
            log.debug("Пользователь с id={} не найден", id);
            throw new UserNotFoundException(e.getMessage());
        }
    }

    // метод для маппинга строки из БД в пользователя
    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .login(resultSet.getString("login"))
                .email(resultSet.getString("email"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, this::mapRowToUser);
    }

    @Override
    public void deleteUser(long id) {
        jdbcTemplate.update(SQL_DELETE_USER, id);
    }

    // добавить дружбу
    @Override
    public void addFriend(Friendship friendship) {
        jdbcTemplate.update(SQL_ADD_FRIEND, friendship.getUser().getId(), friendship.getFriend().getId(), "TRUE");
    }

    // удалить дружбу
    @Override
    public void deleteFriend(Friendship friendship) {
        jdbcTemplate.update(SQL_DELETE_FRIEND, friendship.getUser().getId(), friendship.getFriend().getId());
    }

    // получить список друзей
    @Override
    public Collection<Long> getFriends(long userId) {
        return jdbcTemplate.query(SQL_GET_FRIENDS, (rs, rowNum) -> rs.getLong("friend_id"), userId);
    }
}

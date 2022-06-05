package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public User addUser(User user) {
        String sqlInsert = "INSERT INTO users (name, login, email, birthday) " +
                           "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlInsert, new String[] {"id"});
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
        String sqlUpdate = "UPDATE users SET " +
                           "name = ?, login = ?, email = ?, birthday = ? " +
                           "WHERE id = ?";
        jdbcTemplate.update(sqlUpdate,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public User getUser(long id) {
        String sqlGet = "SELECT id, name, login, email, birthday " +
                        "FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlGet, this::mapRowToUser, id);
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
        String sqlGetAll = "SELECT id, name, login, email, birthday " +
                           "FROM users";
        return jdbcTemplate.query(sqlGetAll, this::mapRowToUser);
    }

    @Override
    public void deleteUser(long id) {
        String sqlDelete = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sqlDelete, id);
    }
}

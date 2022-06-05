package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikeStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class FilmDbStorage implements FilmStorage, LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // добавление фильма
    @Override
    public Film addFilm(Film film) {
        String sqlInsert = "INSERT INTO films (name, description, duration, release_date, rating_id) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlInsert, new String[] {"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getDuration());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(5, film.getRatingId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;
    }

    // обновление фильма
    @Override
    public Film updateFilm(Film film) {
        String sqlUpdate = "UPDATE films SET " +
                "name = ?, description = ?, duration = ?, release_date = ?, rating_id = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(sqlUpdate,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                film.getReleaseDate(),
                film.getRatingId(),
                film.getId());
        return film;
    }

    // удаление фильма
    @Override
    public void deleteFilm(long id) {
        String sqlDelete = "DELETE FROM films WHERE id = ?;";
        jdbcTemplate.update(sqlDelete, id);
    }

    // поиск фильма
    @Override
    public Film getFilm(long id) {
        String sqlGet = "SELECT id, name, description, duration, release_date, rating_id " +
                "FROM films WHERE id = ?;";
        try {
            return jdbcTemplate.queryForObject(sqlGet, this::mapRowToFilm, id);
        } catch (Exception e) {
            log.debug("Фильм с id={} не найден", id);
            throw new FilmNotFoundException(e.getMessage());
        }
    }

    // метод для маппинга строки из БД в фильм
    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .duration(resultSet.getInt("duration"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .ratingId(resultSet.getInt("rating_id"))
                .build();
    }

    // получение списка всех фильмов
    @Override
    public List<Film> getAllFilms() {
        String sqlGetAll = "SELECT id, name, description, duration, release_date, rating_id " +
                "FROM films;";
        return jdbcTemplate.query(sqlGetAll, this::mapRowToFilm);
    }

    // добавление лайка
    @Override
    public void addLike(Like like) {
        String sqlLike = "INSERT INTO likes (film_id, user_id) " + "VALUES (?, ?);";
        jdbcTemplate.update(sqlLike, like.getFilm().getId(), like.getUser().getId());
    }

    // удаление лайка
    @Override
    public void deleteLike(Like like) {
        String sqlDislike = "DELETE FROM likes WHERE film_id = ? AND user_id = ?;";
        jdbcTemplate.update(sqlDislike, like.getFilm().getId(), like.getUser().getId());
    }

    // получение списка популярных фильмов
    @Override
    public Collection<Film> getPopular(int count) {
        String sqlPopular = "SELECT * " +
                "FROM films AS f " +
                "LEFT OUTER JOIN likes AS l ON f.film_id = l.film_id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?;";
        return jdbcTemplate.query(sqlPopular, this::mapRowToFilm, count);
    }
}

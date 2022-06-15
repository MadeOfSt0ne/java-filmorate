package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.MpaaRating;
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
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage, LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_FILM = "INSERT INTO films (name, description, duration, release_date, rating_id) " +
            "VALUES (?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE_FILM = "UPDATE films SET " +
            "name = ?, description = ?, duration = ?, release_date = ?, rating_id = ? WHERE id = ?;";
    private static final String SQL_DELETE_FILM = "DELETE FROM films WHERE id = ?;";
    private static final String SQL_GET_FILM = "SELECT id, name, description, duration, release_date, rating_id " +
            "FROM films WHERE id = ?;";
    private static final String SQL_GET_ALL_FILMS = "SELECT id, name, description, duration, release_date, rating_id " +
            "FROM films;";
    private static final String SQL_GET_ALL_GENRES = "SELECT * FROM genre;";
    private static final String SQL_GET_GENRE = "SELECT * FROM genre WHERE id = ?;";
    private static final String SQL_GET_ALL_RATINGS = "SELECT * FROM MPAA_rating;";
    private static final String SQL_GET_RATING = "SELECT * FROM MPAA_rating WHERE id = ?;";
    private static final String SQL_INSERT_LIKE = "INSERT INTO likes (film_id, user_id) " + "VALUES (?, ?);";
    private static final String SQL_DELETE_LIKE = "DELETE FROM likes WHERE film_id = ? AND user_id = ?;";
    private static final String SQL_GET_POPULAR = "SELECT * FROM films AS f " +
            "LEFT OUTER JOIN likes AS l ON f.film_id = l.film_id " +
            "ORDER BY COUNT(l.user_id) DESC LIMIT ?;";

    // добавление фильма
    @Override
    public Film addFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(SQL_INSERT_FILM, new String[] {"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getDuration());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        if (updated == 1) {
            return Film.builder()
                    .id(Objects.requireNonNull(keyHolder.getKey()).longValue())
                    .name(film.getName())
                    .description(film.getDescription())
                    .duration(film.getDuration())
                    .releaseDate(film.getReleaseDate())
                    .mpa(film.getMpa())
                    .build();
        } else {
            throw new IllegalStateException();
        }
    }

    // обновление фильма
    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update(SQL_UPDATE_FILM,
                film.getName(),
                film.getDescription(),
                film.getDuration(),
                film.getReleaseDate(),
                film.getMpa(),
                film.getId());
        return film;
    }

    // удаление фильма
    @Override
    public void deleteFilm(long id) {
        jdbcTemplate.update(SQL_DELETE_FILM, id);
    }

    // поиск фильма
    @Override
    public Film getFilm(long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_FILM, this::mapRowToFilm, id);
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
                .mpa(MpaaRating.builder().id(resultSet.getInt("rating_id")).build())
                .build();
    }

    // метод для маппинга строки из БД в жанр
    private Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    // метод для маппинга строки из БД в mpaa рейтинг
    private MpaaRating mapRowToRating(ResultSet resultSet, int rowNum) throws SQLException {
        return MpaaRating.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    // получение списка всех фильмов
    @Override
    public List<Film> getAllFilms() {
        return jdbcTemplate.query(SQL_GET_ALL_FILMS, this::mapRowToFilm);
    }

    // получение жанров
    @Override
    public Collection<Genre> getGenre(Integer id) {
        if (id == null) {
            return jdbcTemplate.query(SQL_GET_ALL_GENRES, this::mapRowToGenre);
        }
        return jdbcTemplate.query(SQL_GET_GENRE, this::mapRowToGenre, id);
    }

    // получение рейтингов mpaa
    @Override
    public Collection<MpaaRating> getRating(Integer id) {
        if (id == null) {
            return jdbcTemplate.query(SQL_GET_ALL_RATINGS, this::mapRowToRating);
        }
        return jdbcTemplate.query(SQL_GET_RATING, this::mapRowToRating, id);
    }

    // добавление лайка
    @Override
    public void addLike(Like like) {
        jdbcTemplate.update(SQL_INSERT_LIKE, like.getFilm().getId(), like.getUser().getId());
    }

    // удаление лайка
    @Override
    public void deleteLike(Like like) {
        jdbcTemplate.update(SQL_DELETE_LIKE, like.getFilm().getId(), like.getUser().getId());
    }

    // получение списка популярных фильмов
    @Override
    public Collection<Film> getPopular(int count) {
        return jdbcTemplate.query(SQL_GET_POPULAR, this::mapRowToFilm, count);
    }
}

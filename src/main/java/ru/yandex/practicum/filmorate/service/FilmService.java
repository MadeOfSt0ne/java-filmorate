package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    // зависимость UserStorage внедрена для проверки существования пользователей, которые ставят лайки
    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    // добавление лайка фильму
    public void addLike(int filmId, long userId) {
        //checkUserAndFilm(filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.getLikes().add(user.getId());
        filmStorage.updateFilm(film);
    }

    // удаление лайка
    public void deleteLike(int filmId, long userId) {
        //checkUserAndFilm(filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.getLikes().remove(user.getId());
        filmStorage.updateFilm(film);
    }

    // получение списка популярных фильмов
    public List<Film> getPopular(int amount) {
        return filmStorage.getAllFilms().stream()
                .sorted(Comparator.comparingInt(f0 -> f0.getLikes().size()))
                .limit(amount)
                .collect(Collectors.toList());
    }

    // получение списка всех фильмов
    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    // добавление нового фильма
    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    // обновление фильма
    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    // поиск фильма
    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }

    // удаление фильма
    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    // метод для проверки существования юзера и фильма
    public void checkUserAndFilm(int filmId, long userId) {
        if (userStorage.getUser(userId) == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        if (filmStorage.getFilm(filmId) == null) {
            throw new FilmNotFoundException("Фильм не найден!");
        }
    }
}

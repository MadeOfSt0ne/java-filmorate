package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;

    // зависимость UserStorage внедрена для проверки существования пользователей, которые ставят лайки
    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    /* Эти методы используются для inMemory хранения

    // добавление лайка фильму
    public void addLike(long filmId, long userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);  // проверка на то, что пользователь существует
        Set<Long> likes;
        if (film.getLikes() == null) {
            likes = new HashSet<>();
            likes.add(userId);
        } else {
            likes = film.getLikes();
            likes.add(userId);
        }
        film.setLikes(likes);
        filmStorage.updateFilm(film);
    }

    // удаление лайка
    public void deleteLike(long filmId, long userId) {
        //checkUserAndFilm(filmId, userId);
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        film.getLikes().remove(user.getId());
        filmStorage.updateFilm(film);
    }

    // получение списка популярных фильмов
    public List<Film> getPopular(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted(this::compare)
                .limit(count)
                .collect(Collectors.toList());
    }

    // сравнение фильмов по количеству лайков
    public int compare(Film f1, Film f2) {
        if (f1.getLikes() == null) {
            return 1;
        } else if (f2.getLikes() == null) {
            return -1;
        } else {
            return f2.getLikes().size() - f1.getLikes().size();
        }
    }
*/
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
    public Film getFilm(long id) {
        return filmStorage.getFilm(id);
    }

    // удаление фильма
    public void deleteFilm(long id) {
        filmStorage.deleteFilm(id);
    }

    // получаем 2 id, с помощью билдера делаем из них лайк, а затем передаем его в хранилище
    public void addLike(long filmId, long userId) {
        likeStorage.addLike(Like.builder()
                .film(getFilm(filmId))
                .user(userStorage.getUser(userId))
                .build());
    }

    // получаем 2 id, с помощью билдера делаем из них лайк, а затем передаем его в хранилище
    public void deleteLike(long filmId, long userId) {
        likeStorage.deleteLike(Like.builder()
                .film(getFilm(filmId))
                .user(userStorage.getUser(userId))
                .build());
    }

    // получаем список популярных фильмов
    public Collection<Film> getPopular(int count) {
        return likeStorage.getPopular(count);
    }

    public Collection<Genre> getGenres(Integer id) {
        return filmStorage.getGenre(id);
    }

    public Collection<MpaaRating> getMpaa(Integer id) {
        return filmStorage.getRating(id);
    }
}

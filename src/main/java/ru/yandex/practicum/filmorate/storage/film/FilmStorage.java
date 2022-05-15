package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    // добавление фильма
    Film addFilm(Film film);
    // обновление фильма
    Film updateFilm(Film film);
    // удаление фильма
    void deleteFilm(int id);
    // поиск фильма
    Film getFilm(int id);
    // список всех фильмов
    List<Film> getAllFilms();
}

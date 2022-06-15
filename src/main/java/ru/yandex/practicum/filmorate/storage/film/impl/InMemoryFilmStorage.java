package ru.yandex.practicum.filmorate.storage.film.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaaRating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
/*

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Long, Film> films = new HashMap<>();
    private static int id = 1;

    private static int getNextId() {
        return id++;
    }

    @Override
    public Film addFilm(Film film) {
        if (isInvalid(film)) {
            throw new ValidationException("Невалидные данные");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film changedFilm) {
        if (!films.containsKey(changedFilm.getId())) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        if (isInvalid(changedFilm)) {
            throw new ValidationException("Невалидные данные");
        }
        Film savedFilm = films.get(changedFilm.getId());
        savedFilm.setName(changedFilm.getName());
        savedFilm.setDescription(changedFilm.getDescription());
        savedFilm.setReleaseDate(changedFilm.getReleaseDate());
        savedFilm.setDuration(changedFilm.getDuration());
        //savedFilm.setLikes(changedFilm.getLikes());
        return savedFilm;
    }

    @Override
    public void deleteFilm(long filmId) {
        films.remove(filmId);
    }

    @Override
    public Film getFilm(long filmId) {
        if (filmId < 0) {
            throw new FilmNotFoundException("Film not found!");
        }
        return films.get(filmId);
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Collection<Genre> getGenre(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<MpaaRating> getRating(Integer id) {
        throw new UnsupportedOperationException();
    }

    // метод для проверки валидности фильма
    private boolean isInvalid(Film film) {
        int MAX_DESCRIPTION_LENGTH = 200;
        LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

        return film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)
                || film.getName().isBlank()
                || film.getDuration() <= 0;
    }

    // вспомогательный метод для очистки таблицы
    public void clearMap() {
        films.clear();
        id = 0;
    }
}
*/

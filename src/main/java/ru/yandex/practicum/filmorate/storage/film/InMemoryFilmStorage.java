package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static int id = 1;

    private static int getNextId() {
        return id++;
    }

    @Override
    public Film addFilm(Film film) {
        validate(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film changedFilm) {
        if (!films.containsKey(changedFilm.getId())) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        validate(changedFilm);
        Film savedFilm = films.get(changedFilm.getId());
        savedFilm.setName(changedFilm.getName());
        savedFilm.setDescription(changedFilm.getDescription());
        savedFilm.setReleaseDate(changedFilm.getReleaseDate());
        savedFilm.setDuration(changedFilm.getDuration());
        savedFilm.setLikes(changedFilm.getLikes());
        return savedFilm;
    }

    @Override
    public void deleteFilm(int filmId) {
        films.remove(filmId);
    }

    @Override
    public Film getFilm(int filmId) {
        if (filmId < 0) {
            throw new FilmNotFoundException("Film not found!");
        }
        return films.get(filmId);
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    // метод для проверки валидности фильма
    private void validate(Film film) {
        int MAX_DESCRIPTION_LENGTH = 200;
        LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

        if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)
                || film.getName().isBlank()
                || film.getDuration() <= 0) {
            throw new ValidationException("Невалидные данные!");
        }
    }

    // вспомогательный метод для очистки таблицы
    public void clearMap() {
        films.clear();
        id = 0;
    }
}

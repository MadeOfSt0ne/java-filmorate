package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final int MAX_DESCRIPTION_LENGTH = 200;
    private final LocalDate CINEMA_BIRTHDATE = LocalDate.of(1895, 12, 28);

    private final HashMap<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public HashMap<Integer, Film> getAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @PostMapping("/films")
    public void addFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getReleaseDate().isBefore(CINEMA_BIRTHDATE)
                || film.getName().isBlank()
                || film.getDuration() <= 0
        ) {
            log.debug("Ошибка валидации по запросу POST /film");
            throw new ValidationException();
        }
        log.debug("Добавлен новый фильм: {}", film);
        films.put(film.getFilmId(), film);
    }

    @PutMapping("/films")
    public void updateFilm(@Valid @RequestBody Film changedFilm) throws ValidationException {
        if (changedFilm.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || changedFilm.getReleaseDate().isBefore(CINEMA_BIRTHDATE)
                || changedFilm.getName().isBlank()
                || changedFilm.getDuration() <= 0
        ) {
            log.debug("Ошибка валидации по запросу PUT /film");
            throw new ValidationException();
        }
        log.debug("Информация о фильме {} успешно обновлена", changedFilm.getName());
        Film savedFilm = films.get(changedFilm.getFilmId());
        savedFilm.setName(changedFilm.getName());
        savedFilm.setDescription(changedFilm.getDescription());
        savedFilm.setReleaseDate(changedFilm.getReleaseDate());
        savedFilm.setDuration(changedFilm.getDuration());
     }

    // метод для очистки мапы. нужен для тестов
    public void clearMap() {
        films.clear();
    }
}

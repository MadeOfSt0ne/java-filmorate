package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final int MAX_DESCRIPTION_LENGTH = 200;
    private final LocalDate CINEMA_BIRTHDATE = LocalDate.of(1895, 12, 28);

    private final List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> getAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @PostMapping("/film")
    public void addFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getReleaseDate().isAfter(CINEMA_BIRTHDATE)
        ) {
            log.debug("Ошибка валидации по запросу POST /film");
            throw new ValidationException();
        }
        log.debug("Добавлен новый фильм: {}", film);
        films.add(film);
    }

    @PutMapping("/film")
    public void updateFilm(@Valid @RequestBody Film changedFilm) throws ValidationException {
        if (changedFilm.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || changedFilm.getReleaseDate().isAfter(CINEMA_BIRTHDATE)
        ) {
            log.debug("Ошибка валидации по запросу PUT /film");
            throw new ValidationException();
        }
        log.debug("Информация о фильме {} успешно обновлена", changedFilm.getName());
        Film savedFilm = films.get(changedFilm.getFilmId());
        savedFilm.setDescription(changedFilm.getDescription());
        }
}

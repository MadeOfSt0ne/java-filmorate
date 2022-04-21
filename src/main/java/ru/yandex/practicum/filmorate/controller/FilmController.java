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

    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping("/films")
    public List<Film> getAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public void addFilm(@Valid @RequestBody Film film) throws ValidationException {
        if (isInvalid(film)) {
            log.debug("Ошибка валидации по запросу POST /film");
            throw new ValidationException();
        }
        log.debug("Добавлен новый фильм: {}", film);
        films.put(film.getId(), film);
        id++;
    }

    @PutMapping("/films")
    public void updateFilm(@Valid @RequestBody Film changedFilm) throws ValidationException {
        if (!films.containsKey(changedFilm.getId())) {
            log.debug("фильм не найден.  id: {}", id);
            return;
        }
        if (isInvalid(changedFilm)) {
            log.debug("Ошибка валидации по запросу PUT /film");
            throw new ValidationException();
        }
        log.debug("Информация о фильме {} успешно обновлена", changedFilm.getName());
        Film savedFilm = films.get(changedFilm.getId());
        savedFilm.setName(changedFilm.getName());
        savedFilm.setDescription(changedFilm.getDescription());
        savedFilm.setReleaseDate(changedFilm.getReleaseDate());
        savedFilm.setDuration(changedFilm.getDuration());
    }

    // метод для проверки валидности фильма
    private boolean isInvalid(Film film) {
        int MAX_DESCRIPTION_LENGTH = 200;
        LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

        return  (film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)
                || film.getName().isBlank()
                || film.getDuration() <= 0);
    }

    // метод для очистки мапы. нужен для тестов
    public void clearMap() {
        films.clear();
        id = 1;
    }
}

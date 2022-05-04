package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        log.debug("Текущее количество фильмов: {}", filmService.getAll().size());
        return filmService.getAll();
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.addFilm(film);
        /*if (isInvalid(film)) {
            log.debug("Ошибка валидации по запросу POST /film");
            throw new ValidationException();
        }
        log.debug("Добавлен новый фильм: {}", film);*/


    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.updateFilm(film);
        /*if (!films.containsKey(changedFilm.getId())) {
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
        savedFilm.setDuration(changedFilm.getDuration());*/
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void putLike(@PathVariable int id, @PathVariable int userId) {
        filmService.putLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10", required = false) int count) {
        if (count <= 0) {
            throw new IncorrectParameterException("count");
        }
        return filmService.getPopular(count);
    }

    /*// метод для проверки валидности фильма
    private boolean isInvalid(Film film) {
        int MAX_DESCRIPTION_LENGTH = 200;
        LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

        return  (film.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || film.getReleaseDate().isBefore(CINEMA_BIRTHDAY)
                || film.getName().isBlank()
                || film.getDuration() <= 0);
    }
*/

}

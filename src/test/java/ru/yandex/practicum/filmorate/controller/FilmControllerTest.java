package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController = new FilmController();

    // Валидные фильмы
    static Film validFilm, validFilm1, maxDescriptionLength, newValidFilm;
    // Невалидные фильмы для создания
    static Film blankName, negativeDuration, invalidReleaseDate, moreThan200Symbols, zeroDuration;
    // Невалидные фильмы для обновления
    static Film newBlankName, newNegativeDuration, newInvalidReleaseDate, newMoreThan200Symbols;

    @BeforeEach
    void beforeTests() {
        String str = "test";
        validFilm = new Film("Terminator", 1, "robots vs men", LocalDate.of(1984, 10, 26), 108);
        validFilm1 = new Film("Avatar", 2, "people fight navi for minerals", LocalDate.of(2009, 12, 10), 162);
        //Film nullName = new Film(null, 3, "robots vs men", LocalDate.of(1984, 10, 26), 108); // тест не запускается - сразу NPE
        blankName = new Film(" ", 4, "robots vs men", LocalDate.of(1984, 10, 26), 108);
        maxDescriptionLength = new Film("Titanic", 5, str.repeat(50), LocalDate.of(1997, 11, 1), 194);
        negativeDuration = new Film("Titanic", 6, "ocean liner hits iceberg", LocalDate.of(1997, 11, 1), -194);
        zeroDuration = new Film("Titanic", 7, "ocean liner hits iceberg", LocalDate.of(1997, 11, 1), 0);
        invalidReleaseDate = new Film("Titanic", 8, "ocean liner hits iceberg", LocalDate.of(1895, 12, 27), 194);
        moreThan200Symbols = new Film("Avatar", 9, str.repeat(50) + "Q", LocalDate.of(2009, 12, 10), 162);
        newBlankName = new Film(" ", 1, "new description", LocalDate.of(1984, 10, 26), 108);
        newNegativeDuration = new Film("Titanic", 1, "ocean liner hits iceberg", LocalDate.of(1997, 11, 1), -194);
        newInvalidReleaseDate = new Film("Titanic", 1, "ocean liner hits iceberg", LocalDate.of(1895, 12, 27), 194);
        newMoreThan200Symbols = new Film("Avatar", 1, str.repeat(50) + "Q", LocalDate.of(2009, 12, 10), 162);
        newValidFilm = new Film("Avatar", 11, "people fight navi for minerals", LocalDate.of(2009, 12, 10), 162);
        inMemoryFilmStorage.clearMap();
    }

    @Test
    void getAll() throws ValidationException {
        filmController.addFilm(validFilm);
        assertEquals(1, filmController.getAllFilms().size(), "должен быть один фильм");
        filmController.addFilm(validFilm1);
        assertEquals(2, filmController.getAllFilms().size(), "должно быть два фильма");
        filmController.addFilm(maxDescriptionLength);
        assertEquals(3, filmController.getAllFilms().size(), "должно быть три фильма");
    }

    @Test
    void addFilm() {
        assertThrows(ValidationException.class, () -> filmController.addFilm(blankName), "blank name");
        assertThrows(ValidationException.class, () -> filmController.addFilm(negativeDuration), "negative duration");
        assertThrows(ValidationException.class, () -> filmController.addFilm(negativeDuration), "negative duration");
        assertThrows(ValidationException.class, () -> filmController.addFilm(invalidReleaseDate), "invalid release date");
        assertThrows(ValidationException.class, () -> filmController.addFilm(moreThan200Symbols), "description > 200");
        assertEquals(0, filmController.getAllFilms().size(), "созданы невалидные фильмы");
    }

    @Test
    void updateFilm() throws ValidationException {
        filmController.addFilm(validFilm);
        assertEquals(1, filmController.getAllFilms().size(), "фильм не был создан");
        assertThrows(ValidationException.class, () -> filmController.updateFilm(newBlankName), "blank name");
        assertThrows(ValidationException.class, () -> filmController.updateFilm(newNegativeDuration), "negative duration");
        assertThrows(ValidationException.class, () -> filmController.updateFilm(newInvalidReleaseDate), "invalid release date");
        assertThrows(ValidationException.class, () -> filmController.updateFilm(newMoreThan200Symbols), "description > 200");
        assertEquals(1, filmController.getAllFilms().size(), "созданы невалидные фильмы");
    }
}
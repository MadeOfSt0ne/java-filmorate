package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController = new FilmController();

    @Test
    void getAll() {
    }

    @Test
    void addFilm() {
        Film film = new Film(1, "Terminator", "robots vs men", LocalDate.of());

    }

    @Test
    void updateFilm() {
    }
}
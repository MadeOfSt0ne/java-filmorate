package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private static int id = 0;

    private static int getNextId() {
        return id++;
    }

    @Override
    public void addFilm() {

    }

    @Override
    public void updateFilm() {

    }

    @Override
    public void deleteFilm() {

    }

    // метод для очистки мапы. нужен для тестов
    public void clearMap() {
        films.clear();
        id = 0;
    }
}

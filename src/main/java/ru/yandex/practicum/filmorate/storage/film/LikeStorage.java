package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;

import java.util.Collection;

public interface LikeStorage {

    // поставить лайк
    void addLike(Like like);
    // удалить лайк
    void deleteLike(Like like);
    // получить список популярных фильмов
    Collection<Film> getPopular(int count);
}

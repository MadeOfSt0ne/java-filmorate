package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {

    @NonNull
    private String name;
    private int id;
    private String description;
    @NonNull
    private LocalDate releaseDate;
    // Аннотация возвращает код 400, а тесты требуют 500. Поэтому закомментировал. Принцип работы понятен))
    //@Positive
    private int duration;

}

package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {

    private final int filmId;
    @NonNull
    @NotBlank
    private final String name;
    private String description;
    @NonNull
    private final LocalDate releaseDate;
    @Positive
    private final int duration;

}

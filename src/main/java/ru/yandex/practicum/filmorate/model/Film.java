package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {

    private String name;
    private int id;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likes;
}

package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {

    @NonNull
    private String name;
    private int id;
    //@Size(min = 1, max = 200)
    private String description;
    @NonNull
    private LocalDate releaseDate;
    // Аннотация возвращает код 400, а постман тесты требуют 500. Поэтому закомментировал. Принцип работы понятен))
    //@Positive
    //@Min(1) то же самое: тесты требуют ошибку сервера(код 500), а такая аннотация дает bad request(код 400)
    private int duration;

}

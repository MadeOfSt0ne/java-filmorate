package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    @NonNull
    @NotBlank
    private String login;
    private String name;
    private long id;
    // Если поставить аннотацию @Email, то сервер вернет код 400, а если ее убрать, то проверка на @ выдаст
    // ValidationException и вернет код 500. Тесты требуют 500, поэтому закомментировал аннотацию.
    //@Email
    private String email;
    private LocalDate birthday;
    private Set<Long> friends;

}

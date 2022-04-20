package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    @NonNull
    @NotBlank
    private String login;
    private String name;
    private int id;
    @Email
    private String email;
    @NonNull
    private LocalDate birthday;

}

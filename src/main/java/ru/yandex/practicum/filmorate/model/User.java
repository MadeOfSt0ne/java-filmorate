package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class User {

    @NonNull
    private final int userId;
    @Email
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthdate;

}

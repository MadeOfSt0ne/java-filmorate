package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder

public class User {

    @NonNull
    @NotBlank
    private String login;
    private String name;
    private long id;
    @Email
    private String email;
    private LocalDate birthday;
    private Set<Long> friends;

}

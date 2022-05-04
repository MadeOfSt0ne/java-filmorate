package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class User {

    @NonNull
    @NotBlank
    private String login;
    private String name;
    private int id;
    // Если поставить аннотацию @Email, то сервер вернет код 400, а если ее убрать, то проверка на @ выдаст
    // ValidationException и вернет код 500. Тесты требуют 500, поэтому закомментировал аннотацию.
    //@Email
    private String email;
    private LocalDate birthday;
    private Set<Long> friends;

    public User(String login, String name, int id, String email, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.id = id;
        this.email = email;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Set<Long> getFriends() {
        return friends;
    }

    public void setFriends(Set<Long> friends) {
        this.friends = friends;
    }
}

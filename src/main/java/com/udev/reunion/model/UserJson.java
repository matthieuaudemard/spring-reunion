package com.udev.reunion.model;

import com.udev.reunion.domain.User;
import lombok.Data;

@Data
public class UserJson {

    private Long id;
    private String login;
    private String firstname;
    private String lastname;

    public UserJson() {
        // Constructeur par défaut
    }

    public UserJson(User user) {
        setId(user.getId());
        setLogin(user.getLogin());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
    }
}

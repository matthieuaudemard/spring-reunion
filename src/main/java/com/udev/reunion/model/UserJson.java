package com.udev.reunion.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserJson implements Serializable {

    private Long id;
    private String login;
    private String firstname;
    private String lastname;
    private String about;

    public UserJson() {
        // Constructeur par défaut
    }
}

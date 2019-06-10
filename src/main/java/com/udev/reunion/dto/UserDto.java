package com.udev.reunion.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private Long id;
    private String login;
    private String firstname;
    private String lastname;
    private String about;

    public UserDto() {
        // Constructeur par défaut
    }
}

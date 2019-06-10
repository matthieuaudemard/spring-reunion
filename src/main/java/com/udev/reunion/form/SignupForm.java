package com.udev.reunion.form;

import lombok.Data;

@Data
public class SignupForm {

    private String login = "";
    private String firstname = "";
    private String lastname = "";
    private String password = "";
    private String passwordConfirmation = "";

}

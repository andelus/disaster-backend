package com.example.backend_disaster_project.disasterbackend.entities;

import javax.validation.constraints.NotBlank;


public class ResetPasswordModel {
    @NotBlank(message = "token.not.empty")
    private String token;

    @NotBlank(message = "password.not.empty")
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


package com.spring.SpeedAuction.payload.request;

import jakarta.validation.constraints.NotBlank;

public class SigninRequest {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}

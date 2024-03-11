package com.spring.SpeedAuction.dto;

import java.util.List;

public class UserResponsDTO {

    private String username;

    private String email;

    private List<String> favouriteAuction;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFavouriteAuction() {
        return favouriteAuction;
    }

    public void setFavouriteAuction(List<String> favouriteAuction) {
        this.favouriteAuction = favouriteAuction;
    }
}

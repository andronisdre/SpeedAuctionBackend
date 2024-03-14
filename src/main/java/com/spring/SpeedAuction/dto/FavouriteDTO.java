package com.spring.SpeedAuction.dto;

import java.util.List;

public class FavouriteDTO {

    private List<String> favouriteAuction;


    public List<String> getFavouriteAuction() {
        return favouriteAuction;
    }

    public void setFavouriteAuction(List<String> favouriteAuction) {
        this.favouriteAuction = favouriteAuction;
    }
}

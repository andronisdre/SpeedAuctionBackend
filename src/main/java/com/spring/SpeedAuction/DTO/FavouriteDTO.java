package com.spring.SpeedAuction.DTO;

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

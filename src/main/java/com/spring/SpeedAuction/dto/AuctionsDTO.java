package com.spring.SpeedAuction.dto;

import jakarta.validation.constraints.Min;

import java.util.Date;

public class AuctionsDTO {
    private String sellerId;

    @Min(value = 100000, message = "starting price must be at least 100 thousand swedish crowns")
    private int startingPrice;
    private boolean isActive;
    private Date created_at;
    private Date endOfAuction;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getEndOfAuction() {
        return endOfAuction;
    }

    public void setEndOfAuction(Date endOfAuction) {
        this.endOfAuction = endOfAuction;
    }
}

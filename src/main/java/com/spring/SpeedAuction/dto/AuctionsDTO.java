package com.spring.SpeedAuction.dto;

import java.util.Date;

public class AuctionsDTO {
    private String sellerId;
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

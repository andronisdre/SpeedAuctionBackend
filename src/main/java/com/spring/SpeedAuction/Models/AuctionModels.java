package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "auctions")
public class AuctionModels {


    @Id
    private String id;

    @DBRef
    private UserModels seller;

    private int startingPrice;

    private boolean isActive;

    @CreatedDate
    private Date created_at;

    private Date updated_at;

    private Date endOfAuction;

    public AuctionModels(String id) {
        this.id = id;
    }

    public AuctionModels() {
    }

    public String getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public void setId(String id) {
        this.id = id;
    }

    public UserModels getSeller() {
        return seller;
    }

    public void setSeller(UserModels seller) {
        this.seller = seller;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getEndOfAuction() {
        return endOfAuction;
    }

    public void setEndOfAuction(Date endOfAuction) {
        this.endOfAuction = endOfAuction;
    }
}

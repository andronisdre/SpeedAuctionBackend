package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document (collection = "bids")
public class BidsModels {

    //initiering av attributer
    @Id
    private String id;
    @DBRef
    private AuctionModels auction;
    @DBRef
    private UserModels bidder;
    private Integer amount;
    @CreatedDate
    private Date timeBidded;

    public BidsModels(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuctionModels getAuction() {
        return auction;
    }

    public void setAuction(AuctionModels auction) {
        this.auction = auction;
    }

    public UserModels getBidder() {
        return bidder;
    }

    public void setBidder(UserModels bidder) {
        this.bidder = bidder;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getTimeBidded() {
        return timeBidded;
    }

    public void setTimeBidded(Date timeBidded) {
        this.timeBidded = timeBidded;
    }
}

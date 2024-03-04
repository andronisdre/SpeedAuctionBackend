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
    private AuctionModels auctionId;
    @DBRef
    private UserModels bidderId;
    private Integer amount;
    @CreatedDate
    private Date timeBidded;
    private Boolean priority; //if true activ (highestbidder) else history

    public BidsModels(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModels getBidderId() {
        return bidderId;
    }

    public void setBidderId(UserModels bidderId) {
        this.bidderId = bidderId;
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

    public AuctionModels getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(AuctionModels auctionId) {
        this.auctionId = auctionId;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}

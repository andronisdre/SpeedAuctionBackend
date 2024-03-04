package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document (collection = "bids")
public class BidsModels {

    //initiering av attributer
    @Id
    String id;
    String auctionId;
    String bidderId;
    Integer amount;
    LocalDateTime timeBidded;
    Boolean priority; //if true activ (highestbidder) else history

    public BidsModels(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public LocalDateTime getTimeBidded() {
        return timeBidded;
    }

    public void setTimeBidded(LocalDateTime timeBidded) {
        this.timeBidded = timeBidded;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}

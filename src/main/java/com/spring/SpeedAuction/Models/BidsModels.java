package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@Document (collection = "bids")
public class BidsModels {

    //initiering av attributer
    @Id
    String id;
    String auction_id;
    String bidder_id;
    Float amount;
    LocalDateTime time;
    Boolean priority; //if true activ (highestbidder) else history

    public BidsModels(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(String auction_id) {
        this.auction_id = auction_id;
    }

    public String getBidder_id() {
        return bidder_id;
    }

    public void setBidder_id(String bidder_id) {
        this.bidder_id = bidder_id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}

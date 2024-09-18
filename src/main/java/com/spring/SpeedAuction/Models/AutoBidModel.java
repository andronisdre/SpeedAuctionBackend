package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "auto_bids")
public class AutoBidModel {

    @Id
    private String id;
    @DBRef
    private UserModels autoBidder; // Reference to the user making the auto_bids
    @DBRef
    private String auctionId; // reference to the auctions
    private Integer maxBidAMount;
    @CreatedDate
    private Date timeBidded;


    public AutoBidModel(String id, UserModels autoBidder, String auctionId, Integer maxBidAMount, Date timeBidded) {
        this.id = id;
        this.autoBidder = autoBidder;
        this.auctionId = auctionId;
        this.maxBidAMount = maxBidAMount;
        this.timeBidded = timeBidded;
    }

    public AutoBidModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModels getAutoBidder() {
        return autoBidder;
    }

    public void setAutoBidder(UserModels autoBidder) {
        this.autoBidder = autoBidder;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getMaxBidAMount() {
        return maxBidAMount;
    }

    public void setMaxBidAMount(Integer maxBidAMount) {
        this.maxBidAMount = maxBidAMount;
    }

    public Date getTimeBidded() {
        return timeBidded;
    }

    public void setTimeBidded(Date timeBidded) {
        this.timeBidded = timeBidded;
    }
}

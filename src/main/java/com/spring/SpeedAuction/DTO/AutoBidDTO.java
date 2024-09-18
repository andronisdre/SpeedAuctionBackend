package com.spring.SpeedAuction.DTO;

import java.util.Date;

public class AutoBidDTO {

    private String auctionId;
    private String autoBidder;
    private Integer maxBidAmount;
    private  Date timeBidded;


    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getAutoBidder() {
        return autoBidder;
    }

    public void setAutoBidder(String autoBidder) {
        this.autoBidder = autoBidder;
    }

    public Integer getMaxBidAmount() {
        return maxBidAmount;
    }

    public void setMaxBidAmount(Integer maxBidAmount) {
        this.maxBidAmount = maxBidAmount;
    }

    public Date getTimeBidded() {
        return timeBidded;
    }

    public void setTimeBidded(Date timeBidded) {
        this.timeBidded = timeBidded;
    }
}

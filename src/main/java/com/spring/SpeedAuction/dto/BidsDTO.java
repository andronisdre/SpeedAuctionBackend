package com.spring.SpeedAuction.dto;

import java.util.Date;

public class BidsDTO {
    private String bidderId;
    private String auctionId;
    private Integer amount;
    private Date timeBidded;
    private Boolean priority;

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
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

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }
}

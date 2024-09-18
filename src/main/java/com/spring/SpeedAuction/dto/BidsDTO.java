package com.spring.SpeedAuction.DTO;

import java.util.Date;

public class BidsDTO {
    private String bidderId;
    private String auctionId;
    private Integer amount;
    private Date timeBidded;

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
}

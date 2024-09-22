package com.spring.SpeedAuction.DTO;

import java.util.Date;

public class BidsDTO {
    private String bidderId;
    private Integer amount;
    private Date timeBidded;
    private Integer maxAMount;

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

    public Date getTimeBidded() {
        return timeBidded;
    }

    public void setTimeBidded(Date timeBidded) {
        this.timeBidded = timeBidded;
    }


    public Integer getMaxAMount() {
        return maxAMount;
    }

    public void setMaxAMount(Integer maxAMount) {
        this.maxAMount = maxAMount;
    }
}

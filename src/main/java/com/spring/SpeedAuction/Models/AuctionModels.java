package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "auctions")
public class AuctionModels {

    //very simple model, add more fields following the ER diagram.
    @Id
    private String id;

    private int year_manufactured;

    private String description;

    private int miles_driven;

    private int starting_bid;

    private String reg_number;

    @CreatedDate
    private Date created_at;

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

    public int getYear_manufactured() {
        return year_manufactured;
    }

    public void setYear_manufactured(int year_manufactured) {
        this.year_manufactured = year_manufactured;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMiles_driven() {
        return miles_driven;
    }

    public void setMiles_driven(int miles_driven) {
        this.miles_driven = miles_driven;
    }

    public int getStarting_bid() {
        return starting_bid;
    }

    public void setStarting_bid(int starting_bid) {
        this.starting_bid = starting_bid;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

}

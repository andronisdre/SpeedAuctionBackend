package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "auctions")
public class AuctionModels {

    //very simple model, add more fields following the ER diagram.
    @Id
    private String id;

    @DBRef
    private UserModels seller;

    private int startingBid;

    private boolean isActive;

    private String description;

    @CreatedDate
    private Date created_at;

    private Date updated_at;

    private Date endOfAuction;

    private int yearManufactured;

    private int milesDriven;

    private String reg_number;

    private EBrand brand;

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

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(int yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMilesDriven() {
        return milesDriven;
    }

    public void setMilesDriven(int milesDriven) {
        this.milesDriven = milesDriven;
    }

    public int getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(int startingBid) {
        this.startingBid = startingBid;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public EBrand getBrand() {
        return brand;
    }

    public void setBrand(EBrand brand) {
        this.brand = brand;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModels getSeller() {
        return seller;
    }

    public void setSeller(UserModels seller) {
        this.seller = seller;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getEndOfAuction() {
        return endOfAuction;
    }

    public void setEndOfAuction(Date endOfAuction) {
        this.endOfAuction = endOfAuction;
    }
}

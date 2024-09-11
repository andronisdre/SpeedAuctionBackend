package com.spring.SpeedAuction.Models;

import com.spring.SpeedAuction.enums.EBrand;
import com.spring.SpeedAuction.enums.EColor;
import com.spring.SpeedAuction.enums.ECondition;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "auctions")
public class AuctionModels {


    @Id
    private String id;

    @DBRef
    private UserModels seller;

    private int startingPrice;

    private boolean isActive;

    @CreatedDate
    private Date created_at;

    private Date updated_at;

    private Date endOfAuction;

    private EBrand brand;

    private String carModel;

    private int yearManufactured;

    private int milesDriven;

    private List<EColor> color;

    private String carPng;

    private String regNumber;

    private ECondition condition;

    private String description;

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

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public EBrand getBrand() {
        return brand;
    }

    public void setBrand(EBrand brand) {
        this.brand = brand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(int yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public int getMilesDriven() {
        return milesDriven;
    }

    public void setMilesDriven(int milesDriven) {
        this.milesDriven = milesDriven;
    }

    public List<EColor> getColor() {
        return color;
    }

    public void setColor(List<EColor> color) {
        this.color = color;
    }

    public String getCarPng() {
        return carPng;
    }

    public void setCarPng(String carPng) {
        this.carPng = carPng;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public ECondition getCondition() {
        return condition;
    }

    public void setCondition(ECondition condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

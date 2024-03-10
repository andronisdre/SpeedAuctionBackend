package com.spring.SpeedAuction.Models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "AuctionCar")
public class Auction_Type_Car {

    private int id;
    @DBRef
    private AuctionModels auction;
    private String car_brand;

    private String car_model;

    private int yearManufactured;

    private int milesDriven;

    private List<EColor> color;

    private String car_png;

    private String reg_number;

    private ECondition condition;

    private String description;

    public Auction_Type_Car() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AuctionModels getAuction() {
        return auction;
    }

    public void setAuction(AuctionModels auction) {
        this.auction = auction;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public int getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(int yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public ECondition getCondition() {
        return condition;
    }

    public void setCondition(ECondition condition) {
        this.condition = condition;
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

    public String getCar_png() {
        return car_png;
    }

    public void setCar_png(String car_png) {
        this.car_png = car_png;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
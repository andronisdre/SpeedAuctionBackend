package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "AuctionCar")
public class AuctionTypeCar {

    @Id
    private String id;
    @DBRef
   @Indexed(unique=true)

    private AuctionModels auction;

    private EBrand brand;

    private String car_model;

    private int yearManufactured;

    private int milesDriven;

    private List<EColor> color;

    private String car_png;

    private String reg_number;

    private ECondition condition;

    private String description;

    public AuctionTypeCar() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuctionModels getAuction() {
        return auction;
    }

    public void setAuction(AuctionModels auction) {
        this.auction = auction;
    }

    public EBrand getBrand() {
        return brand;
    }

    public void setBrand(EBrand brand) {
        this.brand = brand;
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
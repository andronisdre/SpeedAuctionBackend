package com.spring.SpeedAuction.Models;

import com.spring.SpeedAuction.enums.EBrand;
import com.spring.SpeedAuction.enums.EColor;
import com.spring.SpeedAuction.enums.ECondition;
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

    private String carModel;

    private int yearManufactured;

    private int milesDriven;

    private List<EColor> color;

    private String carPng;

    private String regNumber;

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

    public String getCarModel() { return carModel; }

    public void setCarModel(String carModel) {this.carModel = carModel; }

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

    public String getCarPng() { return carPng; }

    public void setCarPng(String carPng) { this.carPng = carPng; }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
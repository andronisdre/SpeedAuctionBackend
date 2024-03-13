package com.spring.SpeedAuction.dto;

import com.spring.SpeedAuction.Models.EBrand;
import com.spring.SpeedAuction.Models.EColor;
import com.spring.SpeedAuction.Models.ECondition;

import java.util.List;

public class CarDTO {

    private String auctionId;

    private EBrand brand;

    private String car_model;

    private int yearManufactured;

    private int milesDriven;

    private List<EColor> color;

    private String car_png;

    private String reg_number;

    private ECondition condition;

    private String description;

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
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

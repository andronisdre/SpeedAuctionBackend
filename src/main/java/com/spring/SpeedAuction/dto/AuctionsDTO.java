package com.spring.SpeedAuction.dto;

import com.spring.SpeedAuction.enums.EBrand;
import com.spring.SpeedAuction.enums.EColor;
import com.spring.SpeedAuction.enums.ECondition;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class AuctionsDTO {
    private String id;
    private String sellerId;
    @Min(value = 100000, message = "starting price must be at least 100 thousand swedish crowns")
    private int startingPrice;
    private boolean isActive;
    private Date created_at;
    private Date endOfAuction;
    @NotNull
    private EBrand brand;

    @NotBlank
    private String carModel;

    @Min(value = 1950, message = "must be between 1950 and 2030")
    @Max(value = 2030, message = "must be between 1950 and 2030")
    private int yearManufactured;

    @Min(value = 0)
    private int milesDriven;

    @NotNull
    private List<EColor> color;

    @NotBlank
    private String carPng;

    @NotBlank
    private String regNumber;

    @NotNull
    private ECondition condition;

    @NotBlank
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public String getCarPng() {return carPng; }

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

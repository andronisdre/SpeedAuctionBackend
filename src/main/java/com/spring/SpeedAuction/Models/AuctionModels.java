package com.spring.SpeedAuction.Models;


import com.spring.SpeedAuction.Enums.EBrand;
import com.spring.SpeedAuction.Enums.EColor;
import com.spring.SpeedAuction.Enums.ECondition;
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
        @DBRef
        private List<BidsModels> bids;
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

    public AuctionModels() {
    }

    public AuctionModels(AuctionBuilder builder) {
         this.id = builder.id;
         this.seller = builder.seller;
         this.bids = builder.bids;
         this.startingPrice = builder.startingPrice;
         this.isActive = builder.isActive;
         this.created_at = builder.created_at;
         this.updated_at = builder.updated_at;
         this.endOfAuction = builder.endOfAuction;
         this.brand = builder.brand;
         this.carModel = builder.carModel;
         this.yearManufactured = builder.yearManufactured;
         this.milesDriven = builder.milesDriven;
         this.color = builder.color;
         this.carPng = builder.carPng;
         this.regNumber = builder.regNumber;
         this.condition = builder.condition;
         this.description = builder.description;
    }

    public static class AuctionBuilder {
        @Id
        private String id;
        @DBRef
        private UserModels seller;
        @DBRef
        private List<BidsModels> bids;
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

        public AuctionBuilder idAuction(String id) {
            this.id = id;
            return this;
        }

        public AuctionBuilder seller(UserModels seller) {
            this.seller = seller;
            return this;
        }

        public AuctionBuilder bids(List<BidsModels> bids) {
            this.bids = bids;
            return this;
        }

        public AuctionBuilder startingPrice(int startingPrice) {
            this.startingPrice = startingPrice;
            return this;
        }

        public AuctionBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public AuctionBuilder created(Date created_at) {
            this.created_at = created_at;
            return this;
        }
        public AuctionBuilder updated(Date updated_at) {
            this.updated_at = updated_at;
            return this;
        }
        public AuctionBuilder endOfAuction(Date endOfAuction) {
            this.endOfAuction = endOfAuction;
            return this;
        }
        public AuctionBuilder EBrand(EBrand brand) {
            this.brand = brand;
            return this;
        }
        public AuctionBuilder EColor(List<EColor> color) {
            this.color = color;
            return this;
        }
        public AuctionBuilder carModel(String carModel) {
            this.carModel = carModel;
            return this;
        }
        public AuctionBuilder yearManufactured(int yearManufactured) {
            this.yearManufactured = yearManufactured;
            return this;
        }
        public AuctionBuilder milesDriven(int milesDriven) {
            this.milesDriven = milesDriven;
            return this;
        }

        public AuctionBuilder regNumber(String regNumber) {
            this.regNumber= regNumber;
            return this;
        }

        public AuctionBuilder ECondition(ECondition condition) {
            this.condition = condition;
            return this;
        }
        public AuctionBuilder carPng(String carPng) {
            this.carPng= carPng;
            return this;
        }

        public AuctionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public AuctionModels build() {
            return new AuctionModels(this);
        }
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

        public List<BidsModels> getBids() {
        return bids;
    }

        public void setBids(List<BidsModels> bids) {
        this.bids = bids;
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

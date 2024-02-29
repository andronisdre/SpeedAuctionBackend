package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document("orders")
public class OrderModels {

    @Id
    private String id;
    private List<AuctionModels> auctions;
    @DBRef
    private UserModels seller_id;
    @DBRef
    private UserModels buyer_id;
    @CreatedDate
    private Date order_created;
    @DateTimeFormat
    private Date order_finalized;

    public OrderModels(String id, List<AuctionModels> auctions, UserModels seller_id, UserModels buyer_id, Date order_created, Date order_finalized) {
        this.id = id;
        this.auctions = auctions;
        this.seller_id = seller_id;
        this.buyer_id = buyer_id;
        this.order_created = order_created;
        this.order_finalized = order_finalized;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AuctionModels> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<AuctionModels> auctions) {
        this.auctions = auctions;
    }

    /*public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }*/

    public Date getOrder_created() {
        return order_created;
    }

    public void setOrder_created(Date order_created) {
        this.order_created = order_created;
    }

    public Date getOrder_finalized() {
        return order_finalized;
    }

    public void setOrder_finalized(Date order_finalized) {
        this.order_finalized = order_finalized;
    }

    public void setSeller_id(UserModels seller_id) {
        this.seller_id = seller_id;
    }

    public void setBuyer_id(UserModels buyer_id) {
        this.buyer_id = buyer_id;
    }

    public UserModels getBuyer_id() {
        return buyer_id;
    }

    public UserModels getSeller_id() {
        return seller_id;
    }

    /*public void setAuctions(String auctions_id) {
        this.auctions_id = auctions_id;
    }

    public String getAuctions() {
        return auctions_id;
    }*/
}

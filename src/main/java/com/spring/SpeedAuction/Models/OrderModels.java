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
    @DBRef
    private AuctionModels auction_id;
    @DBRef
    private UserModels seller_id;
    @DBRef
    private UserModels buyer_id;
    @CreatedDate
    private Date order_created;
    @DateTimeFormat
    private Date order_finalized;

    public OrderModels(String id,AuctionModels auction_id, UserModels seller_id, UserModels buyer_id, Date order_created, Date order_finalized) {
        this.id = id;
        this.auction_id = auction_id;
        this.seller_id = seller_id;
        this.buyer_id = buyer_id;
        this.order_created = order_created;
        this.order_finalized = order_finalized;

    }

    public OrderModels() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


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

    public AuctionModels getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(AuctionModels auction_id) {
        this.auction_id = auction_id;
    }
}

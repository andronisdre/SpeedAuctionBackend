package com.spring.SpeedAuction.Models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Review")
public class ReviewModels {

    @Id
    private String id;

    @NotBlank(message = "write a content")
    private String reviewContent;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @CreatedDate
    private Date created_at;

    @DBRef
    private UserModels seller_id;

    @DBRef
    private UserModels buyer_id;

    public ReviewModels() {
    }

    public ReviewModels(String id, String reviewContent, int rating, Date created_at, UserModels seller_id, UserModels buyer_id) {
        this.id = id;
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.created_at = created_at;
        this.seller_id = seller_id;
        this.buyer_id = buyer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }


    public UserModels getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(UserModels seller_id) {
        this.seller_id = seller_id;
    }

    public UserModels getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(UserModels buyer_id) {
        this.buyer_id = buyer_id;
    }
}


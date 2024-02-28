package com.spring.SpeedAuction.Models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Review")
public class ReviewModels {

    @Id
    private String id;

    private String reviewContent;

    private int rating;

    @CreatedDate
    private Date created_at;

    //@DBRef
    //private UserModels sellerId;
    //@DBRef
    //private UserModels buyerId;


    public ReviewModels() {
    }

    public ReviewModels(String id, String reviewContent, int rating, Date created_at) {
        this.id = id;
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.created_at = created_at;
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
}

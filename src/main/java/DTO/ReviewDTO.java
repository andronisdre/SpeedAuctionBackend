package DTO;

import com.spring.SpeedAuction.Models.UserModels;

public class ReviewDTO {

    private String reviewContent;
    private int rating;
    private UserModels user_id; // TESTA LÄGG STRING
    private UserModels reviewer_id; // TESTA LÄGG STRING

    public ReviewDTO() {
    }

    public ReviewDTO(String reviewContent, int rating, UserModels user_id, UserModels reviewer_id) {
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.user_id = user_id;
        this.reviewer_id = reviewer_id;
    }


    public String getReviewContent() {
        return reviewContent;
    }

    public int getRating() {
        return rating;
    }

    public UserModels getUser_id() {
        return user_id;
    }

    public UserModels getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUser_id(UserModels user_id) {
        this.user_id = user_id;
    }

    public void setReviewer_id(UserModels reviewer_id) {
        this.reviewer_id = reviewer_id;
    }
}




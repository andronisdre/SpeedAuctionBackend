package com.spring.SpeedAuction.Services;

import DTO.ReviewDTO;
import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.ReviewRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewServices {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;


    public ReviewModels addReview(ReviewDTO reviewDTO) {

        UserModels user = userRepository.findById(reviewDTO.getUser_id())
                .orElseThrow(() -> new NoSuchElementException("Invalid user id"));

        UserModels reviewer = userRepository.findById(reviewDTO.getReviewer_id())
                .orElseThrow(() -> new NoSuchElementException("Invalid reviewer id"));


        ReviewModels review = new ReviewModels();
        review.setReviewContent(reviewDTO.getReviewContent());
        review.setRating(reviewDTO.getRating());
        review.setUser_id(user);
        review.setReviewer_id(reviewer);


        return reviewRepository.save(review);
 }

    public List<ReviewModels> getAllReviews() {  // GET hämta alla reviews
        return reviewRepository.findAll();
    }

    public ReviewModels getReviewById(String id) {  // GET hämta review genom id
        return reviewRepository.findById(id).get();
    }


    // FÖRSÖK FIXA PUT/UPDATEREVIEW MED DTO

  /*  public ReviewModels updateReview(String id, ReviewModels updateReview) { // PUT updatera review FRÅN BASIC
        return reviewRepository.findById(id)
                .map(existingReviewModels -> {
                    if (updateReview.getReviewContent() != null) {
                        existingReviewModels.setReviewContent(updateReview.getReviewContent());
                    }
                    if (updateReview.getRating() != 0) {
                        existingReviewModels.setRating(updateReview.getRating());
                    }
                    return reviewRepository.save(existingReviewModels);
                })
                .orElseThrow(() -> new NoSuchElementException("Review not found with id: " + id));
    }*/

    public String deleteReview(String id) {  // DELETE ta bort en review
        reviewRepository.deleteById(id);
        return "Your review is deleted";
    }
}

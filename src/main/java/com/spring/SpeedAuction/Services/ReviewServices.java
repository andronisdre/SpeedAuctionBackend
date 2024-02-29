package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewServices {

    @Autowired
    ReviewRepository reviewRepository;

    public ReviewModels addReview(ReviewModels review) {  // POST att lägga till review
        return reviewRepository.save(review);
    }

    public List<ReviewModels> getAllReviews() {  // GET hämta alla reviews
        return reviewRepository.findAll();
    }

    public ReviewModels getReviewById(String id) {  // GET hämta review genom id
        return reviewRepository.findById(id).get();
    }

    public ReviewModels updateReview(String id, ReviewModels updateReview) { // PUT updatera review
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

    }

    public String deleteReview(String id) {  // DELETE ta bort en review
        reviewRepository.deleteById(id);
        return "Your review is deleted";
    }
}

package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ReviewModels updateReview(ReviewModels review) { // PUT updatera review
        return reviewRepository.save(review);
    }

    public String deleteReview(String id) {  // DELETE ta bort en review
        reviewRepository.deleteById(id);
        return "Your review is deleted";
    }









}

package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.dto.ReviewDTO;
import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.ReviewRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReviewServices {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;


    public ReviewModels addReview(ReviewDTO reviewDTO) {    // POST Lägg till en review

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

    public List<ReviewDTO> getAllReviews() {  // GET hämta alla reviews
        List<ReviewModels> review = reviewRepository.findAll();

        return review.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ReviewModels getReviewById(String id) {  // GET hämta review genom id
        return reviewRepository.findById(id).get();
    }

    public ReviewModels updateReview(String id, ReviewModels updateReview) { // PUT updatera review
        return reviewRepository.findById(id)
                .map(existingReviewModels -> {  // Jag vill att man bara ska kunna uppdatera Content och raiting
                    if (updateReview.getReviewContent() != null) {
                        existingReviewModels.setReviewContent(updateReview.getReviewContent());
                    }
                    if (updateReview.getRating() != 0) {
                        existingReviewModels.setRating(updateReview.getRating());
                    }
                    return reviewRepository.save(existingReviewModels);
                })
                .orElseThrow(() -> new NoSuchElementException("Invalid reviewer id"));
    }

    public String deleteReview(String id) {
        ReviewModels reviewModels = reviewRepository.findById(id).orElse(null);
        if (reviewModels != null) {
            reviewRepository.deleteById(id);
            return "review is deleted";
        } else {
            return "Review id doesn't exist";
        }
    }

    // Hjälpmetod
    private ReviewDTO convertToDTO(ReviewModels reviewModels) {
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setUser_id(reviewModels.getUser_id().getId());
        reviewDTO.setReviewer_id(reviewModels.getReviewer_id().getId());

        reviewDTO.setReviewContent(reviewModels.getReviewContent());
        reviewDTO.setRating(reviewModels.getRating());

        return reviewDTO;
    }
}

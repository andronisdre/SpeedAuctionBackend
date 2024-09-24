package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.ReviewDTO;
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


        // POST här Läggs till en review
        public ReviewModels addReview(ReviewDTO reviewDTO) {
            UserModels user = getUserById(reviewDTO.getUser_id(), "Invalid user id");
            UserModels reviewer = getUserById(reviewDTO.getReviewer_id(), "Invalid reviewer id");

            ReviewModels review = createAndSaveReview(user, reviewer, reviewDTO);

            return review;
        }

        // Hämta användare med hjälp av user_id
        private UserModels getUserById(String userId, String errorMessage) {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException(errorMessage));
        }

        // Skapa och spara recension
        private ReviewModels createAndSaveReview(UserModels user, UserModels reviewer, ReviewDTO reviewDTO) {
            ReviewModels review = new ReviewModels();
            review.setReviewContent(reviewDTO.getReviewContent());
            review.setRating(reviewDTO.getRating());
            review.setUser_id(user);
            review.setReviewer_id(reviewer);

            return reviewRepository.save(review);
        }

        // GET Hämta alla recensioner
        public List<ReviewDTO> getAllReviews() {
            List<ReviewModels> reviews = reviewRepository.findAll();
            return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
        }

        // GET för att Hämta review genom ID
        public ReviewModels getReviewById(String id) {
            return reviewRepository.findById(id).get();
        }

        // PUT för att Uppdatera recension
        public ReviewModels updateReview(String id, ReviewModels updateReview) {
            return reviewRepository.findById(id)
                    .map(existingReview -> {
                        if (updateReview.getReviewContent() != null) {
                            existingReview.setReviewContent(updateReview.getReviewContent());
                        }
                        if (updateReview.getRating() != 0) {
                            existingReview.setRating(updateReview.getRating());
                        }
                        return reviewRepository.save(existingReview);
                    })
                    .orElseThrow(() -> new NoSuchElementException("Invalid review id"));
        }

        // DELETE - Radera recension
        public String deleteReview(String id) {
            ReviewModels review = reviewRepository.findById(id).orElse(null);
            if (review != null) {
                reviewRepository.deleteById(id);
                return "Review is deleted";
            } else {
                return "Review id doesn't exist";
            }
        }

        // Hjälpmetod för att konvertera ReviewModels till ReviewDTO
        private ReviewDTO convertToDTO(ReviewModels review) {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setUser_id(review.getUser_id().getId());
            reviewDTO.setReviewer_id(review.getReviewer_id().getId());
            reviewDTO.setReviewContent(review.getReviewContent());
            reviewDTO.setRating(review.getRating());
            return reviewDTO;
        }
    }

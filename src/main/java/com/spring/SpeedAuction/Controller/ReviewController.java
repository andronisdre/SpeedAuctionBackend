package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.DTO.ReviewDTO;
import com.spring.SpeedAuction.Security.Services.ReviewServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {

    @Autowired
    ReviewServices reviewServices;


    // POST
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
   @PostMapping()
   public ResponseEntity<ReviewModels> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
       ReviewModels newReview = reviewServices.addReview(reviewDTO);
       return ResponseEntity.ok(newReview);
   }

   // GET ALL
   @GetMapping("/all")
   public ResponseEntity<List<ReviewDTO>> getAllReviews() {
       List<ReviewDTO> review = reviewServices.getAllReviews();
       return ResponseEntity.ok(review);
   }


    // GET BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReviewModels getReviewById(@PathVariable String id) {
        return reviewServices.getReviewById(id);
    }


    // PUT
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable String id, @Valid @RequestBody ReviewModels reviewDetails) {
        try {
            ReviewModels updateReview = reviewServices.updateReview(id,reviewDetails);
            return ResponseEntity.ok(updateReview);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteReview(@PathVariable String id) {
        return reviewServices.deleteReview(id);
    }
}

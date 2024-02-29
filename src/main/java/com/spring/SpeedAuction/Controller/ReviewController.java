package com.spring.SpeedAuction.Controller;

import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.Services.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/review")
public class ReviewController {

    @Autowired
    ReviewServices reviewServices;


    // POST
    @PostMapping()
    public ReviewModels addReview(@RequestBody ReviewModels review) {
        return reviewServices.addReview(review);
    }

    // GET ALL
    @GetMapping("/all")
    public List<ReviewModels> getAllReviews() {
        return reviewServices.getAllReviews();
    }

    // GET BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReviewModels getReviewById(@PathVariable String id) {
        return reviewServices.getReviewById(id);
    }

    // PUT











}

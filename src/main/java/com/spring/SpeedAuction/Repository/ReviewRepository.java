package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.ReviewModels;
import com.spring.SpeedAuction.Models.UserModels;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<ReviewModels, String> {

    List<ReviewModels> findByUser(UserModels user);  // TESTA LÄGG STRING ist för usermodels
    List<ReviewModels> findByReviewer(UserModels reviewer); // TESTA LÄGG STRING ist för usermodels
}

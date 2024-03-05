package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.ReviewModels;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<ReviewModels, String> {


}

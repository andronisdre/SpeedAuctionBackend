package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuctionRepository extends MongoRepository<AuctionModels, String> {
}

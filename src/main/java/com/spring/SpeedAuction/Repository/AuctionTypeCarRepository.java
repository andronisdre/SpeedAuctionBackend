package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionTypeCar;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuctionTypeCarRepository extends MongoRepository<AuctionTypeCar, String> {
}

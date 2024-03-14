package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends MongoRepository<AuctionModels, String> {
    List<AuctionModels> findAuctionModelsByIsActive(boolean active);
    List<AuctionModels> findAuctionModelsByStartingPriceBetweenOrderByStartingPriceAsc(int minStartingPrice, int maxStartingPrice);
}

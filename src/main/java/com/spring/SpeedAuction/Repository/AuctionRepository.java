package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends MongoRepository<AuctionModels, String> {
    List<AuctionModels> findAuctionModelsByYearManufactured(int yearManufactured);
    List<AuctionModels> findAuctionModelsByIsActive(boolean isActive);
    List<AuctionModels> findAuctionModelsByStartingBid(int startingBid);
    List<AuctionModels> findAuctionModelsByStartingBidBetweenOrderByStartingBidAsc(int minStartingBid, int maxStartingBid);
}

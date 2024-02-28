package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends MongoRepository<AuctionModels, String> {
    List<AuctionModels> findAuctionModelsByYearManufacturedBetweenOrderByYearManufacturedDesc(int minYearManufactured, int maxYearManufactured);
    List<AuctionModels> findAuctionModelsByIsActive(boolean active);
    List<AuctionModels> findAuctionModelsByBrand(String brand);
    List<AuctionModels> findAuctionModelsByStartingBidBetweenOrderByStartingBidAsc(int minStartingBid, int maxStartingBid);
    List<AuctionModels> findAuctionModelsByMilesDrivenBetweenOrderByMilesDrivenAsc(int minMilesDriven, int maxMilesDriven);
    List<AuctionModels> findAuctionModelsByStartingBidBetweenAndYearManufacturedAndIsActiveOrderByStartingBidAsc(int minStartingBid, int maxStartingBid, int yearManufactured, boolean isActive);
}

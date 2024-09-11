package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends MongoRepository<AuctionModels, String> {
    List<AuctionModels> findAuctionModelsByIsActive(boolean active);
    List<AuctionModels> findAuctionModelsByStartingPriceBetweenOrderByStartingPriceAsc(int minStartingPrice, int maxStartingPrice);
    List<AuctionModels> findAuctionModelsByColor(String color);
    List<AuctionModels> findAuctionModelsByBrand(String brand);
    List<AuctionModels> findAuctionModelsByCondition(String condition);
    List<AuctionModels> findAuctionModelsByYearManufacturedBetweenOrderByYearManufacturedDesc(int minYearManufactured,int maxYearManufactured);
    List<AuctionModels> findAuctionModelsByMilesDrivenBetweenOrderByMilesDrivenAsc(int minMilesDriven,int maxMilesDriven);

}

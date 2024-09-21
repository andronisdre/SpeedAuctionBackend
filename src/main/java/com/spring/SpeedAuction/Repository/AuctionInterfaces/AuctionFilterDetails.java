package com.spring.SpeedAuction.Repository.AuctionInterfaces;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionFilterDetails extends MongoRepository<AuctionModels, String> {
    List<AuctionModels> findAuctionModelsByColor(String color);
    List<AuctionModels> findAuctionModelsByBrand(String brand);
    List<AuctionModels> findAuctionModelsByCondition(String condition);
    List<AuctionModels> findAuctionModelsByYearManufacturedBetweenOrderByYearManufacturedDesc(int minYearManufactured,int maxYearManufactured);
    List<AuctionModels> findAuctionModelsByMilesDrivenBetweenOrderByMilesDrivenAsc(int minMilesDriven,int maxMilesDriven);
}

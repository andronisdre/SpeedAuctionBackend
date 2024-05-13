package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionTypeCar;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuctionTypeCarRepository extends MongoRepository<AuctionTypeCar, String> {
    List<AuctionTypeCar> findAuctionTypeCarByColor(String color);
    List<AuctionTypeCar> findAuctionTypeCarByAuction(String auctionId);
    List<AuctionTypeCar> findAuctionTypeCarByBrand(String brand);
    List<AuctionTypeCar> findAuctionTypeCarByCondition(String condition);
    List<AuctionTypeCar> findAuctionTypeCarByYearManufacturedBetweenOrderByYearManufacturedDesc(int minYearManufactured,int maxYearManufactured);
    List<AuctionTypeCar> findAuctionTypeCarByMilesDrivenBetweenOrderByMilesDrivenAsc(int minMilesDriven,int maxMilesDriven);

}

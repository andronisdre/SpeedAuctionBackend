package com.spring.SpeedAuction.Repository.AuctionInterfaces;

import com.spring.SpeedAuction.Models.AuctionModels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionFilterActive extends MongoRepository<AuctionModels, String> {
    List<AuctionModels> findAuctionModelsByIsActive(boolean active);
}


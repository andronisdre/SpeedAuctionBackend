package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.BidsModels;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BidsRepository extends MongoRepository<BidsModels,String> {
    List<BidsModels> findBidsModelsByBidderIdOrderByTimeBiddedDesc(String bidderId);
}

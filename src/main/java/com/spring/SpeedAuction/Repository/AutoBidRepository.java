package com.spring.SpeedAuction.Repository;


import com.spring.SpeedAuction.Models.AutoBidModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoBidRepository  extends MongoRepository<AutoBidModel, String> {

    // Fetch all auto id for specific auction,
    List<AutoBidModel>findByAuctionId(String  auctionID);


}

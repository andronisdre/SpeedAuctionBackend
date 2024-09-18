package com.spring.SpeedAuction.Repository;


import com.spring.SpeedAuction.Models.AutoBidModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoBidRepository  extends MongoRepository<AutoBidModel, String> {

    // Fetch all auto id for specific auction,
    List<AutoBidModel>findByAuctionId(String  auctionID);

    //Fetch auto-id for specific user in a specific aucitom
    AutoBidModel findByAuctionIdAndAutoBidderId(String auctionId, String autoBidder_id);

    //Fech all active auto-bidder  of a user
    List<AutoBidModel>findAutoBidderId(String autoBidder);
}

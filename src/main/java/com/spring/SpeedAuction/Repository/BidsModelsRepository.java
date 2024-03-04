package com.spring.SpeedAuction.Repository;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BidsModelsRepository extends MongoRepository<BidsModels,String> {
    List<BidsModels> findBidsModelsByBidderIdOrderByTimeBiddedDesc(UserModels bidderId);
    List<BidsModels> findBidsModelsByAuctionIdOrderByTimeBiddedDesc(AuctionModels auctionId);
}

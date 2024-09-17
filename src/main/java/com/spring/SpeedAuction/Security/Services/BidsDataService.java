package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Repository.BidsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsDataService {

    @Autowired
    BidsRepository bidsRepository;

    public BidsModels saveBid(BidsModels bid) {
        return bidsRepository.save(bid);
    }

    public BidsModels getBidById(String id) {
        return bidsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bid not found."));
    }

    public List<BidsModels> getBidsByAuctionId(String auctionId) {
        return bidsRepository.findBidsModelsByAuctionIdOrderByAmountDesc(auctionId);
    }

    public List<BidsModels> getBidsByBidderId(String bidderId) {
        return bidsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
    }

    public void deleteBid(BidsModels bid) {
        bidsRepository.delete(bid);
    }

    // GET by id
    public BidsModels getBidsModel(String id) {
        return bidsRepository.findById(id).get();
    }
}



package com.spring.SpeedAuction.security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Repository.BidsModelsRepository;
import com.spring.SpeedAuction.dto.BidsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidsDataService {

    @Autowired
    BidsModelsRepository bidsModelsRepository;

    public BidsModels saveBid(BidsModels bid) {
        return bidsModelsRepository.save(bid);
    }

    public BidsModels getBidById(String id) {
        return bidsModelsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bid not found."));
    }

    public List<BidsModels> getBidsByAuctionId(String auctionId) {
        return bidsModelsRepository.findBidsModelsByAuctionIdOrderByAmountDesc(auctionId);
    }

    public List<BidsModels> getBidsByBidderId(String bidderId) {
        return bidsModelsRepository.findBidsModelsByBidderIdOrderByTimeBiddedDesc(bidderId);
    }

    public void deleteBid(BidsModels bid) {
        bidsModelsRepository.delete(bid);
    }

    // GET by id
    public BidsModels getBidsModel(String id) {
        return bidsModelsRepository.findById(id).get();
    }
}



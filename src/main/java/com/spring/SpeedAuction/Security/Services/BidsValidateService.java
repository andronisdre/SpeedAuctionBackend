package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import com.spring.SpeedAuction.DTO.BidsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BidsValidateService {

    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BidsRepository bidsRepository;


    public UserModels checkUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid userId"));
    }

    public AuctionModels checkAuctionId(String auctionId) {
        return auctionRepository.findById(auctionId).orElseThrow(() -> new IllegalArgumentException("Invalid auctionId"));
    }

    public void checkIsActive(AuctionModels auction) {
        if (!auction.isActive()) {
            throw new IllegalArgumentException("you cant bid on an inactive auction");
        }
    }

    public void compareSellerAndBidder(AuctionModels auction, BidsModels newBid) {
        if (auction.getSeller().getId().equals(newBid.getBidder().getId())) {
            throw new IllegalArgumentException("you cant bid on your own auction");
        }
    }

    public void bidBeforeEndOfAuction(BidsModels newBid, AuctionModels auctionModels) {
        if(auctionModels.getEndOfAuction().before(newBid.getTimeBidded())) {
            throw new IllegalArgumentException("This auction has already ended");
        }
    }

    public void bidLargeEnough(BidsModels newBid, AuctionModels auction) {

        // ###### BRYTA NER TILL TVÅ OLIKA METODER
        if (newBid.getAmount() < auction.getStartingPrice()) {
            throw new IllegalArgumentException("bid is smaller than Starting bid!");
        }

        List<BidsModels> bidsModels = auction.getBids();
        for (BidsModels existingBid : bidsModels) {
            if (newBid.getAmount() < (existingBid.getAmount() + 1000)) {
                throw new IllegalArgumentException("bid needs to be at least 1000 higher than the largest bid!");
            }
        }
    }

}

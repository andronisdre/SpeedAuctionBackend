package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import com.spring.SpeedAuction.Repository.BidsRepository;
import com.spring.SpeedAuction.Repository.UserInterfaces.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class BidsValidateService {

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final BidsRepository bidsRepository;

    public BidsValidateService(AuctionRepository auctionRepository, UserRepository userRepository, BidsRepository bidsRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
        this.bidsRepository = bidsRepository;
    }

    public BidsDTO convertToDTO(BidsModels bidsModels) {
        BidsDTO bidsDTO = new BidsDTO();
        bidsDTO.setBidderId(bidsModels.getBidder().getId());
        bidsDTO.setAmount(bidsModels.getAmount());
        bidsDTO.setMaxAmount(bidsModels.getMaxAmount());
        bidsDTO.setTimeBidded(bidsModels.getTimeBidded());

        return bidsDTO;
    }

    public BidsModels retrieveData(BidsDTO bidsDTO, UserModels user) {
        BidsModels newBid = new BidsModels();
        newBid.setBidder(user);
        if (bidsDTO.getTimeBidded() != null) {
            newBid.setTimeBidded(bidsDTO.getTimeBidded());
        } else {
            newBid.setTimeBidded(new Date());
        }
        newBid.setAmount(bidsDTO.getAmount());
        newBid.setMaxAmount(bidsDTO.getMaxAmount());

        return newBid;
    }

    public AuctionModels setAuctionBids(AuctionModels auction, BidsModels bid) {
        List<BidsModels> existingBids = auction.getBids();
        if (existingBids == null) {
            existingBids = (new ArrayList<>());
        }
        existingBids.add(bid);
        auction.setBids(existingBids);

        return auction;
    }

    //saves both bid and auction then returns bid
    public BidsModels saveBidAndAuction(AuctionModels auction, BidsModels bid) {
        bidsRepository.save(bid);
        auctionRepository.save(auction);
        return bid;
    }


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
        if (bidsModels != null) {
            for (BidsModels existingBid : bidsModels) {
                if (newBid.getAmount() < (existingBid.getAmount() + 1000)) {
                    throw new IllegalArgumentException("bid needs to be at least 1000 higher than the largest bid!");
                }
            }
        }
    }

    //GET top bid for an auction
    public BidsDTO getTopBidByAuctionId (String auctionId){
        AuctionModels auction = checkAuctionId(auctionId);
        List<BidsModels> bids = auction.getBids();
        if (bids == null || bids.isEmpty()) {
            throw new IllegalArgumentException("No bids exist for this auction ID");
        }

        BidsModels topBid = bids.get(0);
        for (BidsModels bid : bids) {
            if (bid.getAmount() > topBid.getAmount()) {
                topBid = bid;
            }
        }
        return convertToDTO(topBid);
    }
}

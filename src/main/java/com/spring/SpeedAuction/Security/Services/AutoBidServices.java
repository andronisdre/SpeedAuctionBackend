package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import com.spring.SpeedAuction.Repository.UserInterfaces.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoBidServices {

    private final AuctionRepository auctionRepository;
    private final BidsValidateService bidsValidateService;
    private final UserRepository userRepository;


    public AutoBidServices(AuctionRepository auctionRepository, BidsValidateService bidsValidateService, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.bidsValidateService = bidsValidateService;
        this.userRepository = userRepository;
    }

    //
    //LOGIC TO HANDLE AUTOMATIC BIDDING

    public void checkForAutobids(AuctionModels auction) {

        UserModels topUser = getTopUser(auction);
        BidsDTO topBidDto = bidsValidateService.getTopBidByAuctionId(auction.getId());
        BidsModels topBid = bidsValidateService.retrieveData(topBidDto, topUser);

        List<BidsModels> otherUserBids = getOtherUsersBids(auction, topUser);

        List<BidsModels> newAutoBids = new ArrayList<>();

        boolean biddingContinues = true;

        while (biddingContinues) {
            biddingContinues = false;

            for (BidsModels autoBid : otherUserBids) {
                placeAutobids(topBid, autoBid, newAutoBids);
                biddingContinues = true;
            }
        }
        for (BidsModels newAutoBid : newAutoBids) {
            if (!auction.getBids().contains(newAutoBid)) {
                auction.getBids().add(newAutoBid);
            }
            bidsValidateService.saveBidAndAuction(auction, newAutoBid);
        }
    }

    public UserModels getTopUser(AuctionModels auction) {
        BidsDTO topBidDto = bidsValidateService.getTopBidByAuctionId(auction.getId());
        return userRepository.findById(topBidDto.getBidderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    public List<BidsModels> getOtherUsersBids(AuctionModels auction, UserModels topUser) {
        List<BidsModels> existingBids = auction.getBids();

       return existingBids.stream()
                .filter(bid -> !bid.getBidder().equals(topUser))
                .collect(Collectors.toList());
    }

    public BidsModels placeAutobids(BidsModels topBid, BidsModels autoBid, List<BidsModels> newAutoBids) {
        if (topBid.getAmount() < autoBid.getMaxAmount() && !topBid.getBidder().equals(autoBid.getBidder())) {
            int newBidAmount = topBid.getAmount() + 1000;

            if (newBidAmount > autoBid.getMaxAmount()) {
                newBidAmount = autoBid.getMaxAmount();
            }

            BidsDTO autoBidDto = bidsValidateService.convertToDTO(autoBid);
            BidsModels newAutoBid = bidsValidateService.retrieveData(autoBidDto, autoBid.getBidder());
            newAutoBid.setAmount(newBidAmount);

            newAutoBids.add(newAutoBid);

            topBid = newAutoBid;
        }
        return topBid;
    }
}

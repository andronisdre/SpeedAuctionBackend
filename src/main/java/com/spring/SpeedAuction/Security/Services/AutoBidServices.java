package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.BidsDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import com.spring.SpeedAuction.Repository.UserInterfaces.UserRepository;
import org.springframework.stereotype.Service;

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

        List<BidsModels> otherUserBids = getOtherUsersBids(auction, topUser, topBid);
        loopThroughBids(otherUserBids, auction, topBid);
    }

    public void loopThroughBids(List<BidsModels> otherUserBids, AuctionModels auction, BidsModels topBid) {
        boolean biddingContinues = true;
        while (biddingContinues) {
            biddingContinues = false;

            for (BidsModels autoBid : otherUserBids) {

                System.out.println("autoBid MaxAmount: " + autoBid.getMaxAmount() + " --- topBid Amount: " + topBid.getAmount());
                System.out.println("autoBid user id: " + autoBid.getBidder().getId() + " --- topBid user id: " + topBid.getBidder().getId());
                BidsModels updatedTopBid = placeAutobids
                        (auction, topBid, autoBid);
                if (!updatedTopBid.equals(topBid)) {
                    topBid = updatedTopBid;
                    biddingContinues = true;
                    UserModels topUser = getTopUser(auction);
                    otherUserBids = getOtherUsersBids(auction, topUser, topBid);
                }
            }
        }
    }

    public BidsModels placeAutobids(AuctionModels auction, BidsModels topBid, BidsModels autoBid) {
        if (topBid.getAmount() < autoBid.getMaxAmount() && !topBid.getBidder().getId().equals(autoBid.getBidder().getId())) {
            int newBidAmount = topBid.getAmount() + 1000;

            if (newBidAmount > autoBid.getMaxAmount()) {
                newBidAmount = autoBid.getMaxAmount();
            }

            BidsDTO autoBidDto = bidsValidateService.convertToDTO(autoBid);
            BidsModels newAutoBid = bidsValidateService.retrieveData(autoBidDto, autoBid.getBidder());
            newAutoBid.setAmount(newBidAmount);

            bidsValidateService.setAuctionBids(auction, newAutoBid);
            bidsValidateService.saveBidAndAuction(auction, newAutoBid);

            topBid = newAutoBid;
        }
        return topBid;
    }

    public UserModels getTopUser(AuctionModels auction) {
        BidsDTO topBidDto = bidsValidateService.getTopBidByAuctionId(auction.getId());
        return userRepository.findById(topBidDto.getBidderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    public List<BidsModels> getOtherUsersBids(AuctionModels auction, UserModels topUser, BidsModels topBid) {
        List<BidsModels> existingBids = auction.getBids();

        //filters bids so that only the ones with a higher maxAmount than the topbids amount are assigned
        existingBids = existingBids.stream()
                .filter(bid -> bid.getMaxAmount()>(topBid.getAmount()))
                .collect(Collectors.toList());

        return existingBids.stream()
                .filter(bid -> !bid.getBidder().equals(topUser))
                .collect(Collectors.toList());
    }
}

package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AutoBidServices {

    private final AuctionRepository auctionRepository;
    private final BidsValidateService bidsValidateService;


    public AutoBidServices(AuctionRepository auctionRepository, BidsValidateService bidsValidateService) {
        this.auctionRepository = auctionRepository;
        this.bidsValidateService = bidsValidateService;
    }

    //
    //LOGIC TO HANDLE AUTOMATIC BIDDING

    public BidsModels placeAutoBid(AuctionModels auction, BidsModels currentBid) {

        List<BidsModels> existingBids = auction.getBids();
        if (existingBids == null || existingBids.isEmpty()) {
            existingBids = (new ArrayList<>());
        }


        System.out.println("max amount is >>>>" + currentBid.getMaxAmount());
        System.out.println("amount is >>>>" + currentBid.getAmount());


        existingBids.add(currentBid);
        auction.setBids(existingBids);

        boolean isBigger = true;

        for (BidsModels autoBid : existingBids) {
            while (isBigger) {
                BidsModels topBid = existingBids.get(0);

                for (BidsModels bid : existingBids) {
                    if (bid.getAmount() > topBid.getAmount()) {
                        topBid = bid;
                    }
                }

                if (autoBid.getAmount() > topBid.getAmount()) {
                    topBid.setAmount(autoBid.getAmount());
                }
                    int newBidAmount = topBid.getAmount() + 2000;


                    if (newBidAmount <= autoBid.getMaxAmount()) {


                        return bidsValidateService.saveBidAndAuction(auction, currentBid);
                    }
                if (autoBid.getMaxAmount() <= topBid.getAmount()) {
                    isBigger = false;
                    break;
                }
            }
        }
        return currentBid;
    }

}

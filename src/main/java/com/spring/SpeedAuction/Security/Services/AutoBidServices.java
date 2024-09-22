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

    public BidsModels placeAutoBid(AuctionModels auction, BidsModels currentBid){

        List<BidsModels> existingBids = auction.getBids();
        if (existingBids == null || existingBids.isEmpty()) {
            existingBids = (new ArrayList<>());
        }


        System.out.println("max amount is >>>>" + currentBid.getMaxAmount());
        System.out.println("amount is >>>>" + currentBid.getAmount());


        existingBids.add(currentBid);
        auction.setBids(existingBids);

        BidsModels topBid = existingBids.get(0);
        for (BidsModels bid : existingBids) {
            if (bid.getAmount() > topBid.getAmount()) {
                topBid = bid;
            }
        }

        System.out.println("test2 ");

        for (BidsModels  autoBid: existingBids){
            if(autoBid.getMaxAmount() > topBid.getAmount()){
                int newBidAmount = topBid.getAmount() + 5000;

                System.out.print("amout1 " + autoBid.getMaxAmount());

                if(newBidAmount <= autoBid.getMaxAmount()){

                    System.out.println("innan rad 61" +  currentBid);
                    return bidsValidateService.saveBidAndAuction(auction, currentBid);
                }
            }
        }
        return currentBid;
    }

}

package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Repository.AuctionInterfaces.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class AutoBidServices {

    private final AuctionRepository auctionRepository;


    public AutoBidServices( AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    //
    //LOGIC TO HANDLE AUTOMATIC BIDDING



    public BidsModels placeAutoBid(AuctionModels auction, BidsModels currentBid){
        BidsModels topBid = auction.getBids()
                .stream().max(Comparator.comparingInt(BidsModels::getAmount))
                .orElse(currentBid);

        List<BidsModels> autoBids = auction.getBids();
        if (autoBids.isEmpty()){
            System.out.print("test1 ");
            return null;

        }
        System.out.println("test2 ");
        for (BidsModels  autoBid: autoBids){
            if(autoBid.getMaxAmount() > topBid.getAmount()){
                int newBidAmount = topBid.getAmount() + 5000;
                System.out.print("amout1 " + autoBid.getMaxAmount());
                if(newBidAmount <= autoBid.getMaxAmount()){
                    BidsModels newBid = new BidsModels();
                    newBid.setBidder(autoBid.getBidder());
                    newBid.setAmount(newBidAmount);
                    newBid.setTimeBidded(new Date());
                    return saveBidAndAuction(auction, newBid);
                }
            }
        }

        return null;
    }

    private BidsModels saveBidAndAuction(AuctionModels auction, BidsModels bid){
        auction.getBids().add(bid);
        auctionRepository.save(auction);
        return bid;
    }
}

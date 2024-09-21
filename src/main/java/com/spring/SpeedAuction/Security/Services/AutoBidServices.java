package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.AutoBidDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.AutoBidModel;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.AutoBidRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class AutoBidServices {
    final
    AutoBidRepository autoBidRepository;

    final
    AuctionRepository auctionRepository;

    final
    UserRepository userRepository;


    public AutoBidServices(AutoBidRepository autoBidRepository, AuctionRepository auctionRepository, UserRepository userRepository) {
        this.autoBidRepository = autoBidRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    // create AutoBid using DTO
    public AutoBidModel setAutoBid(AutoBidDTO autoBidDTO){
        UserModels user = userRepository.findById(autoBidDTO.getAutoBidder()).orElseThrow(() -> new IllegalArgumentException("invalid userId"));
        AuctionModels auction = auctionRepository.findById(autoBidDTO.getAuctionId()).orElseThrow(()-> new IllegalArgumentException("Invalid auctionId"));

        AutoBidModel autoBid = new AutoBidModel();
        autoBid.setAutoBidder(user);
        autoBid.setAuctionId(autoBidDTO.getAuctionId());
        autoBid.setMaxBidAMount(autoBidDTO.getMaxBidAmount());
        autoBid.setTimeBidded(new Date());

        return autoBidRepository.save(autoBid);
    }

    //convert AutobidModel to AutobidDto
    public   AutoBidDTO convertAutoBidDTO(AutoBidModel autoBidModel){
        AutoBidDTO autoBidDTO = new AutoBidDTO();
        autoBidDTO = new AutoBidDTO();
        autoBidDTO.setAuctionId(autoBidModel.getAuctionId());
        autoBidDTO.setAutoBidder(autoBidModel.getAutoBidder().getId());
        autoBidDTO.setMaxBidAmount(autoBidModel.getMaxBidAMount());
        autoBidDTO.setTimeBidded(autoBidModel.getTimeBidded());

        return autoBidDTO;
    }

    //LOGIC TO HANDLE AUTOMATIC BIDDING

    public BidsModels placeBidAutomatically(AuctionModels auction, BidsModels currentBid){
        BidsModels topBid = auction.getBids()
                .stream().max(Comparator.comparingInt(BidsModels::getAmount))
                .orElse(currentBid);

        List<AutoBidModel> autoBids =autoBidRepository.findByAuctionId(auction.getId());
        if (autoBids.isEmpty()){
            return null;
        }

        for (AutoBidModel autoBid: autoBids){
            if(autoBid.getMaxBidAMount() > topBid.getAmount()){
                int newBidAmount = topBid.getAmount() + 5000;
                if(newBidAmount <= autoBid.getMaxBidAMount()){
                    BidsModels newBid = new BidsModels();
                    newBid.setBidder(autoBid.getAutoBidder());
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

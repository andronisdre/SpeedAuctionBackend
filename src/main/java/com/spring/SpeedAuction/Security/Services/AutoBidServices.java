package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.DTO.AutoBidDTO;
import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Models.AutoBidModel;
import com.spring.SpeedAuction.Models.BidsModels;
import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import com.spring.SpeedAuction.Repository.AutoBidRepository;
import com.spring.SpeedAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AutoBidServices {
    @Autowired
    AutoBidRepository autoBidRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    UserRepository userRepository;

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


}

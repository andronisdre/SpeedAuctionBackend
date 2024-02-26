package com.spring.SpeedAuction.Services;

import com.spring.SpeedAuction.Models.AuctionModels;
import com.spring.SpeedAuction.Repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionServices {
    @Autowired
    AuctionRepository auctionRepository;

    //post
    public AuctionModels createAuctionModels(AuctionModels auctionModels) {
        return auctionRepository.save(auctionModels);
    }

    //get all
    public List<AuctionModels> getAllAuctionModels() {
        return auctionRepository.findAll();
    }


    //update
    public AuctionModels updateAuctionModels(AuctionModels auctionModels) {
        return auctionRepository.save(auctionModels);
    }

    //get auction by id
    public AuctionModels getAuctionModelsById(String id) {
        return auctionRepository.findById(id).get();
    }

    //get all auctions with matching yearManufactured
    public List<AuctionModels> getAuctionModelsByYearManufactured(int yearManufactured) {
        return auctionRepository.findAuctionModelsByYearManufactured(yearManufactured);
    }

    //delete auction
    public String deleteAuctionModels(String id) {
        auctionRepository.deleteById(id);
        return "Auction deleted";
    }
}
